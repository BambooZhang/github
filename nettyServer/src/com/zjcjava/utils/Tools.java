package com.zjcjava.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import sun.misc.BASE64Decoder;

/**
 * 工具:1.获取资源配置文件; 2.获取资源配置文件的具体字段; 3.填充PreparedStatement;
 */
public class Tools {


	

	/**
	 * 初始化配置文件的路径并获取配置文件流
	 */
	public static InputStream getFileInputStream(String file) {
		InputStream fis = null;
		try {
		fis = new FileInputStream(new File(file));
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + " " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fis;
	}

	/**
	 * 填充PreparedStatement中的未知项
	 * 
	 * @return 返回填充后的PreparedStatement
	 */
	public static PreparedStatement setPreStatementItems(
			PreparedStatement preStat, Object... inserts) {
		try {
			if (inserts == null) {
				return preStat;
			}
			int i = 1;
			for (Object v : inserts) {
				if (v.getClass().getSimpleName().equals("Integer")) {// 判断是整形
					preStat.setInt(i, (Integer) v);
					i++;
				} else if (v.getClass().getSimpleName().equals("String")) {// 判断是字符串
					preStat.setString(i, (String) v);
					i++;
				} else if (v.getClass().getSimpleName().equals("Long")) {
					preStat.setLong(i, (long) v);
					i++;
				} else
					continue;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preStat;
	}

	/**
	 * 获取字符串长度
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	/**
	 * 获得一个随机序列
	 */
	public static int[] getRandomSequence(int rangeSize, int queueSize) {
		if (rangeSize < queueSize) {
			return null;
		}
		Random rd = new Random();
		int[] sequence = new int[rangeSize];
		for (int i = 0; i < rangeSize; i++)
			sequence[i] = i;
		int[] output = new int[queueSize];
		int end = rangeSize - 1;
		for (int i = 0; i < queueSize; i++) {
			int num = rd.nextInt(end + 1);
			output[i] = sequence[num];
			sequence[num] = sequence[end];
			end--;
		}
		return output;
	}

	/***********
	 * 命令报文转为键值对格式：<br>
	 * eg:-Uid=010000010&dt=1211091800&gps_switch=1-转为 map键值对类型
	 * 
	 * @param code
	 *            ：-Uid=010000010&dt=1211091800&gps_switch=1-
	 * @return Map :codeType报文类型
	 */
	@SuppressWarnings("unused")
	public static Map<String, Object> codeToString(String code) {
		Map<String, Object> codeMap = new HashMap<String, Object>();
		// /验证code是否是合法的-code-格式
		code=code!=null?code.replace("\n", ""):null;
		if (code != null && code.indexOf("-") != -1
				&& code.lastIndexOf("-") == (code.length() - 1)) {
			String deMsg = code.toString().substring(1,
					code.toString().length() - 1);
			codeMap.put("codeType", deMsg.substring(0, 1));// /U,N,S,C,G,L,K,W,T,X,E
			deMsg = deMsg.substring(1, deMsg.length());
			if (deMsg.indexOf("&") != -1) {// /多个键值对
				String[] argsList = deMsg.split("&");
				for (String kv : argsList) {
					String[] args = kv.split("=");
					codeMap.put(args[0], args[1]);// /放入键值对
				}
			} else if (deMsg.indexOf("=") != -1){// /此时只有一个键值对
				String[] args = deMsg.split("=");
				codeMap.put(args[0], args[1]);// /放入键值对
			}

			return codeMap;
		} else {
			return null;
		}
	}

	/***********
	 * object转为命令报文格式：<br>
	 * eg:new User(id,name)转为报文命令类型-Uid=010000010&dt=1211091800&gps_switch=1-
	 * @param <T>
	 * 
	 * @param prefx eg:U
	 * @param code  eg:new user(id,name)
	 *            
	 * @return String eg:返回报文类型为-Uid=010000010&name=1211091800-
	 */
	@SuppressWarnings("unused")
	public static <T> String objctToCode(String prefx,T t) {
		if (t == null)
			return null;
		StringBuffer codeBuf=new StringBuffer();
		codeBuf.append("-"+prefx.toUpperCase());///前缀大写
		Class<?> clazz = t.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName(); // 获取属性的名字
					// 注意下面这句，不设置true的话，不能修改private类型变量的值
					if(!name.equals("serialVersionUID")){
						fields[i].setAccessible(true);
						Object value=fields[i].get(t);
						codeBuf.append(name+"="+value+"&");
					}
						
				}
			} catch (Exception e) {
			}
		}
		if(codeBuf.indexOf("=")!=-1){
			String code=codeBuf.substring(0, codeBuf.lastIndexOf("&"))+"-";
			return code;
		}else{
			return null;
		}
		
	}
	
	
	/*
	 * 将Map形式的键值对中的值转换为泛型形参给出的类中的属性值 t一般代表pojo类</br>
	 * 对于特殊类型的fileBytes文件biyte字节流要求传送时使用BASE64处理为String类型，这里对其String进行BASE64解压
	 * @param t
	 * 
	 * @param params
	 */
	public static <T extends Object> void mapToObject(T t,Map<String, Object> params) {
		if (params == null || t == null)
			return;
		Class<?> clazz = t.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName(); // 获取属性的名字
					// if(logger.isDebugEnabled())
					// logger.debug(ReflectUtils.class +
					// "method flushObject attribute name:" + name + "  ");
					Object value = params.get(name);
					if (value != null && !"".equals(value)) {
 						// 注意下面这句，不设置true的话，不能修改private类型变量的值
 						fields[i].setAccessible(true);
 						if(fields[i].getType().getSimpleName().equals("String")){
 							fields[i].set(t, value.toString());
 						}else if(fields[i].getType().getSimpleName().equals("Integer")){
 							fields[i].set(t, Integer.valueOf(value.toString()));
 						}else if(fields[i].getType().getSimpleName().equals("int")){
 							fields[i].set(t, Integer.parseInt(value.toString()));
 						}else if(fields[i].getType().getSimpleName().equals("Double")){
 							fields[i].set(t, Double.valueOf(value.toString()));
 						}else if(fields[i].getType().getSimpleName().equals("double")){
 							fields[i].set(t, Double.parseDouble(value.toString()));
 						}else if(fields[i].getType().getSimpleName().equals("Long")){
							fields[i].set(t, Long.valueOf(value.toString()));
						}else if(fields[i].getType().getSimpleName().equals("long")){
							fields[i].set(t, Long.parseLong(value.toString()));
						}else if(fields[i].getType().getSimpleName().equals("byte[]")){
 							if("fileBytes".equals(name)){///文件的字节流使用base64解码
 								BASE64Decoder decoder = new BASE64Decoder();
 								fields[i].set(t, decoder.decodeBuffer(value.toString()));
 							}else{///其他类型则进行普通byte位转换
 								fields[i].set(t, Byte.valueOf(value.toString()));
 							}
							
						}
 						
 					}
				}
			} catch (Exception e) {
			}
		}
	}
	
	public static String byte2hex(byte[] b) // 二进制转字符串  
	{  
	   StringBuffer sb = new StringBuffer();  
	   String stmp = "";  
	   for (int n = 0; n < b.length; n++) {  
	    stmp = Integer.toHexString(b[n] & 0XFF);  
	    if (stmp.length() == 1){  
	        sb.append("0" + stmp);  
	    }else{  
	        sb.append(stmp);  
	    }  
	      
	   }  
	   return sb.toString();  
	}  
	  
	public static byte[] hex2byte(String str) { // 字符串转二进制  
	    if (str == null)  
	     return null;  
	    str = str.trim();  
	    int len = str.length();  
	    if (len == 0 || len % 2 == 1)  
	     return null;  
	    byte[] b = new byte[len / 2];  
	    try {  
	     for (int i = 0; i < str.length(); i += 2) {  
	      b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();  
	     }  
	     return b;  
	    } catch (Exception e) {  
	     return null;  
	    }  
	 }  
}