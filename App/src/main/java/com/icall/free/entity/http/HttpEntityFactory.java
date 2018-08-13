package com.icall.free.entity.http;


import com.android.pc.ioc.internet.ResponseEntity;
import com.google.gson.Gson;
import com.icall.free.activity.BaseActivity;
import com.icall.free.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpEntityFactory {
    public static Gson gson = new Gson();

    public static BaseEntity createHttpEntity(ResponseEntity entity, Class<? extends BaseEntity> classType) {
        if (entity == null) {
            return null;
        }
        String data = entity.getContentAsString();
        if (StringUtil.isNull(data))
            return null;
        try {
            if (classType.equals(BaseEntity.class)) {
                BaseEntity baseEntity = gson.fromJson(data, classType);
                return baseEntity;
            } else {
                JSONObject json = new JSONObject(data);
                int code = json.optInt("code");
                String msg = json.optString("msg");
                String jsonData = json.optString("data");
                BaseEntity baseEntity = null;
                if (StringUtil.isNull(jsonData)) {
                    baseEntity = new BaseEntity();
                } else {
                    baseEntity = gson.fromJson(jsonData, classType);
                }
                baseEntity.setCode(code);
                baseEntity.setMsg(msg);
                return baseEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
