package com.zjcjava.utils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static boolean isNull(Object str){
		if ( str==null )
			return true;
		return false;
	}
	
	public static boolean isNullOrEmpty(Object obj){
		if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
	}
	
	public static boolean isEmpty(String str){
		if ( str==null || str.trim().equals("") )
			return true;
		return false;
	}
	
	public static boolean isNotNull(Object str){
		return !isNull(str);
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static String toUpperFirst(String src) {
		
		char l = Character.toUpperCase(src.charAt(0));
		return src.replaceFirst(String.valueOf(src.charAt(0)), String.valueOf(l));
	}
	
	public static String toLowerFirst(String src) {
		
		char l = Character.toLowerCase(src.charAt(0));
		return src.replaceFirst(String.valueOf(src.charAt(0)), String.valueOf(l));
	}
	
	public static String convertStackTrace(Exception e) {
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		return stringWriter.toString();
	}
	
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();
	}
	
	
	public static boolean match(String str) {
		Pattern p = Pattern.compile("^[0-9]+(.[0-9]*)?$");
		Matcher m = p.matcher(str.trim());
		return m.find();
	}

	public static String getClob(Clob c) {
		Reader reader;
		StringBuffer sb = new StringBuffer();
		try {
			reader = c.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	// 随机6位数字
	public static String Random6(){
		
		Random random = new Random();
		
		int x = random.nextInt(899999);
		
		x = x + 100000;
		
		return String.valueOf(x);
	}
	
	public static boolean startsWith(String str,String with){
		if(StringUtils.isEmpty(str))
			return false;
		return str.startsWith(with);
	}
	
	/**********
	 * //自动生成单号前缀+日期
	 * @param pre F
	 * @return F201510100101120101
	 */
	public static String createNO(String pre){
		
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH+1));
		String date = String.valueOf(c.get(Calendar.DATE));
		String day = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));
        String no =pre+year+month+date+day+minute+second;
        return no;
	}
}
