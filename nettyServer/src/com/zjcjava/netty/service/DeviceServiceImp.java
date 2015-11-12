package com.zjcjava.netty.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.xy.game.message.MessageManager;
import com.zjcjava.common.Constant;
import com.zjcjava.entities.devicegps.DeviceGps;
import com.zjcjava.entities.devicegps.DeviceGpsDao;
import com.zjcjava.entities.devicegps.DeviceGpsDaoImp;
import com.zjcjava.entities.serverdevice.ServerDevice;
import com.zjcjava.entities.serverdevice.ServerDeviceDao;
import com.zjcjava.entities.serverdevice.ServerDeviceImpl;
import com.zjcjava.netty.request.DeviceInf;
import com.zjcjava.netty.server.StartServer;
import com.zjcjava.utils.StringUtils;

public class DeviceServiceImp implements DeviceService {

	Logger logger  =  Logger.getLogger(StartServer. class );
	
	
	@Override
	public String ServerDeviceHandle(DeviceInf df) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer msg= new StringBuffer().append("-U");
		if(StringUtils.isNullOrEmpty(df.getDevice_id())){
			msg.append("msg=设备ID为空");
			logger.debug("设备ID为空.........");
		}else{
			ServerDevice serverDevice=new ServerDevice();
			serverDevice.setDevice_id(df.getDevice_id());//device_id;//
			serverDevice.setImei(df.getImei());/// imei;//
			serverDevice.setImsi(df.getImsi());/// imsi;//
									///   uuid;//
			serverDevice.setSn(df.getSn());///  sn;//
			/// p_name;//
			/// rfid;//
			/// iccid;//
			/// soft_version;//
			/// sys_version;//
			/// tcp;//
			/// udp;//
			///  power;//
			///  contact_size;//
			///  tl_build;
			
			ServerDevice sd= MessageManager.serverDeviceSet.get(df.getDevice_id());
			if(sd!=null){
				serverDevice.setId(sd.getId());
			}
			 ServerDeviceDao serverDeviceDao = new ServerDeviceImpl(Constant.sqlSessionFactory);  
		     serverDeviceDao.insertOrUpdateServerDevice(serverDevice);
		     msg.append("msg=success");
		     logger.debug("设备更新成功.........");
		}
		
		
		
		return msg.append("-").toString();
	}


	@Override
	public String DeviceGpsHandle(DeviceGps gps) throws Exception {
		// TODO Auto-generated method stub
		DeviceGpsDao deviceGpsDao = new DeviceGpsDaoImp(Constant.sqlSessionFactory);  
		deviceGpsDao.insertDeviceGps(gps);
	     //msg.append("msg=success");
	     logger.debug("GPS更新成功.........");
	    return "";
	}

}
