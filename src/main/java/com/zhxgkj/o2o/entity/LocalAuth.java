package com.zhxgkj.o2o.entity;

import java.util.Date;

public class LocalAuth {
	private Long localAuthId;
	private String username;
	private String password;
	private Date cteateTime;
	private Date lastEditTime;
	private PersonInfo personinfo;
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCteateTime() {
		return cteateTime;
	}
	public void setCteateTime(Date cteateTime) {
		this.cteateTime = cteateTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public PersonInfo getPersoninfo() {
		return personinfo;
	}
	public void setPersoninfo(PersonInfo personinfo) {
		this.personinfo = personinfo;
	}
	@Override
	public String toString() {
		return "localAuth [localAuthId=" + localAuthId + ", username=" + username + ", password=" + password
				+ ", cteateTime=" + cteateTime + ", lastEditTime=" + lastEditTime + ", personinfo=" + personinfo + "]";
	}
	
}
