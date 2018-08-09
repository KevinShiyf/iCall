package com.icall.free.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.icall.free.R;
import com.icall.free.activity.demo.ThinkAndroidBaseActivity;
import com.icall.free.adapter.CountryCodeAdapter;
import com.ta.annotation.TAInjectView;

public class CountryCodeActivity extends ThinkAndroidBaseActivity {

    CountryCodeAdapter codeAdapter;

    @TAInjectView(id = R.id.country_select_listview)
    ListView countrySelectListview;

    @Override
    protected void onAfterOnCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onAfterOnCreate(savedInstanceState);
        setTitle(R.string.signin);
        setContentView(R.layout.countrycode);
    }

    @Override
    protected void onAfterSetContentView() {
        // TODO Auto-generated method stub
        super.onAfterSetContentView();
        codeAdapter = new CountryCodeAdapter(this, getTAApplication());
        countrySelectListview.setAdapter(codeAdapter);
        codeAdapter.notifyDataSetChanged();
    }
}
