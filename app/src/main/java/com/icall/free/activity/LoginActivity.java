package com.icall.free.activity;

import android.os.Bundle;

import com.icall.free.R;
import com.icall.free.activity.demo.ThinkAndroidBaseActivity;

public class LoginActivity extends ThinkAndroidBaseActivity {
    @Override
    protected void onAfterOnCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onAfterOnCreate(savedInstanceState);
        setTitle(R.string.signin);
        setContentView(R.layout.login);
    }

    @Override
    protected void onAfterSetContentView() {
        // TODO Auto-generated method stub
        super.onAfterSetContentView();
    }
}
