package com.xy.game.message;

import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zjcjava.common.Constant;
import com.zjcjava.entities.serverdevice.ServerDevice;
import com.zjcjava.entities.serverdevice.ServerDeviceDao;
import com.zjcjava.entities.serverdevice.ServerDeviceImpl;
import com.zjcjava.utils.Tools;

public class MessageManager {
	
	private static Logger log  =  Logger.getLogger(MessageManager. class );
	
	/**消息类型配置文件*/
	private static Tools properties;
	
	/**会话*/
	private ChannelHandlerContext session;		
	
	/**消息*/
	private JSONObject message;	
	
	/**session集合*/
	public static HashMap<Long, ChannelHandlerContext> sessionSet = new HashMap<Long, ChannelHandlerContext>();
	
	/**已成功登陆的用户设备session集合*/
	public  static HashMap<String, ChannelHandlerContext> devideSet = new HashMap<String, ChannelHandlerContext>();
	
	/**已存在的设备ServerDevice集合*/
	public  static HashMap<Integer, ServerDevice> serverDeviceSet = new HashMap<Integer, ServerDevice>();
	
	public MessageManager(ChannelHandlerContext session, Object message){
		/*this.session = session;
		if(message!=null&&!"".equals(message)){
			this.message = JSONObject.fromObject(message.toString());
			
			// 拿到所有的客户端Session
	        Collection<ChannelHandlerContext> sessions = session;
	        // 向所有客户端发送数据
	        for (ChannelHandlerContext sess : sessions) {
	            sess.write(message.toString());
	        }
			
		}else{
			this.message =null;
		}*/
		
	}

	
	public static void init() throws Exception{

		log.info("开始初始化服务设备信息>>>>");
        /**
         * 方法1映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        //String statement = "com.zjcjava.entities.serverDeviceMapper.getServerDevice";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
       // List<Object> user = session.selectList(statement, 1);
//        ServerDevice user = Constant.sqlSession.selectOne(statement, 1);
//        System.out.println(user);
     
        ////方法2使用serviceDAO映射调用sql查询,xml中UserMapper改为接口名称
      //  ServerDeviceDao modelService=Constant.sqlSession.getMapper(ServerDeviceDao.class);
        ///方法3 注入dao实现
        ServerDeviceDao serverDeviceDao = new ServerDeviceImpl(Constant.sqlSessionFactory);  
        List<ServerDevice> userList = serverDeviceDao.getAllServerDevice();
        for (ServerDevice serverDevice : userList) {
        	serverDeviceSet.put(serverDevice.getDevice_id(), serverDevice);///放入数据集中
		}
        
        System.out.println(userList.size()+"---------------");
        ServerDevice s= new ServerDevice();
        s.setId	(2);
        s.setDt(3);
        s.setTz	(4);
        s.setGps_switch	(5);
        s.setBt_switch	(6);
        s.setIncoming	(7);
        s.setRecord	(8);
        s.setCall_monitor	(9);
        s.setCall_monitor_no	(10);
        s.setUdp_cycle	(11);
        s.setCtrl_light(12);
        s.setCtrl_voice	(13);
        s.setSync	(14);
        s.setRestart(15);
        s.setTrack	(16);
        s.setUpload_loc	(17);
        s.setSos	(18);
        s.setUdp_wifi	(19);
        s.setShutdown	(20);
        s.setNormal_key	(21);
        s.setYaoyao	(22);
        s.setKtone	(23);
        s.setMode_fly	(24);
        s.setLang	(25);
        
        //serverDeviceDao.addServerDevice(s);
        
        List<ServerDevice> userList1 = serverDeviceDao.getAllServerDevice();
        System.out.println(userList1.size()+"----------22222-----");
        log.info("结束初始化服务设备信息>>>>");
        
	}
	
	/**
	 * 接收消息，并将消息发送到相对应的消息处理者处理
	 */
	public void msgTransfer() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//String s = properties.getStringFromProperty(message.getInt("msgType") + "");
//		Class<?> transmission = Class.forName(properties.getStringFromProperty(message.getInt("msgType") + ""));
//		Constructor<?> test = transmission.getConstructor(ChannelHandlerContext.class, JSONObject.class);
//		AbstractHandler handler = (AbstractHandler) test.newInstance(session, message);
//		handler.handle();
	}
}

