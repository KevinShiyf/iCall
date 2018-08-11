package com.icall.free.activity;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;

import android.view.View;
import android.widget.Button;

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
    private static final String SYSTEMCACHE = "thinkandroid";

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button splash_login_btn;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button splash_register_btn;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button splash_facebook_btn;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button splash_main_btn;

    public void click(View v) {
        switch (v.getId()) {
            case R.id.splash_facebook_btn:
                startActivity(FaceBookLoginActivity.class);
                break;
            case R.id.splash_login_btn:
                startActivity(LoginActivity.class);
                break;
            case R.id.splash_register_btn:
                startActivity(RegisterActivity.class);
                break;
            case R.id.splash_main_btn:
                startActivity(MainTabActivity.class);
                break;
        }
    }
}
