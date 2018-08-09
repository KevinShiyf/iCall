package com.icall.free.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.icall.free.R;
import com.icall.free.activity.demo.ThinkAndroidBaseActivity;
import com.icall.free.views.LineEditText;
import com.ta.annotation.TAInjectView;

public class RegisterActivity extends ThinkAndroidBaseActivity {

    @TAInjectView(id = R.id.register_btn)
    Button loginBtn;
    @TAInjectView(id = R.id.register_checkBox)
    CheckBox loginCheckBox;
    @TAInjectView(id = R.id.register_country_layout)
    View registerCountryLayout;
    @TAInjectView(id = R.id.register_phone_edit)
    EditText phoneEdit;
    @TAInjectView(id = R.id.register_code_edit)
    LineEditText codeEdit;

    @TAInjectView(id = R.id.register_codeT_1)
    TextView codeT1;
    @TAInjectView(id = R.id.register_codeV_1)
    View codeV1;

    @TAInjectView(id = R.id.register_codeT_2)
    TextView codeT2;
    @TAInjectView(id = R.id.register_codeV_2)
    View codeV2;

    @TAInjectView(id = R.id.register_codeT_3)
    TextView codeT3;
    @TAInjectView(id = R.id.register_codeV_3)
    View codeV3;

    @TAInjectView(id = R.id.register_codeT_4)
    TextView codeT4;
    @TAInjectView(id = R.id.register_codeV_4)
    View codeV4;


    @Override
    protected void onAfterOnCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onAfterOnCreate(savedInstanceState);
        setTitle(R.string.signin);
        setContentView(R.layout.register);
    }

    @Override
    protected void onAfterSetContentView() {
        // TODO Auto-generated method stub
        super.onAfterSetContentView();

        codeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int length = s.length();
                switch (length) {
                    case 0:
                        codeV1.setVisibility(View.VISIBLE);
                        codeV2.setVisibility(View.VISIBLE);
                        codeV3.setVisibility(View.VISIBLE);
                        codeV4.setVisibility(View.VISIBLE);

                        codeT1.setVisibility(View.GONE);
                        codeT2.setVisibility(View.GONE);
                        codeT3.setVisibility(View.GONE);
                        codeT4.setVisibility(View.GONE);
                        break;
                    case 1:
                        codeV1.setVisibility(View.GONE);
                        codeV2.setVisibility(View.VISIBLE);
                        codeV3.setVisibility(View.VISIBLE);
                        codeV4.setVisibility(View.VISIBLE);

                        codeT1.setVisibility(View.VISIBLE);
                        codeT1.setText(s);
                        codeT2.setVisibility(View.GONE);
                        codeT3.setVisibility(View.GONE);
                        codeT4.setVisibility(View.GONE);
                        break;
                    case 2:
                        codeV1.setVisibility(View.GONE);
                        codeV2.setVisibility(View.GONE);
                        codeV3.setVisibility(View.VISIBLE);
                        codeV4.setVisibility(View.VISIBLE);

                        codeT1.setVisibility(View.VISIBLE);
                        codeT1.setText(s.subSequence(0, 1));
                        codeT2.setVisibility(View.VISIBLE);
                        codeT2.setText(s.subSequence(1, 2));
                        codeT3.setVisibility(View.GONE);
                        codeT4.setVisibility(View.GONE);
                        break;
                    case 3:
                        codeV1.setVisibility(View.GONE);
                        codeV2.setVisibility(View.GONE);
                        codeV3.setVisibility(View.GONE);
                        codeV4.setVisibility(View.VISIBLE);

                        codeT1.setVisibility(View.VISIBLE);
                        codeT1.setText(s.subSequence(0, 1));
                        codeT2.setVisibility(View.VISIBLE);
                        codeT2.setText(s.subSequence(1, 2));
                        codeT3.setVisibility(View.VISIBLE);
                        codeT3.setText(s.subSequence(2, 3));
                        codeT4.setVisibility(View.GONE);
                        break;
                    case 4:
                        codeV1.setVisibility(View.GONE);
                        codeV2.setVisibility(View.GONE);
                        codeV3.setVisibility(View.GONE);
                        codeV4.setVisibility(View.GONE);

                        codeT1.setVisibility(View.VISIBLE);
                        codeT1.setText(s.subSequence(0, 1));
                        codeT2.setVisibility(View.VISIBLE);
                        codeT2.setText(s.subSequence(1, 2));
                        codeT3.setVisibility(View.VISIBLE);
                        codeT3.setText(s.subSequence(2, 3));
                        codeT4.setVisibility(View.GONE);
                        codeT4.setText(s.subSequence(3, 4));
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
