package com.icall.free.activity.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;

/**
 * @author: xiaozhenhua
 * @data: 2018-01-25
 */

public class MeFragment extends BaseFragment {
    @InjectAll
    Views views;

    class Views {
        @InjectBinder(method = "click", listeners = {OnClick.class})
        public ImageView me_head_iv;

        public TextView me_name_tv;
        public TextView me_credits_tv;
        public TextView me_lv_tv;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.me_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;
    }

    private void initViews() {

    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.me_head_iv:
//                drawerLayout.showContextMenu();
                break;
        }
    }
}

