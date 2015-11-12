package com.zjcjava.netty.request;

import java.io.Serializable;
import java.util.Date;
/**********
*
*Title: 客户端发送的报文请求信息 
*Description: 客户端发送的报文请求信息 
*Copyright: Copyright (c) 2015
*Company:www.xxx.com 
*Makedate:2015年10月12日 下午4:06:17
* @author zjcajva
* @version %I%, %G% 
* @since 1.0 
*
 */
public class RequestMsg implements Serializable {

	private static final long serialVersionUID = -7335293929249462183L;
	
    private String code;//报文类型
    private String message;//报文内容
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
	
    
    
    

	
}
