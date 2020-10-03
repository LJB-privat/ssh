package com.zking.ssh.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PageBean {

	//页码
	private int page=1;
	//每页显示多少条记录
	private int rows=10;
	//总记录数
	private int total=0;
	//是否分页，分页标记，默认分页
	private boolean pagintaion=true;
	//上一次的请求路径
	private String url;
	//上一次的请求参数
	private Map<String,String[]> map;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String[]> getMap() {
		return map;
	}
	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isPagintaion() {
		return pagintaion;
	}
	public void setPagintaion(boolean pagintaion) {
		this.pagintaion = pagintaion;
	}
	public PageBean() {
		super();
	}
	
	public void setPage(String page) {
		if(null!=page&&!"".equals(page))
			this.page=Integer.parseInt(page);
	}
	public void setRows(String rows) {
		if(null!=rows&&!"".equals(rows))
			this.rows=Integer.parseInt(rows);
	}
	public void setPagintaion(String pagination) {
		if(null!=pagination&&!"".equals(pagination))
			this.pagintaion=Boolean.parseBoolean(pagination);
	}
	
	/**
	 * 获取sql语句的开始查询位置
	 * @return
	 */
	public int getStartIndex() {
		//(1-1)*10=0   limit 0,10
		//(2-1)*10=10  limit 10,10
		return (this.page-1)*this.rows;
	}
	
	/**
	 * 获取最大页码
	 * @return
	 */
	public int getMaxPager() {
		int maxPager=this.total/this.rows;
		if(this.total%this.rows!=0)
			maxPager++;
		return maxPager;
	}
	
	/**
	 * 上一页
	 * @return
	 */
	public int getProviousPager() {
		int provPager=this.page-1;
		if(provPager<=1)
			provPager=1;
		return provPager;
	}
	
	/**
	 * 下一页
	 * @return
	 */
	public int getNextPager() {
		int nextPager=this.page+1;
		if(nextPager>=getMaxPager())
			nextPager=getMaxPager();
		return nextPager;
	}
	
	public void setRequest(HttpServletRequest req) {
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String pagination=req.getParameter("pagintaion");
		
		//设置参数
		this.setPage(page);
		this.setRows(rows);
		this.setPagintaion(pagination);
		//设置url请求路径
		this.url=req.getRequestURI();
		//设置请求参数
		this.map=req.getParameterMap();
	}
	
	@Override
	public String toString() {
		return "PageBean [page=" + page + ", rows=" + rows + ", total=" + total + ", pagintaion=" + pagintaion
				+ ", url=" + url + ", map=" + map + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
