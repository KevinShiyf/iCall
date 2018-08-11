package com.icall.free.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.icall.free.R;
import com.icall.free.adapter.CountryCodeAdapter;

@InjectLayer(R.layout.country_code_activity)
public class CountryCodeActivity extends BaseActivity {

    CountryCodeAdapter codeAdapter;

    @InjectView
    ListView country_select_listview;

    @InjectInit
    protected void init() {
        // TODO Auto-generated method stub
        codeAdapter = new CountryCodeAdapter(this);
        country_select_listview.setAdapter(codeAdapter);
        codeAdapter.notifyDataSetChanged();
        country_select_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
