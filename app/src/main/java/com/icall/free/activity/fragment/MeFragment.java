package com.icall.free.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.icall.free.R;

import java.util.ArrayList;

/**
 * @author: xiaozhenhua
 * @data: 2018-01-25
 */

public class MeFragment extends BaseFragment {
    private View mView;
    private ImageButton roamButton;

    public MeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mView = inflater.inflate(R.layout.me_activity, container, false);
        initViews();
        return this.mView;
    }

    private void initViews() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private static MeFragment mInstance = null;

    public static Fragment newInstance() {
        if (mInstance == null) {
            mInstance = new MeFragment();
        }
        return mInstance;
    }
}

