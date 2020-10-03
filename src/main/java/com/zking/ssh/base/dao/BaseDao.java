package com.zking.ssh.base.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.query.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.zking.ssh.util.PageBean;

public abstract class BaseDao extends HibernateDaoSupport {
	
	/**
	 * 设置Query的查询参数
	 * @param params
	 * @param query
	 * @return
	 */
	public void setParamters(Map<String,Object> params,Query query) {
		if(null==params||0==params.size())
			return;
		
		Set<Entry<String, Object>> set = params.entrySet();
		String name=null;
		Object value=null;
		for (Entry<String, Object> entry : set) {
			name=entry.getKey();
			value=entry.getValue();
			//判断参数是否是Collection，一般用于List/Set集合参数时使用
			if(value instanceof Collection)
				query.setParameterList(name, (Collection)value);
			//判断参数是否是Object[]
			else if(value instanceof Object[]) 
				query.setParameterList(name, (Object[])value);
			else 
				query.setParameter(name, value);
		}
	}
	
	/**
	 * 将普通hql语句转换成查询总记录数的hql语句
	 * @param hql
	 * @return
	 */
	public String countSql(String hql) {
		int start=hql.toUpperCase().indexOf("FROM");
		return "select count(1) "+hql.substring(start);
	}
	
	public String getCountSql(String sql) {
		return "select count(1) from ("+sql+") temp";
	}
	
	public List<Map<String,Object>> executeQueryMap(String sql,
			Map<String,Object> params,PageBean pageBean){
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Map<String,Object>>>() {
			@SuppressWarnings("deprecation")
			@Override
			public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
				Query query=null;
				if(null!=pageBean&&pageBean.isPagintaion()) {
					String countSql=getCountSql(sql);
					query=session.createNativeQuery(countSql);
					setParamters(params, query);
					List<Object> list = query.list();
					pageBean.setTotal(Integer.parseInt(list.get(0).toString()));
				}
				query=session.createNativeQuery(sql);
				if(null!=pageBean&&pageBean.isPagintaion()) {
					query.setFirstResult(pageBean.getStartIndex());
					query.setMaxResults(pageBean.getRows());
				}
				setParamters(params, query);
				query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}
	
	/**
	 * 查询(支持分页)
	 * @param hql       普通hql语句
	 * @param params    请求参数
	 * @param pageBean  分页对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List executeQuery(String hql,Map<String,Object> params,PageBean pageBean) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query query=null;
				//1.根据满足条件查询总记录数
				if(null!=pageBean&&pageBean.isPagintaion()) {
					String countHql=countSql(hql);
					query = session.createQuery(countHql);
					setParamters(params, query);
					List<Object> list = query.list();
					pageBean.setTotal(Integer.parseInt(list.get(0).toString()));
				}
				query=session.createQuery(hql);
				//2.根据满足条件查询分页记录
				if(null!=pageBean&&pageBean.isPagintaion()) {
					query.setFirstResult(pageBean.getStartIndex());
					query.setMaxResults(pageBean.getRows());
				}
				setParamters(params, query);
				return query.list();
			}
		});
	}
}
