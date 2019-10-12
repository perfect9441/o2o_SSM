package com.zhxgkj.o2o.entity;

import java.util.Date;

public class PersonInfo {
	private Long userId;
	private String name;
	private String profileImg;
	private String email;
	private String gender;
	private Integer enableStatus;
//	1.顾客 2.店家 3.超级管理员
	private Integer userType;
	private Date cteateTime;
	private Date lastEditTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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
	@Override
	public String toString() {
		return "Personinfo [userId=" + userId + ", name=" + name + ", profileImg=" + profileImg + ", email=" + email
				+ ", gender=" + gender + ", enableStatus=" + enableStatus + ", userType=" + userType + ", cteateTime="
				+ cteateTime + ", lastEditTime=" + lastEditTime + "]";
	}
	
}
