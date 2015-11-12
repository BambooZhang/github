package com.zjcjava.netty.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjcjava.netty.request.DeviceInf;
import com.zjcjava.netty.request.RequestFile;
import com.zjcjava.netty.request.ResponseFile;
import com.zjcjava.netty.service.DeviceService;
import com.zjcjava.netty.service.DeviceServiceImp;
import com.zjcjava.netty.service.MsgHandleService;
import com.zjcjava.utils.FileTransferProperties;
import com.zjcjava.utils.Tools;

public class ServerHandler extends ChannelHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	private volatile int byteRead;
	private volatile long start = 0;
	
	/**
	 * 文件默认存储地址
	 */
	private String file_dir = FileTransferProperties.getString("file_write_path","/");
	private RandomAccessFile randomAccessFile; 
	private File file ;
	private long fileSize = -1 ;
	private StringBuffer  msgBuf=new StringBuffer();
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		MsgHandleService.channelGroup.add(ctx.channel());///登陆的设备放入gourp中
		MsgHandleService.userMap.put(ctx.channel().id().toString(), ctx);
		
		System.out.println("登录"+MsgHandleService.channelGroup.size()+"客户端IP:"+ctx.channel().remoteAddress());
		log.info("登录服务器"+ctx.channel().remoteAddress());
		//ctx.writeAndFlush("-Uweclcomelogin-");
		MDC.put("userId",ctx.channel().id().toString());///合法用户登录
        MDC.put("userType",2);
        MDC.put("ip",ctx.channel().remoteAddress().toString());///合法用户登录
        MDC.put("port",000);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		MsgHandleService.channelGroup.remove(ctx.channel());
		MsgHandleService.userMap.remove(ctx.channel().id().toString());
		MsgHandleService.userList.remove(ctx.channel().id().toString());
		System.out.println("退出");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		
		 System.out.println("服务器读取到客户端请求...");
		 
		 //ctx.write(msg);
		 //判断消息类型
		 if(msg!=null){
			 if(!msg.toString().contains("\n-")&&!msg.toString().endsWith("\n")){///不包含换行符并且不是命令结束行
				 msgBuf.append(msg);
				 log.debug("报文正在进行粘包处理中.....");
				 System.out.println("报文正在进行粘包处理中.....");
			 }else {///如果其中包含\n则进行切分处理
				 String[]msgArry=msg.toString().split("\n-");
				 for (int i = 0; i < msgArry.length; i++) {
					 boolean flage=false;///是否进行命令处理
					
					 msgBuf.append((i>0?"-":"")+msgArry[i]+(i<(msgArry.length-1)?"\n":""));///进行处理,把截取掉的"-"和"\n"补上，
					if(i<(msgArry.length-1)||(i==(msgArry.length-1)&&msg.toString().endsWith("\n"))){///最后一条如果包含了\n
						flage=true;
					}
					if(flage){////进行命令处理
						
						/*******msg处理START************/
						 log.debug("报文粘包处理完毕.....");
						 System.out.println("报文粘包处理完毕.....");
						 System.out.println("msgBuf.toString()");
						 Map<String, Object> deMsg=Tools.codeToString(msgBuf.toString());
						 msgBuf=new StringBuffer();///清空字符串
						 switch (deMsg.get("codeType").toString()) {
						 ///处理设备端口提交上来的业务数据Start
							 case  "F":///	F	上传文件	-F&id=01000001-
								 deMsg.remove("codeType");
								 RequestFile ef=new RequestFile();
								 Tools.mapToObject(ef, deMsg);
								 System.out.println(ef.getFile_name());
								 
								 //处理文件上传
								 if (!ef.getFile_name().isEmpty()) {
										//RequestFile ef = (RequestFile) msg;
										byte[] bytes = ef.getFileBytes();
										byteRead = ef.getEndPos();
										start= ef.getStarPos();
										fileSize = ef.getFile_size();
										
										String md5 = ef.getFile_md5();//文件MD5值
										System.out.println(start);
										if(start == 0){ //只有在文件开始传的时候才进入 这样就减少了对象创建 和可能出现的一些错误
											String path = file_dir + File.separator + md5+ef.getFile_type();
											file = new File(path);
											
											
											//根据 MD5 和 文件类型 来确定是否存在这样的文件 如果存在就 秒传
											if( file.exists() ) {
												log.info("file exists:" + ef.getFile_name()+"--" +ef.getFile_md5() +"[" + ctx.channel().remoteAddress()+"]");
												ResponseFile responseFile = new ResponseFile(start,md5,getFilePath());
												//ctx.writeAndFlush(Tools.objctToCode("F",responseFile)+"\n");//返回带有F的报文信息
												ctx.writeAndFlush("-Fstart="+start+"&file_md5="+md5+"-\n");//返回带有F的报文信息
												
												//TODO 这里可以做 断点续传 ，读取当前已经存在文件的总长度  和 传输过来的文件总长度对比 如果不一致，则认为本地文件没有传完毕 则续传
												// 不过这步骤必须做好安全之后来做，否则可能会出现 文件被恶意加入内容
												return ;
											}
											
											randomAccessFile = new RandomAccessFile(file, "rw");///设置文件具有读写权限
										}
										
										randomAccessFile.seek(start);
										randomAccessFile.write(bytes);
										start = start + byteRead;
										
										if (byteRead > 0 && (start < fileSize && fileSize != -1)) {
											//log.info((start*100)/fileSize+"::::" +fileSize+"::: " +(start*100));
											ResponseFile responseFile = new ResponseFile(start,md5,(start*100)/fileSize);
											//ctx.writeAndFlush(Tools.objctToCode("F",responseFile)+"\n");//返回带有F的文件上传进度报文信息
											ctx.writeAndFlush("-Fstart="+start+"&file_md5="+md5+"-\n");//返回带有F的报文信息
										} else {
											start=-1;
											log.info("create file success:" +ef.getFile_name()+"--" +ef.getFile_md5() +"[" + ctx.channel().remoteAddress() +"]");
											
											ResponseFile responseFile = new ResponseFile(start,md5,getFilePath());
											//ctx.writeAndFlush(Tools.objctToCode("F",responseFile)+"\n");//返回带有F的报文信息
											ctx.writeAndFlush("-Fstart="+start+"&file_md5="+md5+"-\n");//返回带有F的报文信息
											
											randomAccessFile.close();
											file = null ;
											fileSize = -1;
											randomAccessFile = null;
											//ctx.close();  这步让客户端来做
										}
									}
								 
								 
								 
								 break;
							case  "U":///	U	更新设备状态	-U&id=01000001-
									deMsg.remove("codeType");
									DeviceInf df=new DeviceInf();
									Tools.mapToObject(df, deMsg);
									
									System.out.println("设备信息开始："+df.getDevice_id());
									DeviceService deviceService = new DeviceServiceImp();
									String msgStr=deviceService.ServerDeviceHandle(df);
									System.out.println("设备信息结束："+msgStr);
									
									ctx.writeAndFlush(msgStr+"\n");
									break;
							case  "N":///	N	更新设备网络参数	-N121.37.42.116:7002-
									break;
							case  "S":///	S	发送短信	-S15220202558,13688993300-hello-
									break;
							case  "C":///	C	拨打电话	-C10086-
									break;
							case  "G":///	G	提交位置	-G-
									break;
							case  "L":///	L	更新设备位置	-L广东省深圳市罗湖区京基100大厦南方20米-
									break;
							case  "K":///	K	同步亲情号码按键	-KA123,B,C234,D789-
									break;
							case  "W":///	W	同步白名单	-W13692839182,15219182875-
									break;
							case  "T":///	T	设置免打扰时间段	-TA127-09001100,14001600-
									break;
							case  "X":///	X	设置设备休眠	-X1,20,7-
									break;
							case  "E":///	E	恢复出厂设置	-E-
									break;
						///处理设备端口提交上来的业务数据END
						////****************************************/////			
						///处理APP提交上来对设备端进行下行业务数据START
							case  "LOCATION":///	更新设备GPS
								////TODO
								break;
						///处理APP提交上来对设备端进行下行业务数据END
							 default://无法处理
					               /* responseMsg=new RequestMsg();
					                responseMsg.setCode(Constant.CODE_NONE);
					                responseMsg.setMessage("指令无法处理！");*/
					            break;
					            
					            
					            
					            
						 }
						
						/*******msg处理END************/
					}
					
					
				}
				 
			 }
			 
			 
		 }
		 
		/*if(msg.toString().endsWith("\n")){///因为每条命令行结束处有\n
			///当命令行解析成功时进行业务处理
			 msgBuf.append(msg);///报文处理完毕
			 
			 
		 }*/
		 
		 
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("客户端端开");
		//当连接断开的时候 关闭未关闭的文件流
		if(randomAccessFile != null ){
			try {
				randomAccessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ctx.close();
	}
	
	
	/**
	 * 获取 文件路径
	 * @return
	 */
	private String getFilePath(){
		if( file != null )
			return FileTransferProperties.getString("download_root_path") +"/" + file.getName();
		else 
			return null ;
	}
}
