package com.icall.free.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.util.InputMethodUtil;

import java.util.List;

@InjectLayer(R.layout.google_search_activity)
public class GoogleSearchActivity extends BaseActivity {

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    TextView google_delete_tv;
    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    RelativeLayout google_search_iv;
    @InjectView
    EditText google_search_et;
    @InjectView
    ListView google_search_listview;

    public void click(View v) {
        switch (v.getId()) {
            case R.id.google_delete_tv:

                break;
        }
    }

    @InjectInit
    private void init() {
        google_search_et.requestFocus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InputMethodUtil.display(google_search_et);
            }
        }).start();
    }

}
