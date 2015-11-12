package com.zjcjava.netty.request;

import java.io.Serializable;
/*************
 * 根据客户端传递过来的token判断是否是合法用户
 */
public class SecureModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108336644101910071L;
	/**
	 * 验证 token 
	 */
	private String token ;
	
	private boolean autoSuccess;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isAutoSuccess() {
		return autoSuccess;
	}
	public void setAutoSuccess(boolean autoSuccess) {
		this.autoSuccess = autoSuccess;
	}
	
	
}
