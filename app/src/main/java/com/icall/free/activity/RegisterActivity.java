package com.icall.free.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.icall.free.R;

@InjectLayer(R.layout.register_activity)
public class RegisterActivity extends BaseActivity {

    @InjectView
    Button register_btn;
    @InjectView
    CheckBox register_checkBox;

    @InjectView
    View register_country_layout;

    @InjectView
    EditText register_phone_edit;

    @InjectView
    EditText register_code_edit;

    @InjectView
    TextView register_codeT_1;
    @InjectView
    View register_codeV_1;

    @InjectView
    TextView register_codeT_2;
    @InjectView
    View register_codeV_2;

    @InjectView
    TextView register_codeT_3;
    @InjectView
    View register_codeV_3;

    @InjectView
    TextView register_codeT_4;

    @InjectView
    View register_codeV_4;

    @InjectInit
    protected void init() {
        register_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int length = s.length();
//                if (length == 0) {
//                    register_code_edit.setVisibility(View.VISIBLE);
//                } else {
//                    register_code_edit.setVisibility(View.INVISIBLE);
//                }
                switch (length) {
                    case 0:
                        register_codeV_1.setVisibility(View.VISIBLE);
                        register_codeV_2.setVisibility(View.VISIBLE);
                        register_codeV_3.setVisibility(View.VISIBLE);
                        register_codeV_4.setVisibility(View.VISIBLE);

                        register_codeT_1.setVisibility(View.GONE);
                        register_codeT_2.setVisibility(View.GONE);
                        register_codeT_3.setVisibility(View.GONE);
                        register_codeT_4.setVisibility(View.GONE);
                        break;
                    case 1:
                        register_codeV_1.setVisibility(View.GONE);
                        register_codeV_2.setVisibility(View.VISIBLE);
                        register_codeV_3.setVisibility(View.VISIBLE);
                        register_codeV_4.setVisibility(View.VISIBLE);

                        register_codeT_1.setVisibility(View.VISIBLE);
                        register_codeT_1.setText(s);
                        register_codeT_2.setVisibility(View.GONE);
                        register_codeT_3.setVisibility(View.GONE);
                        register_codeT_4.setVisibility(View.GONE);
                        break;
                    case 2:
                        register_codeV_1.setVisibility(View.GONE);
                        register_codeV_2.setVisibility(View.GONE);
                        register_codeV_3.setVisibility(View.VISIBLE);
                        register_codeV_4.setVisibility(View.VISIBLE);

                        register_codeT_1.setVisibility(View.VISIBLE);
                        register_codeT_1.setText(s.subSequence(0, 1));
                        register_codeT_2.setVisibility(View.VISIBLE);
                        register_codeT_2.setText(s.subSequence(1, 2));
                        register_codeT_3.setVisibility(View.GONE);
                        register_codeT_4.setVisibility(View.GONE);
                        break;
                    case 3:
                        register_codeV_1.setVisibility(View.GONE);
                        register_codeV_2.setVisibility(View.GONE);
                        register_codeV_3.setVisibility(View.GONE);
                        register_codeV_4.setVisibility(View.VISIBLE);

                        register_codeT_1.setVisibility(View.VISIBLE);
                        register_codeT_1.setText(s.subSequence(0, 1));
                        register_codeT_2.setVisibility(View.VISIBLE);
                        register_codeT_2.setText(s.subSequence(1, 2));
                        register_codeT_3.setVisibility(View.VISIBLE);
                        register_codeT_3.setText(s.subSequence(2, 3));
                        register_codeT_4.setVisibility(View.GONE);
                        break;
                    case 4:
                        register_codeV_1.setVisibility(View.GONE);
                        register_codeV_2.setVisibility(View.GONE);
                        register_codeV_3.setVisibility(View.GONE);
                        register_codeV_4.setVisibility(View.GONE);

                        register_codeT_1.setVisibility(View.VISIBLE);
                        register_codeT_1.setText(s.subSequence(0, 1));
                        register_codeT_2.setVisibility(View.VISIBLE);
                        register_codeT_2.setText(s.subSequence(1, 2));
                        register_codeT_3.setVisibility(View.VISIBLE);
                        register_codeT_3.setText(s.subSequence(2, 3));
                        register_codeT_4.setVisibility(View.VISIBLE);
                        register_codeT_4.setText(s.subSequence(3, 4));
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
