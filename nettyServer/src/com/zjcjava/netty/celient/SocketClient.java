package com.zjcjava.netty.celient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.tools.Tool;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zjcjava.netty.repost.RequestFile;
import com.zjcjava.netty.request.DeviceInf;
import com.zjcjava.netty.request.RequestMsg;
import com.zjcjava.utils.MD5FileUtil;
import com.zjcjava.utils.SerializableUtil;
import com.zjcjava.utils.Tools;

public class SocketClient {
	
	
	private static int byteRead;
	private volatile static long start = 0;
	public static RandomAccessFile randomAccessFile;
	private RequestFile request;
	private final static int minReadBufferSize = 8192;
	
	/** 
     * 方法名：main 描述： 作者：白鹏飞 日期：2012-8-23 下午01:47:12 
     *  
     * @param @param args 
     * @return void 
     */  
    public static void main(String[] args) {  
        Socket socket = null;  
        BufferedReader in = null;  
        PrintWriter out = null;  
        //ObjectOutputStream out=null;
        try {  
        	 while (true) {
        		 //客户端socket指定服务器的地址和端口号  
                 socket = new Socket("127.0.0.1", 8003);  
                 System.out.println("Socket=" + socket);  
                 //同服务器原理一样  
               //读取服务器端数据
                 in = new BufferedReader(new InputStreamReader(  
                         socket.getInputStream()));  
                 //向服务器端发送数据
                 out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
                         socket.getOutputStream())));
                 System.out.print("请输入： \t");
                 
                 String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
               
//             
//     			File file = new File("D://KuGou/张敬轩 - 断点.mp3");  // 张敬轩 - 断点.mp3 11111.txt
//     			String fileName = file.getName();// 文件名
//     			BASE64Encoder encoder = new BASE64Encoder();  
//     			BASE64Decoder decoder = new BASE64Decoder();
//     			
//     			
//     			randomAccessFile = new RandomAccessFile(file, "r");
//     			StringBuffer bu=new StringBuffer();
//				while(start>-1){//循环读取文件
//					randomAccessFile.seek(start);
//					int a = (int) (randomAccessFile.length() - start);//文件长度-已经读过的文件长度=剩下的文件长度
//					int sendLength = minReadBufferSize;///需要读取的文件长度
//					if (a < minReadBufferSize) {///如果所剩文件长度小于需要读取的文件长度初始值则设需要读取的文件长度为当前值
//						sendLength = a;
//					}
//					byte[] bytes = new byte[sendLength];
//					if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
//						
//						String tep=encoder.encode(bytes);//64位字符串
//						String str1="-Ffile_name="+fileName+"&starPos="+start+"&file_md5="+MD5FileUtil.getFileMD5String(file)+""
//								+ "&file_type="+getSuffix(fileName)+"&file_size="+randomAccessFile.length()+"&fileBytes="+tep+"-";// file=
//		                 
//						out.write(str1+"\n");
//		                out.flush();
//		                start = start + byteRead;
//		                Thread.sleep(10*1000);//停一秒
//		                System.out.println(str1);
//		                bu.append(tep);
//					}else{///读取完成
//						start=-1;
//						randomAccessFile.close();
//						randomAccessFile=null;
//						System.out.println("文件已经读完");
//						File  file3 = new File("D://张敬轩 - 断点4.mp3");
//						RandomAccessFile raf3 = new RandomAccessFile(file3, "rw");
//						raf3.seek(0);
//						raf3.write(decoder.decodeBuffer(bu.toString()));//Tools.hex2byte(bu.toString())
//						raf3.close();
//					}
//					
//					//String bk = in.readLine();  
//	               // System.out.println("服务端返回数据："+bk);  
//					
//				} 
//     			
//                 
                 
                /* System.out.println(str.length()+"\t"+str.lastIndexOf("-"));
                 Map<String, Object> deMsg=Tools.codeToString(str);
        		 //判断消息类型
        		 System.out.println(deMsg!=null?deMsg.get("codeType"):"命令有问题");*/
        		 
                 
                 out.write(str+"\n");
                 out.flush();
                 System.out.println("发送数据完毕");  
                 
        		String bk = in.readLine();  
	            System.out.println("服务端返回数据："+bk);  
        		 
        		/* DeviceInf df=new DeviceInf();
        		 Tools.mapToObject(df, deMsg);//map转为对象
                 System.out.println(df.getDevice_id());*/
                 
        		 
        		
              
               
        	 }
           
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                System.out.println("close......");  
                in.close();  
                out.close();  
                socket.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
    
    private static String getSuffix(String fileName)
    {
		
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return fileType;
    }
    
    private static byte[] intToByteArray(int byteLength, int intValue) {
		return ByteBuffer.allocate(byteLength).putInt(intValue).array();
	}
    private static byte[] combineByteArray(byte[] array1, byte[] array2) {
		byte[] combined = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, combined, 0, array1.length);
		System.arraycopy(array2, 0, combined, array1.length, array2.length);
		return combined;
	}
}
