package com.zjcjava.netty.celient;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.logging.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zjcjava.netty.repost.RequestFile;
import com.zjcjava.utils.MD5FileUtil;
import com.zjcjava.utils.Tools;
/**
 * Client 网络IO事件处理
 * @author xwalker
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());
   
    
    private int byteRead;
	private volatile long start = 0;
	public RandomAccessFile randomAccessFile;
	private RequestFile request;
	private final int minReadBufferSize = 8192;
	
/*
	public TimeClientHandler(RequestFile ef) {
		if (ef.getFile().exists()) {
			if (!ef.getFile().isFile()) {
				System.out.println("Not a file :" + ef.getFile());
				return;
			}
		}
		this.request = ef;
	}
    */
    
    public TimeClientHandler() {
		// TODO Auto-generated constructor stub
	}


	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ctx.writeAndFlush(firstMessage);
		String MESSAGE="-Udevice_id=10008&imei=135790246811220&imsi=460000660340693&uuid=54b4c01f-dce0-102a-a4e0-462c07a00c5e-\n";
		ctx.writeAndFlush(MESSAGE);
    }
     
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("客户端收到服务器响应数据");

        
      Map<String, Object> deMsg=Tools.codeToString(msg.toString());
      //判断消息类型
		 if(deMsg==null){
			 System.out.println("客户端传送过来的报文有问题");
		 }else {//
			 ///处理设备端口提交上来的业务数据Start
			
			if(deMsg.get("codeType").toString().equals("U")||deMsg.get("codeType").toString().equals("F")){
				 if(deMsg.get("codeType").toString().equals("U")){
					 start=0;
				 }else{
					 start=Long.parseLong(deMsg.get("start").toString());
				 }
				
				
			 System.out.println(start);
			 
			 File file = new File("D://KuGou/张敬轩 - 断点.mp3");  // 张敬轩 - 断点.mp3 11111.txt
  			String fileName = file.getName();// 文件名
  			BASE64Encoder encoder = new BASE64Encoder();  
  			BASE64Decoder decoder = new BASE64Decoder();
  			
  			
  			randomAccessFile = new RandomAccessFile(file, "r");
  			StringBuffer bu=new StringBuffer();
			if(start>-1){//循环读取文件
					randomAccessFile.seek(start);
					int a = (int) (randomAccessFile.length() - start);//文件长度-已经读过的文件长度=剩下的文件长度
					int sendLength = minReadBufferSize;///需要读取的文件长度
					if (a < minReadBufferSize) {///如果所剩文件长度小于需要读取的文件长度初始值则设需要读取的文件长度为当前值
						sendLength = a;
					}
					byte[] bytes = new byte[sendLength];
					if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
						
						String tep=encoder.encode(bytes);//64位字符串
						String str1="-Ffile_name="+fileName+"&starPos="+start+"&file_md5="+MD5FileUtil.getFileMD5String(file)+""
								+ "&file_type="+getSuffix(fileName)+"&file_size="+randomAccessFile.length()+"&endPos="+byteRead+"&fileBytes="+tep+"-";// file=
		                 
						ctx.writeAndFlush(str1+"\n");
		                start = start + byteRead;
		                Thread.sleep(10*1000);//停一秒
		                System.out.println(str1);
		                bu.append(tep);
					}else{///读取完成
						start=-1;
						randomAccessFile.close();
						randomAccessFile=null;
						System.out.println("文件已经读完");
						File  file3 = new File("D://张敬轩 - 断点4.mp3");
						RandomAccessFile raf3 = new RandomAccessFile(file3, "rw");
						raf3.seek(0);
						raf3.write(decoder.decodeBuffer(bu.toString()));//Tools.hex2byte(bu.toString())
						raf3.close();
					}
					
					//String bk = in.readLine();  
	               // System.out.println("服务端返回数据："+bk);  
					
				} 
			 }
			 //////////////////////////////////
			 
		}
			 
        
      
        
        
        
        
        
         
    }
     
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("客户端收到服务器响应数据处理完成");
    }
     
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warning("Unexpected exception from downstream:"+cause.getMessage());
        ctx.close();
        System.out.println("客户端异常退出");
    }
    
    private static String getSuffix(String fileName)
    {
		
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return fileType;
    }
}