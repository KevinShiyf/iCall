package com.icall.free.activity.fragment;


import android.os.Bundle;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;
import com.icall.free.activity.GameActivity;
import com.icall.free.event.MessageType;

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
        @InjectBinder(method = "click", listeners = {OnClick.class})
        public TextView me_checkin_reward_tv, me_game_reward_tv, me_bind_reward_tv, me_recommend_reward_tv, me_share_reward_tv;
        public TextView me_name_tv;
        public TextView me_credits_tv;
        public TextView me_lv_tv;


        public RelativeLayout me_signin_rl, me_game_rl, me_bind_phone_rl, me_recommend_rl, me_share_rl;

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
                Message msg = Message.obtain();
                msg.what = MessageType.OPEN_LEFT_DRAWER.ordinal();
                EventBus.getDefault().post(msg);
                break;
            case R.id.me_game_reward_tv:
                startActivity(GameActivity.class);
                break;
        }
    }
}

