package com.zhxgkj.o2o.dto;

public class Result<T> {
	private boolean success;//成功标志
	private T data;//成功时返回的数据
	private String errorMsg;//错误信息
	private int errorCode;//错误代码
//	默认构造器
	public Result() {
	}
//	成功时候的构造器
	public Result(boolean success,T data) {
		this.success = success;
		this.data = data;
	}
//	失败时候的构造器
	public Result(boolean success,int errorCode,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "Result [success=" + success + ", data=" + data + ", errorMsg=" + errorMsg + ", errorCode=" + errorCode
				+ "]";
	}
	
}
