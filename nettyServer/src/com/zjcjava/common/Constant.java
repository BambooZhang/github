package com.zjcjava.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sun.util.logging.resources.logging;

import com.zjcjava.netty.server.StartServer;
import com.zjcjava.test.Test;
import com.zjcjava.utils.FileTransferProperties;
import com.zjcjava.utils.Tools;

/**********
*Title: minaServer
*Description: 服务器启动时初始化常量参数
*Copyright: Copyright (c) 2015
*Company:www.xxx.com 
*Makedate:2015年10月9日 上午10:46:35
* @author zjcava
* @version 1.0
* @since 1.0 
*
 */
public class Constant {

	
	private Logger log = Logger.getLogger(StartServer.class);
	
	/**配置文件实体*/
	public static Tools utility;
	/**数据库url*/
	//public static String mysql_url = null;
	/**数据库用户名和密码信息*/
	//public static Properties connInfo;
	///mybatais sqlSqlSession session 
	public static SqlSession sqlSession ;
	public static SqlSessionFactory  sqlSessionFactory  ;
	//webPaht
	public static Integer server_port =9000;


	/*常量初始化获取当前的配置文件</br>
	 * systemConfig.properties：属性文件默认存放目录为文件根目录下的config文件夹中
	 */
	public static void init() {
		////加载系统配置
		FileTransferProperties.load("config" + java.io.File.separator + "systemConfig.properties");
		//请把加载属性文件放在 加载日志配置的上面，因为读取日志输出的目录配置在 属性文件里面
		PropertyConfigurator.configure("config" + java.io.File.separator + "log4j.properties");
		// TODO Auto-generated method stub
		Constant.utility = new Tools();
//		Constant.connInfo = new Properties();
		
		//获取服务器信息,默认为9000
		Constant.server_port= FileTransferProperties.getInteger("server_port", 9000);//获取服务器配置端口号
			
		
		//获取数据库配置信息
//		Constant.mysql_url = FileTransferProperties.getString("jdbc_url");//获取数据库配置URL
//		Constant.connInfo.put("user", FileTransferProperties.getString("jdbc_user"));//获取数据库用户名
//		Constant.connInfo.put("password", FileTransferProperties.getString("jdbc_pwd"));//获取数据库密码
		
		//mybatis的配置文件
        String resource = "config.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = Test.class.getResourceAsStream(resource);
        FileInputStream fis=null;
        try {
			fis = new FileInputStream(new File("config" + java.io.File.separator + "mybatisConfig.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //构建sqlSession的工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(fis);
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource); 
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
       	//sqlSession = sqlSessionFactory.openSession();
		
		
	}
	
	
	///消息类型常量定义
	public static final String CODE_SEARCH="U";
	public static final String CODE_NONE="300";///不存在此命令
	public static final String SERVER_EROR="500";///服务器异常
	
	
}
