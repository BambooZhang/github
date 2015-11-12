package com.zjcjava.entities.serverdevice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.xy.game.message.MessageManager;
import com.zjcjava.utils.StringUtils;

public class ServerDeviceImpl implements ServerDeviceDao{


		//需要向dao实现类中注入sqlSessionFactory
		private SqlSessionFactory sqlSessionFactory;
		//在这里用构造方法注入
		public ServerDeviceImpl(SqlSessionFactory sqlSessionFactory){
			this.sqlSessionFactory = sqlSessionFactory;
		}

		@Override
		public ServerDevice getServerDevice(int id) {
			SqlSession session = sqlSessionFactory.openSession();
			ServerDevice user = session.selectOne("com.zjcjava.entities.serverdevice.serverDeviceMapper.getServerDevice", id);
			session.commit();
			session.close();
			return user;
		}

		@Override
		public List <ServerDevice> getAllServerDevice() {
			// TODO Auto-generated method stub
			SqlSession session = sqlSessionFactory.openSession();
			List <ServerDevice> user = session.selectList("com.zjcjava.entities.serverdevice.serverDeviceMapper.getAllServerDevice");
			session.commit();
			session.close();
			return user;
		}

		@Override
		@Deprecated
		public int addServerDevice(ServerDevice serverDevice) {
			// TODO Auto-generated method stub
			SqlSession session = sqlSessionFactory.openSession();
			int success = session.insert("com.zjcjava.entities.serverdevice.serverDeviceMapper.addServerDevice",serverDevice);
			session.commit();
			MessageManager.serverDeviceSet.put(serverDevice.getDevice_id(), serverDevice);///放入数据集中
			session.close();
			return success;
		}

		@Override
		public int insertServerDevice(ServerDevice serverDevice) {
			// TODO Auto-generated method stub
			SqlSession session = sqlSessionFactory.openSession();
			int success = session.insert("com.zjcjava.entities.serverdevice.serverDeviceMapper.insertServerDevice",serverDevice);
			session.commit();
			MessageManager.serverDeviceSet.put(serverDevice.getDevice_id(), serverDevice);///放入数据集中
			session.close();
			return success;
		}

		@Override
		public int updateServerDevice(ServerDevice serverDevice) {
			// TODO Auto-generated method stub
			SqlSession session = sqlSessionFactory.openSession();
			int success = session.update("com.zjcjava.entities.serverdevice.serverDeviceMapper.updateServerDevice",serverDevice);
			session.commit();
			MessageManager.serverDeviceSet.put(serverDevice.getDevice_id(), serverDevice);///放入数据集中
			session.close();
			return success;
		}

		@Override
		public int insertOrUpdateServerDevice(ServerDevice serverDevice) {
			// TODO Auto-generated method stub
			SqlSession session = sqlSessionFactory.openSession();
			int success=0;
			
			if(StringUtils.isNullOrEmpty(serverDevice.getId())){///如果主键为空则插入数据
				success= session.insert("com.zjcjava.entities.serverdevice.serverDeviceMapper.insertServerDevice",serverDevice);
			}else{
				success = session.update("com.zjcjava.entities.serverdevice.serverDeviceMapper.updateServerDevice",serverDevice);
			}
			session.commit();
			MessageManager.serverDeviceSet.put(serverDevice.getDevice_id(), serverDevice);///放入数据集中
			session.close();
			return success;
		}

	}

