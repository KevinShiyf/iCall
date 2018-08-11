package com.icall.free.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.views.LineEditText;

@InjectLayer(R.layout.login_activity)
public class LoginActivity extends BaseActivity {

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    Button login_btn;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    CheckBox login_checkBox;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    TextView login_code_tv;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    View login_country_layout;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    TextView login_forgot_tv;

    EditText login_phone_edit;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    TextView login_register_tv;

    @InjectView
    LineEditText login_password_edit;


    private final static int SELECT_CODE_CODE = 101;
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                break;
            case R.id.login_code_tv:
                break;
            case R.id.login_country_layout:
                startActivity(CountryCodeActivity.class, SELECT_CODE_CODE);
                break;
            case R.id.login_forgot_tv:
                break;
            case R.id.login_register_tv:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CODE_CODE) {

        }
    }
}
