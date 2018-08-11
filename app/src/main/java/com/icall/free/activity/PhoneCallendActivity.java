package com.icall.free.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.views.LineEditText;

@InjectLayer(R.layout.phone_end_activity)
public class PhoneCallendActivity extends BaseActivity {

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    ImageView phone_end_back_iv;
    @InjectView
    TextView phone_end_name_tv;
    @InjectView
    TextView phone_end_duration_tv;
    @InjectView
    TextView phone_end_cost_tv;
    @InjectView
    TextView phone_end_save_tv;
}
