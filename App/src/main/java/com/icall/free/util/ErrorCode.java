package com.icall.free.util;

public enum ErrorCode {
	SUCCESS(0, "成功"),

	C001006(1006, "用户被冻结"),
	C001012(1012, "token更新失败"),
	C001007(1007, "通过FaceBook注册"),
	C001008(1008, "通过手机号注册"),
	C000500(500, "系统错误");

	private int code;
	private String msg;

	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getDes(int code) {
		for (ErrorCode message : ErrorCode.values()) {
			if (message.getCode() == code) {
				return message.getMsg();
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
