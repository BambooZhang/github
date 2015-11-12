package com.zjcjava.entities.serverdevice;

import java.io.Serializable;
/**
 * 设备信息
 */
public class ServerDevice  implements Serializable {

	
	private static final long serialVersionUID = 6200390330718630934L;

	private	Integer	id;//	主键ID
	private	Integer	device_id;//设备ID,唯一的UUID
	private	Integer	dt;//	服务器时间
	private	double 	tz;//	设备当时时区
	private	Integer	gps_switch;//	GPS开关
	private	Integer	bt_switch;//	蓝牙开关
	private	Integer	incoming;//	来电防火墙
	private	Integer	record;//	录音监听模式
	private	Integer	call_monitor;//	电话监听模式
	private	Integer	call_monitor_no;//	远程拾音
	private	Integer	udp_cycle;//	UDP周期
	private	Integer	ctrl_light;//	控制亮灯
	private	Integer	ctrl_voice;//	控制发声
	private	Integer	sync;//	同步数据
	private	Integer	restart;//	重启设备
	private	Integer	track;//	追踪模式
	private	Integer	upload_loc;//	上报精确位置
	private	Integer	sos;//	SOS策略
	private	Integer	udp_wifi;//	UDPwifi上传
	private	Integer	shutdown;//	远程关机
	private	Integer	normal_key;//	按键类型
	private	Integer	yaoyao;//	摇一摇功能
	private	Integer	ktone;//	按键音
	private	Integer	mode_fly;//	飞行模式策略
	private	Integer	lang;//	显示语言
	
	private String imei;//设备手机卡
	private String imsi;//设备手机卡
	private String sn;//设备手机卡
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDt() {
		return dt;
	}
	public void setDt(Integer dt) {
		this.dt = dt;
	}
	public double getTz() {
		return tz;
	}
	public void setTz(double tz) {
		this.tz = tz;
	}
	public Integer getGps_switch() {
		return gps_switch;
	}
	public void setGps_switch(Integer gps_switch) {
		this.gps_switch = gps_switch;
	}
	public Integer getBt_switch() {
		return bt_switch;
	}
	public void setBt_switch(Integer bt_switch) {
		this.bt_switch = bt_switch;
	}
	public Integer getIncoming() {
		return incoming;
	}
	public void setIncoming(Integer incoming) {
		this.incoming = incoming;
	}
	public Integer getRecord() {
		return record;
	}
	public void setRecord(Integer record) {
		this.record = record;
	}
	public Integer getCall_monitor() {
		return call_monitor;
	}
	public void setCall_monitor(Integer call_monitor) {
		this.call_monitor = call_monitor;
	}
	public Integer getCall_monitor_no() {
		return call_monitor_no;
	}
	public void setCall_monitor_no(Integer call_monitor_no) {
		this.call_monitor_no = call_monitor_no;
	}
	public Integer getUdp_cycle() {
		return udp_cycle;
	}
	public void setUdp_cycle(Integer udp_cycle) {
		this.udp_cycle = udp_cycle;
	}
	public Integer getCtrl_light() {
		return ctrl_light;
	}
	public void setCtrl_light(Integer ctrl_light) {
		this.ctrl_light = ctrl_light;
	}
	public Integer getCtrl_voice() {
		return ctrl_voice;
	}
	public void setCtrl_voice(Integer ctrl_voice) {
		this.ctrl_voice = ctrl_voice;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public Integer getRestart() {
		return restart;
	}
	public void setRestart(Integer restart) {
		this.restart = restart;
	}
	public Integer getTrack() {
		return track;
	}
	public void setTrack(Integer track) {
		this.track = track;
	}
	public Integer getUpload_loc() {
		return upload_loc;
	}
	public void setUpload_loc(Integer upload_loc) {
		this.upload_loc = upload_loc;
	}
	public Integer getSos() {
		return sos;
	}
	public void setSos(Integer sos) {
		this.sos = sos;
	}
	public Integer getUdp_wifi() {
		return udp_wifi;
	}
	public void setUdp_wifi(Integer udp_wifi) {
		this.udp_wifi = udp_wifi;
	}
	public Integer getShutdown() {
		return shutdown;
	}
	public void setShutdown(Integer shutdown) {
		this.shutdown = shutdown;
	}
	public Integer getNormal_key() {
		return normal_key;
	}
	public void setNormal_key(Integer normal_key) {
		this.normal_key = normal_key;
	}
	public Integer getYaoyao() {
		return yaoyao;
	}
	public void setYaoyao(Integer yaoyao) {
		this.yaoyao = yaoyao;
	}
	public Integer getKtone() {
		return ktone;
	}
	public void setKtone(Integer ktone) {
		this.ktone = ktone;
	}
	public Integer getMode_fly() {
		return mode_fly;
	}
	public void setMode_fly(Integer mode_fly) {
		this.mode_fly = mode_fly;
	}
	public Integer getLang() {
		return lang;
	}
	public void setLang(Integer lang) {
		this.lang = lang;
	}
	public Integer getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	
	
	

	
}
