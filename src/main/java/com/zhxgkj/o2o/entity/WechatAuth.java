package com.zhxgkj.o2o.entity;

import java.util.Date;

public class WechatAuth {
	private Long wechatAuthId;
	private String openId;
	private Date cteateTime;
	private PersonInfo personinfo;
	public Long getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCteateTime() {
		return cteateTime;
	}
	public void setCteateTime(Date cteateTime) {
		this.cteateTime = cteateTime;
	}
	public PersonInfo getPersoninfo() {
		return personinfo;
	}
	public void setPersoninfo(PersonInfo personinfo) {
		this.personinfo = personinfo;
	}
	@Override
	public String toString() {
		return "WechatAuth [wechatAuthId=" + wechatAuthId + ", openId=" + openId + ", cteateTime=" + cteateTime
				+ ", personinfo=" + personinfo + "]";
	}
	
}
