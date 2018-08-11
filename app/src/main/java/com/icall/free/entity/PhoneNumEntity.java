package com.icall.free.entity;

import java.io.Serializable;

public class PhoneNumEntity implements Serializable {
	private static final long serialVersionUID = 2433369912707334472L;
	public String phoneNum;
	public String typeFromDb;
	public String location;
	public String type;//手机或固话
	public String name;
	public String matchPhoneNum;			//匹配的电话号码
}
