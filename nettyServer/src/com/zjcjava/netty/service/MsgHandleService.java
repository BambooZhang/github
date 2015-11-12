package com.zjcjava.netty.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MsgHandleService {
	
	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static Map<String, ChannelHandlerContext> userMap = new HashMap<String, ChannelHandlerContext>();
	
	public static Map<String,String> userList = new HashMap<String,String>();

}
