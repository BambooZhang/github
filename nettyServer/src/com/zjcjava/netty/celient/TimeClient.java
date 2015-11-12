package com.zjcjava.netty.celient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zjcjava.common.Constant;
import com.zjcjava.netty.repost.RequestFile;
import com.zjcjava.netty.request.RequestMsg;
import com.zjcjava.utils.MD5FileUtil;

/**
 * client 存在TCP粘包问题
 * @author xwlaker
 *
 */
public class TimeClient {
	
	 private static  Channel channel ;
	 
	 private static int byteRead;
		private volatile static long start = 0;
		public static RandomAccessFile randomAccessFile;
		private RequestFile request;
		private final static int minReadBufferSize = 8192;
	
    /**
     * 连接服务器
     * @param port
     * @param host
     * @throws Exception
     */
    public void connect(int port, String host) throws Exception {
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY, true);//设置封包 使用一次大数据的写操作，而不是多次小数据的写操作
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                   // ch.pipeline().addLast(new FixedLengthFrameDecoder(30));//设置定长解码器
                	ch.pipeline().addLast(new StringDecoder());//设置字符串解码器 自动将报文转为字符串
	            	ch.pipeline().addLast(new StringEncoder());//设置字符串解码器 自动将报文转为字符串
                	  //添加POJO对象解码器 禁止缓存类加载器
                  //  ch.pipeline().addLast(new ObjectDecoder(1024,ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                  //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
                  //  ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new TimeClientHandler());//设置客户端网络IO处理器
                }
            });
            //异步链接服务器 同步等待链接成功
            //future = b.connect(host, port).sync();
             channel = b.connect(host, port).sync().channel();
            //等待链接关闭
             channel.closeFuture().sync();
             System.out.println("连接.........");
        } finally {
            group.shutdownGracefully();
            System.out.println("客户端优雅的释放了线程资源...");
        }
 
    }
 
    public static void main(String[] args) throws Exception {
       TimeClient c=new TimeClient();
       c.connect(9000, "127.0.0.1");
       System.err.println("字符串输入");
       Scanner sc = new Scanner(System.in); 
       sc.useDelimiter("\n"); 
       System.err.println("连接.........");
       while (sc.hasNext()) { 
           String s = sc.next(); 
           System.err.println("你输入的是："+s);
           
          /* RequestMsg responseMsg=new RequestMsg();
           responseMsg.setCode(Constant.CODE_NONE);
           responseMsg.setMessage("指令无法处理！");
           
           //ctx.writeAndFlush(responseMsg);/
           
           RequestFile echo = new RequestFile();
			File file = new File("D://KuGou/陈奕迅 - 好久不见.mp3");  //  "D://files/xxoo"+args[0]+".amr"
			String fileName = file.getName();// 文件名
			echo.setFile(file);
			echo.setFile_md5(MD5FileUtil.getFileMD5String(file));
			echo.setFile_name(fileName);
			echo.setFile_type(getSuffix(fileName));
			echo.setStarPos(0);// 文件开始位置
*/           
           
           
           
           File file = new File("D://KuGou/张敬轩 - 断点.mp3");  // 张敬轩 - 断点.mp3 11111.txt
			String fileName = file.getName();// 文件名
			BASE64Encoder encoder = new BASE64Encoder();  
			BASE64Decoder decoder = new BASE64Decoder();
			
			
			randomAccessFile = new RandomAccessFile(file, "r");
			StringBuffer bu=new StringBuffer();
			while(start>-1){//循环读取文件
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
							+ "&file_type="+getSuffix(fileName)+"&file_size="+randomAccessFile.length()+"&fileBytes="+tep+"-";// file=
	                 
					channel.write(str1);
	                start = start + byteRead;
	                Thread.sleep(10*1000);//停一秒
	                System.out.println(str1);
	                bu.append(tep);
				}else{///读取完成
					start=-1;
					randomAccessFile.close();
					randomAccessFile=null;
					System.out.println("文件已经读完");
				}
				
				//String bk = in.readLine();  
              // System.out.println("服务端返回数据："+bk);  
				
			} 
           
           
       } 
    }
 
    private static String getSuffix(String fileName)
    {
		
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return fileType;
    }
}
