package com.icall.free.activity;

import android.view.View;
import android.widget.Button;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.entity.http.BaseEntity;
import com.icall.free.entity.http.HttpEntityFactory;
import com.icall.free.entity.http.LoginEntity;
import com.icall.free.util.ConfigProperties;
import com.icall.free.util.DateUtil;
import com.icall.free.util.ErrorCode;
import com.icall.free.util.TimesUtils;
import com.icall.free.util.Tools;
import com.icall.free.util.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * @author 白猫
 * @version V1.0
 * @Title: 用户其启动界面
 * @Package com.icall.free.activity
 * @Description: 用户其启动界面时候的一个启动页面完成一些初始化工作
 * @date 2013-5-6
 */
@InjectLayer(R.layout.splash_activity)
public class SplashActivity extends BaseActivity {

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button splash_btn;

    private boolean requestComplate = false;
    private int autoLoginCode = -1;
    private String autoLoginMsg = "";
    public void click(View v) {
        switch (v.getId()) {
            case R.id.splash_btn:
                if (requestComplate) {
                    handleEvnet();
                } else {
                    showTipsDialog("");
                }
                break;
        }
    }

    @InjectInit
    private void init() {
        JSONObject param = new JSONObject();
        try {
            param.put("xaid", Tools.getAndroidId(this));
            param.put("mac", Tools.getMac(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FastHttpHander.ajax(ConfigProperties.getDefault().getHostUrl() + "/user/autoLogin", param.toString(), this);
    }

    @InjectHttpOk
    private void success(ResponseEntity entity) {
        requestComplate = true;
        LoginEntity loginEntity = (LoginEntity)HttpEntityFactory.createHttpEntity(entity, LoginEntity.class);
        System.out.printf("splash success result = " + loginEntity);
        if (loginEntity != null) {
            autoLoginCode = loginEntity.getCode();
            autoLoginMsg = loginEntity.getMsg();
            if (loginEntity.getCode() == 0) {
                UserData.getInstance().setUserId(loginEntity.getUid());
                UserData.getInstance().setToken(loginEntity.getToken());
                UserData.getInstance().setPhoneNum(loginEntity.getPhone());
                UserData.getInstance().setName(loginEntity.getName());
                UserData.getInstance().setMcc(loginEntity.getMcc());
                UserData.getInstance().setPhoto(loginEntity.getPhoto());
                UserData.getInstance().setTokenExpried(loginEntity.getToken_expried());
            }
            if (isShow()) {
                handleEvnet();
            }
        } else {
            hideTipsDialog();
            displayToast(entity.getContentAsString());
        }
    }

    @InjectHttpErr
    private void fail(ResponseEntity entity) {
        requestComplate = true;
        hideTipsDialog();
        displayToast(entity.getContentAsString());
    }

    public void handleEvnet() {
        if (autoLoginCode == ErrorCode.SUCCESS.getCode()) {
            startActivity(MainTabActivity.class);
            finish();
        } else if (autoLoginCode == ErrorCode.C001007.getCode()) {
            startActivity(FaceBookLoginActivity.class);
            finish();
        } else if (autoLoginCode == ErrorCode.C001008.getCode()) {
            startActivity(RegisterActivity.class);
            finish();
        } else {
            displayToast(autoLoginMsg);
        }
    }
}
