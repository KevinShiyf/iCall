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

@InjectLayer(R.layout.phone_calling_activity)
public class PhoneCallingActivity extends BaseActivity {

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    ImageView phone_calling_mute_iv, phone_calling_key_iv, phone_calling_speaker_iv, phone_calling_end_iv;
    @InjectView
    TextView phone_calling_name_tv, phone_calling_duration_tv;
    @InjectView
    TextView phone_calling_country_tv;
    @InjectView
    TextView phone_calling_time_tv, phone_calling_am_tv;
    @InjectView
    TextView phone_calling_rate_tv, phone_calling_credits_tv;
    @InjectView
    TextView phone_calling_rest_tv;

    public void click(View v) {
        switch (v.getId()) {
            case R.id.phone_calling_mute_iv:

                break;
            case R.id.phone_calling_key_iv:

                break;
            case R.id.phone_calling_speaker_iv:

                break;
            case R.id.phone_calling_end_iv:
                startActivity(PhoneCallendActivity.class);
                finish();
                break;
        }
    }
}
