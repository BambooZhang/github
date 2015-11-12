package com.zjcjava.netty.service;

import java.util.List;

import com.zjcjava.entities.devicegps.DeviceGps;
import com.zjcjava.netty.request.DeviceInf;

public interface DeviceService {

	///根据
	public String ServerDeviceHandle(DeviceInf df) throws Exception;
	
	///GPS
	public String DeviceGpsHandle(DeviceGps gps) throws Exception;
}
