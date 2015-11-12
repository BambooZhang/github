package com.zjcjava.netty.server;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.xy.game.message.MessageManager;
import com.zjcjava.common.Constant;
import com.zjcjava.entities.devicegps.DeviceGps;
import com.zjcjava.entities.devicegps.DeviceGpsDao;
import com.zjcjava.entities.devicegps.DeviceGpsDaoImp;
import com.zjcjava.entities.serverdevice.ServerDevice;
import com.zjcjava.entities.serverdevice.ServerDeviceDao;
import com.zjcjava.entities.serverdevice.ServerDeviceImpl;
import com.zjcjava.netty.service.DeviceService;
import com.zjcjava.netty.service.DeviceServiceImp;
import com.zjcjava.utils.FileTransferProperties;

public class StartServer {

	@SuppressWarnings("unused")
	
	public static void main(String[] args) throws Exception  {
		
		MDC.put("userId",1);///合法用户登录
        MDC.put("userType",1);
        MDC.put("ip","127.0.0.1");///合法用户登录
        MDC.put("port",9000);
		
		/**系统配置信息初始化*/
		Constant.init();
		Logger logger  =  Logger.getLogger(StartServer. class );
		logger.debug("服务器启动初始化完成.........");
		/**硬盘数据库初始化配置信息*/
//		ConnectionPoolManager.JDBCInit();
//		logger.debug("服务器连接池初始化完成.........");
		/**消息信息初始化*/
		try {
			MessageManager.init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		
//		/**内存数据库初始化*/
//		System.out.println("等待内存数据库初始化...");
//		DBManager.dbInit();
		
		logger.debug("服务器数据库信息进入内存初始化.........");
		logger.error("服务器数据库信息进入内存初始化.........");
		logger.info("服务器数据库信息进入内存初始化.........");
//		System.out.println("ok！！！");
//		
//		/**TCP长连接服务，即游戏服务器（含登陆服务器）*/
//		MinaNetworkManager network = new MinaNetworkManager();
//		network.startNetwork();
		
		
//		Logger log  =  Logger.getLogger(StartServer. class );
		
		///初始化
		init();
		// 获取端口
		int port  = Constant.server_port;//如果配置信息为空则设置为9000
		System.out.println(port);
		 
		 logger.debug("服务器启动端口号"+port);
		NettyServer ns = new NettyServer();
		
		try {
			
			ns.bing(port);
			//log.info("服务器启动.........");
			System.out.println(port+"服务器启动.........");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*DeviceInf df=new DeviceInf();
		df.setDevice_id(1008);
		df.setImei("55455");
		df.setImsi("55455");
		///df.setUuid("55555");
		DeviceService deviceService = new DeviceServiceImp();  
		String msgStr=deviceService.ServerDeviceHandle(df);*/
		
		
		///GPS 添加
		/*DeviceGps gps=new DeviceGps();
		gps.setDevice_id(1008);
		gps.setLng("120.558957");
		gps.setLat("31.325152");
		DeviceService deviceService = new DeviceServiceImp();  
		String msgStr=deviceService.DeviceGpsHandle(gps);

		///GPS 查询
		DeviceGpsDao serverDeviceDao = new DeviceGpsDaoImp(Constant.sqlSessionFactory);  
		DeviceGps g= serverDeviceDao.getDeviceGps(1);
		System.out.println(g.getLat()+g.getLng());*/
		
	}
	
	
	
	private static void init(){
		
		
		System.setProperty("WORKDIR", FileTransferProperties.getString("WORKDIR","/"));
		
		//PropertyConfigurator.configure("config" + java.io.File.separator + "log4j.xml");
	}
	
}
