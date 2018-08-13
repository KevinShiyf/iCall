package com.icall.free.activity;

import android.view.View;
import android.widget.Button;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.util.UserData;

/**
 * @author 白猫
 * @version V1.0
 * @Title: 用户其启动界面
 * @Package com.icall.free.activity
 * @Description: 用户其启动界面时候的一个启动页面完成一些初始化工作
 * @date 2013-5-6
 */
@InjectLayer(R.layout.launch_activity)
public class LaunchActivity extends BaseActivity {
    @InjectInit
    public void init() {
        if (UserData.getInstance().isLogin()) {
            startActivity(MainTabActivity.class);
        } else {
            startActivity(SplashActivity.class);
        }
        finish();
    }
}
