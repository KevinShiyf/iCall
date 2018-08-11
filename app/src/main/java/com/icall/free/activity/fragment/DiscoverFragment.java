package com.icall.free.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.icall.free.R;

public class DiscoverFragment extends BaseFragment  {
    private View mView;
    private ImageButton roamButton;

    public DiscoverFragment() {

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


    private static DiscoverFragment mInstance = null;

    public static Fragment newInstance() {
        if (mInstance == null) {
            mInstance = new DiscoverFragment();
        }
        return mInstance;
    }
}
