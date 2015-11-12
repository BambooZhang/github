package com.zjcjava.entities.devicegps;

import com.zjcjava.entities.devicegps.DeviceGps;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeviceGpsDao {
   
	///根据
	public List <DeviceGps> getAllDeviceGps() throws Exception;
	
	public DeviceGps getDeviceGps(int id) throws Exception;
		
	public int insertDeviceGps(DeviceGps deviceGps) throws Exception;
	
	public int updateDeviceGps(DeviceGps deviceGps) throws Exception;
	
	public int insertOrUpdateDeviceGps(DeviceGps deviceGps) throws Exception;
	
}