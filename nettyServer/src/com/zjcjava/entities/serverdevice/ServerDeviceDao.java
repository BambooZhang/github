package com.zjcjava.entities.serverdevice;

import java.util.List;

import com.zjcjava.entities.serverdevice.ServerDevice;


/*********
*Title: project_name 
*Description: XXXX 
*Copyright: Copyright (c) 2015
*Company:www.xxx.com 
*Makedate:2015年10月19日 下午5:35:40
* @author Administrator 
* @version %I%, %G% 
* @since 1.0 
*
 */
public interface  ServerDeviceDao {
	
	///根据
	public List <ServerDevice> getAllServerDevice() throws Exception;
	
	public ServerDevice getServerDevice(int id) throws Exception;
	
	///此方法作废，使用insertServerDevice
	 @Deprecated
	public int addServerDevice(ServerDevice serverDevice) throws Exception;
	
	public int insertServerDevice(ServerDevice serverDevice) throws Exception;
	
	public int updateServerDevice(ServerDevice serverDevice) throws Exception;
	
	public int insertOrUpdateServerDevice(ServerDevice serverDevice) throws Exception;
	
	
	
	
}
