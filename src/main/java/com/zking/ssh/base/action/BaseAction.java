package com.zking.ssh.base.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseAction implements ServletRequestAware, ServletResponseAware {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		// 设置浏览器字符集编码. 
		this.response.setContentType("application/json;charset=UTF-8");
		// 设置response的缓冲区的编码.
		this.response.setCharacterEncoding("UTF-8");
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();
		this.application=request.getServletContext();
	}
	public void toJSONMessage(String msg,boolean success){
		try {
			Map<String,Object> json=new HashMap<String,Object>();
			json.put("msg", msg);
			json.put("success", success);
			mapper.writeValue(response.getOutputStream(), json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void toJSONPager(String msg,boolean success,
			int total,Object data){
		try {
			Map<String,Object> json=new HashMap<String,Object>();
			json.put("msg", msg);
			json.put("success", success);
			json.put("total", total);
			json.put("rows", data);
			mapper.writeValue(response.getOutputStream(),json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void toJSONObject(boolean success,Object data){
		try {
			Map<String,Object> json=new HashMap<String,Object>();
			json.put("success", success);
			json.put("data", data);
			mapper.writeValue(response.getOutputStream(),json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
