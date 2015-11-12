package com.zjcjava.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zjcjava.utils.Tools;

/**
 * 测试利用多线程进行文件的写操作
 */
public class Test {

	public static void main(String[] args) throws Exception {
		// 预分配文件所占的磁盘空间，磁盘中会创建一个指定大小的文件
		RandomAccessFile raf1 = new RandomAccessFile("D://KuGou/张敬轩 - 断点.mp3", "rw");//File file = new File("D://KuGou/陈奕迅 - 好久不见.mp3");
		//raf1.setLength(1024*1024); // 预分配 1M 的文件空间
		//raf.close();
		
		int byteRead = 1024,start=0;
		byte[] byte1 = new byte[1024];
		File  file = new File("D://张敬轩 - 断点2.mp3");
		RandomAccessFile raf2 = new RandomAccessFile(file, "rw");	
		File  file1 = new File("D://张敬轩 - 断点3.mp3");
		RandomAccessFile raf3 = new RandomAccessFile(file1, "rw");	
		long fileSize=raf1.length();
		
		
		System.out.println(byteRead);
		 BASE64Encoder encoder = new BASE64Encoder();  
		 BASE64Decoder decoder = new BASE64Decoder();
		StringBuffer bu=new StringBuffer();
		if((byteRead = raf1.read(byte1)) != -1) {
				
			raf2.seek(start);
			String tep=encoder.encode(byte1);  ;//Tools.byte2hex(byte1);
			System.out.print(tep);
			bu.append(tep);
			raf2.write(byte1);
			start = start + byteRead;
		}
		
		while(start>-1){//byteRead > 0 && (start < fileSize && fileSize != -1)
			//System.out.println(byteRead);
			raf1.seek(start);
			int a = (int) (raf1.length() - start);
			int sendLength = 1024;
			if (a < 1024) {
				sendLength = a;
			}
			byte[] bytes = new byte[sendLength];
			if ((byteRead = raf1.read(bytes)) != -1 && (raf1.length() - start) > 0) {
				start = start + byteRead;
				String tep=encoder.encode(bytes);//Tools.byte2hex(bytes);
				System.out.print(tep);
				bu.append(tep);
				raf2.write(bytes);
			}else{
				start=-1;
				raf1.close();
				raf1=null;
				raf2.close();
				raf2=null;
				System.out.println("文件已经读完");
			}
			
		} 
			
		raf3.seek(0);
		raf3.write(decoder.decodeBuffer(bu.toString()));//Tools.hex2byte(bu.toString())
		raf3.close();
		
		
		
		
		/*// 所要写入的文件内容
		String s1 = "第一个字符串";
		String s2 = "第二个字符串";
		String s3 = "第三个字符串";
		String s4 = "第四个字符串";
		String s5 = "第五个字符串";
		
		// 利用多线程同时写入一个文件
		new FileWriteThread(0,s1.getBytes()).start(); // 从文件的1024字节之后开始写入数据
		new FileWriteThread(s1.getBytes().length*1,s2.getBytes()).start(); // 从文件的2048字节之后开始写入数据
		new FileWriteThread(s1.getBytes().length*2,s3.getBytes()).start(); // 从文件的3072字节之后开始写入数据
		new FileWriteThread(s1.getBytes().length*3,s4.getBytes()).start(); // 从文件的4096字节之后开始写入数据
		new FileWriteThread(s1.getBytes().length*4,s5.getBytes()).start(); // 从文件的5120字节之后开始写入数据
*/	}
	
	// 利用线程在文件的指定位置写入指定数据
	static class FileWriteThread extends Thread{
		private int skip;
		private byte[] content;
		
		public FileWriteThread(int skip,byte[] content){
			this.skip = skip;
			this.content = content;
		}
		
		public void run(){
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile("D://abc.txt", "rw");
				raf.seek(skip);
				raf.write(content);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					raf.close();
				} catch (Exception e) {
				}
			}
		}
	}

}