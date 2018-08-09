package com.icall.free.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.Context;

import com.icall.free.entity.CountryCodeEntity;
import com.ta.TAApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CodeProperties {

	private static CodeProperties self = null;

	private List<CountryCodeEntity> codeList = null;

	private CodeProperties() {
		codeList = new ArrayList<>();
		Properties properties = new Properties();
		try {
			Context mC = TAApplication.getApplication();
			InputStream inputStream = mC.getAssets().open("code.json");
			if (inputStream != null) {
				int bodyLength = inputStream.available();
				byte[] body = new byte[bodyLength];
				byte[] buf = new byte[1024];
				int resdLength = 0;
				int rl = 0;
				while (resdLength < bodyLength) {
					int lastLength = bodyLength - resdLength;
					if (lastLength > 1024) {
						rl = inputStream.read(buf);
					} else {
						rl = inputStream.read(buf, 0, lastLength);
					}
					if (rl >= 0) {
						System.arraycopy(buf, 0, body, resdLength, rl);
						resdLength += rl;
					}
					rl = 0;
				}
				String commandStr = new String(body);
				try {
					JSONArray codeArray = new JSONArray(commandStr);
					for (int i = 0; i < codeArray.length(); i++) {
						CountryCodeEntity entity = new CountryCodeEntity();
						JSONObject item = codeArray.getJSONObject(i);
						entity.setCode(item.optInt("code"));
						entity.setLocale(item.optString("locale"));
						entity.setEn(item.optString("en"));
						entity.setTw(item.optString("tw"));
						entity.setZh(item.optString("zh"));
						codeList.add(entity);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				inputStream.close();
				inputStream = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			properties.clear();
			properties = null;
		}
	}

	public static CodeProperties generateUrl() {
		if (null == self) {
			self = new CodeProperties();
		}
		return self;
	}

	public List<CountryCodeEntity> getCodeArray() {
		return codeList;
	}
}
