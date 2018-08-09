package com.icall.free.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.icall.free.R;
import com.icall.free.activity.demo.ThinkAndroidBaseActivity;
import com.icall.free.views.LineEditText;
import com.ta.annotation.TAInjectResource;
import com.ta.annotation.TAInjectView;

public class LoginActivity extends ThinkAndroidBaseActivity implements View.OnClickListener {

    @TAInjectView(id = R.id.login_btn)
    Button loginBtn;

    @TAInjectView(id = R.id.login_checkBox)
    CheckBox loginCheckBox;

    @TAInjectView(id = R.id.login_code_tv)
    TextView loginCodeTv;

    @TAInjectView(id = R.id.login_country_layout)
    View loginCountryLayout;

    @TAInjectView(id = R.id.login_forgot_tv)
    TextView forgotPasswordTv;

    @TAInjectView(id = R.id.login_phone_edit)
    EditText phoneEdit;

    @TAInjectView(id = R.id.login_register_tv)
    TextView loginRegisterTv;

    @TAInjectView(id = R.id.login_password_edit)
    LineEditText passwordEdit;

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
        loginCountryLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:

                break;
            case R.id.login_code_tv:
                break;
            case R.id.login_country_layout:
                doActivity(R.string.countryCodeActivity);
                break;
            case R.id.login_forgot_tv:
                break;
            case R.id.login_register_tv:
                doActivity(R.string.registerActivity);
                break;
        }
    }
}
