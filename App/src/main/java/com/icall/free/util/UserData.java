package com.icall.free.util;

import com.android.pc.util.Handler_SharedPreferences;

public class UserData {
	private static UserData mInstance = null;
	
	private static final String SP_NAME = "icall_preferences";

	public static final String USER_ID = "user_id"; // 注册、登录返回的用户id
	public static final String PASSWORD = "password"; // 登录密码
	public static final String TOKEN = "token"; // 用户注册的电话号码
	public static final String TOKEN_EXPRIED = "token_expried"; // 用户注册的电话号码
	public static final String PHONE_NUMBER = "phone_number";
	public static final String NAME = "name";
	public static final String PHOTO = "photo";
	public static final String MCC = "mcc";

	private String token;
	private String tokenExpried;
	private String name;
	private String photo;
	private String mcc;

	public synchronized static UserData getInstance() {
		if (mInstance == null)
			mInstance = new UserData();
		return mInstance;
	}


	public void clearData() {
		Handler_SharedPreferences.ClearSharedPreferences(SP_NAME);
	}
	
	public Integer getUserId() {
		return Handler_SharedPreferences.readObject(SP_NAME, USER_ID);
	}
	
	public void setUserId(Integer id) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, USER_ID, id);
	}
	
	public String getPassword() {
		return Handler_SharedPreferences.readObject(SP_NAME, PASSWORD);
	}
	
	public void setPassword(String password) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, PASSWORD, password);
	}
	
	public String getPhoneNum() {
		return Handler_SharedPreferences.readObject(SP_NAME, PHONE_NUMBER);
	}
	
	public void setPhoneNum(String phoneNum) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, PHONE_NUMBER, phoneNum);
	}

	public String getToken() {
		return Handler_SharedPreferences.readObject(SP_NAME, TOKEN);
	}

	public void setToken(String token) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, TOKEN, token);
	}

	public String getTokenExpried() {
		return Handler_SharedPreferences.readObject(SP_NAME, TOKEN_EXPRIED);
	}

	public void setTokenExpried(String tokenExpried) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, TOKEN_EXPRIED, tokenExpried);
	}

	public String getName() {
		return Handler_SharedPreferences.readObject(SP_NAME, NAME);
	}

	public void setName(String name) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, NAME, name);
	}

	public String getPhoto() {
		return Handler_SharedPreferences.readObject(SP_NAME, PHOTO);
	}

	public void setPhoto(String photo) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, PHOTO, photo);
	}

	public String getMcc() {
		return Handler_SharedPreferences.readObject(SP_NAME, MCC);
	}

	public void setMcc(String mcc) {
		Handler_SharedPreferences.WriteSharedPreferences(SP_NAME, MCC, mcc);
	}

	public boolean isLogin() {
		if (getUserId() != null && StringUtil.isNotNull(getToken())) {
			return true;
		}
		return false;
	}
}
