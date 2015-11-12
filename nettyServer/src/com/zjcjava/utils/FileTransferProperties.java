package com.zjcjava.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileTransferProperties
{
    private static Properties properties ;

    
    /**
	 * 1初始化配置文件的路径并获取最新的properties
	 */
	public void getProperties(String file) {
		try {
			properties = new Properties();
			FileInputStream fis = new FileInputStream(new File(file));
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + " " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取properties资源配置文件里定义的字段，例如：key = xxx
	 */
	public String getStringFromProperty(String key) {
		String str = null;
		if (properties != null) {
			str = properties.getProperty(key);
		}
		return str;
	}
    
	/**
	 * 2初始化配置文件的路径并获取最新的properties
	 */
    public static void load(String path)
    {
    	properties = new Properties();
    	try {
			FileInputStream fis = new FileInputStream(new File(path));
			properties.load(fis);
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
      
    }

    public static String getString(String key, String defaultValue)
    {
        String value = (String) properties.get(key);
        if (StringUtils.isEmpty(value))
        {
            return defaultValue;
        }
        return value;
    }
    
    public static String getString(String key)
    {
        String value = (String) properties.get(key);
        return value;
    }

    public static int getInt(String key, int defaultValue)
    {
        Object value = properties.get(key);
        if (StringUtils.isNullOrEmpty(value))
        {
            return defaultValue;
        }
        return Integer.parseInt(value.toString());
    }
    
    public static Integer getInteger(String key, Integer defaultValue)
    {
    	Object value = properties.get(key);
    	if (StringUtils.isNullOrEmpty(value))
    	{
    		return defaultValue;
    	}
    	return Integer.valueOf(value.toString());
    }

    public static boolean getBoolean(String key, boolean defaultValue)
    {
        Object value = (Boolean) properties.get(key);
        if (StringUtils.isNullOrEmpty(value))
        {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.toString());
    }
}
