package com.icall.free.util;

import android.content.Context;

import com.android.pc.ioc.event.EventBus;
import com.icall.free.ICallApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigProperties {
	private static ConfigProperties self = null;

	public String HOST_ADDRESS = "";
	public String BUSINESS_URL = "";


	private ConfigProperties() {
		Properties properties = new Properties();
		try {
			Context mC = ICallApplication.getApplication();
			InputStream inputStream = mC.getAssets().open("config.properties");
			properties.load(inputStream);
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
			HOST_ADDRESS = properties.getProperty("HOST_ADDRESS", "");
			BUSINESS_URL = properties.getProperty("BUSINESS_URL", "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			properties.clear();
			properties = null;
		}
	}

	public static ConfigProperties getDefault() {
		if (null == self) {
			self = new ConfigProperties();
		}
		return self;
	}

	public String getHostUrl() {
		return HOST_ADDRESS + BUSINESS_URL;
	}
}
