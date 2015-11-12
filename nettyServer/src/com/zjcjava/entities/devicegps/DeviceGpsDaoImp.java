package com.zjcjava.entities.devicegps;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.xy.game.message.MessageManager;
import com.zjcjava.entities.devicegps.DeviceGps;
import com.zjcjava.utils.StringUtils;

public class DeviceGpsDaoImp implements DeviceGpsDao {

	
	//需要向dao实现类中注入sqlSessionFactory
	private SqlSessionFactory sqlSessionFactory;
	//在这里用构造方法注入
	public DeviceGpsDaoImp(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public List<DeviceGps> getAllDeviceGps() throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		List <DeviceGps> user = session.selectList("com.zjcjava.entities.devicegps.DeviceGpsMapper.getAllDeviceGps");
		session.commit();
		session.close();
		return user;
	}

	@Override
	public DeviceGps getDeviceGps(int id) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		DeviceGps user = session.selectOne("com.zjcjava.entities.devicegps.DeviceGpsMapper.getDeviceGps", id);
		session.commit();
		session.close();
		return user;
	}

	@Override
	public int insertDeviceGps(DeviceGps deviceGps) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int success = session.insert("com.zjcjava.entities.devicegps.DeviceGpsMapper.insertDeviceGps",deviceGps);
		session.commit();
		session.close();
		return success;
	}

	@Override
	public int updateDeviceGps(DeviceGps deviceGps) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int success = session.insert("com.zjcjava.entities.devicegps.DeviceGpsMapper.updateDeviceGps",deviceGps);
		session.commit();
		session.close();
		return success;
	}

	@Override
	public int insertOrUpdateDeviceGps(DeviceGps deviceGps) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int success=0;
		
		if(StringUtils.isNullOrEmpty(deviceGps.getId())){///如果主键为空则插入数据
			success= session.insert("com.zjcjava.entities.devicegps.DeviceGpsMapper.insertDeviceGps",deviceGps);
		}else{
			success = session.insert("com.zjcjava.entities.devicegps.DeviceGpsMapper.updateDeviceGps",deviceGps);
		}
		session.commit();
		session.close();
		return success;
	}

}
