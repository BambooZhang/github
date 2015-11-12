package com.zjcjava.netty.request;

//客户端连接上服务器时提交的是设备信息
public class DeviceInf implements java.io.Serializable {

	private static final long serialVersionUID = -3518187653162813645L;

	private Integer	   device_id;//
	private String	   imei;//
	private String	   imsi;//
	private String	   uuid;//
	private String	   sn;//
	private String	   p_name;//
	private String	   rfid;//
	private String	   iccid;//
	private String	   soft_version;//
	private String	   sys_version;//
	private String	   tcp;//
	private String	   udp;//
	private Integer	   power;//
	private Integer	   contact_size;//
	private Integer	   tl_build;
	
	
	
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getSoft_version() {
		return soft_version;
	}
	public void setSoft_version(String soft_version) {
		this.soft_version = soft_version;
	}
	public String getSys_version() {
		return sys_version;
	}
	public void setSys_version(String sys_version) {
		this.sys_version = sys_version;
	}
	public String getTcp() {
		return tcp;
	}
	public void setTcp(String tcp) {
		this.tcp = tcp;
	}
	public String getUdp() {
		return udp;
	}
	public void setUdp(String udp) {
		this.udp = udp;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Integer getContact_size() {
		return contact_size;
	}
	public void setContact_size(Integer contact_size) {
		this.contact_size = contact_size;
	}
	public Integer getTl_build() {
		return tl_build;
	}
	public void setTl_build(Integer tl_build) {
		this.tl_build = tl_build;
	}

	
	
}
