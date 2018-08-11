package com.icall.free.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;

/**
 * @author: xiaozhenhua
 * @data: 2018-01-25
 */

public class GirlsFragment extends BaseFragment {
    @InjectAll
    Views views;

    class Views {
        public TextView test_tv;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.test_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;
    }

    @InjectInit
    private void init() {
        views.test_tv.setText("girls");
    }
}

