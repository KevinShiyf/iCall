package com.icall.free.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icall.free.R;
import com.icall.free.entity.ContactsEntity;
import com.icall.free.entity.CountryCodeEntity;
import com.icall.free.util.CodeProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactsAdapter extends BaseAdapter {
    private ArrayList<ContactsEntity> mContacts = new ArrayList<ContactsEntity>();
    private LayoutInflater mInflater;
    private Activity mActivity;

    private int type = 0; // 0-默认 1-添加号码到已有联系人 2-邀请好友短信
    private String inserPhoneString; // 添加的号码

    public ContactsAdapter(Activity activity, ArrayList<ContactsEntity> contacts) {
        mContacts.clear();
        this.mActivity = activity;
        this.mContacts.addAll(contacts);
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
    }

//    public ContactsAdapter(Activity activity, ArrayList<ContactsEntity> contacts, int type) {
//        Log.e("------------------", "mType:" + type);
//        mContacts.clear();
//        this.mActivity = activity;
//        this.mContacts.addAll(contacts);
//        this.type = type;
//        mInflater = LayoutInflater.from(activity);
//        mActivity = activity;
//    }

//    public ContactsAdapter(Activity activity, ArrayList<ContactsEntity> contacts, int type, String number) {
//        mContacts.clear();
//        this.mActivity = activity;
//        this.type = type;
//        this.inserPhoneString = number;
//        this.mContacts.addAll(contacts);
//        mInflater = LayoutInflater.from(activity);
//        mActivity = activity;
//    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.contacts_item_layout, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.contacts_item_name_tv = (TextView) convertView.findViewById(R.id.contacts_item_name_tv);
            holder.contacts_item_phone_tv = (TextView) convertView.findViewById(R.id.contacts_item_phone_tv);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ContactsEntity model = this.mContacts.get(position);

        holder.contacts_item_name_tv.setText(model.getDisplayName());
        holder.contacts_item_phone_tv.setText(model.getPhoneNum());
//        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (type == 0) {
//                    Intent intent = new Intent(mActivity, ContactsDetailActivity.class)
//                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(DefineConstant.Contacts.MODEL, model)
//                            .putExtra(DefineConstant.Contacts.FROM_FLAG, DefineConstant.Contacts.FROM_CONTACTS);
//                    mActivity.startActivity(intent);
//                } else if (type == 1) {
//                    Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
//                    intent.setType("vnd.android.cursor.item/person");
//                    intent.setType("vnd.android.cursor.item/contact");
//                    intent.setType("vnd.android.cursor.item/raw_contact");
//                    intent.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, model.getDisplayName());
//                    intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, inserPhoneString);
//                    intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE_TYPE, 3);
//
//                    mActivity.startActivity(intent);
//                } else if (type == 2) {
//
//
//                }
//            }
//        });

        return convertView;
    }

    private class ViewHolder {
        public TextView contacts_item_name_tv;
        public TextView contacts_item_phone_tv;
    }

    public void setData(ArrayList<ContactsEntity> result) {
        this.mContacts.clear();
        this.mContacts.addAll(result);
        notifyDataSetChanged();
    }

//    public int getRandomBackground() {
//        int [] backgroundList = new int[] {
//                R.drawable.contacts_name_bg_a7dbc2,
//                R.drawable.contacts_name_bg_a9d2f2,
//                R.drawable.contacts_name_bg_abdba7,
//                R.drawable.contacts_name_bg_b2c6f3,
//                R.drawable.contacts_name_bg_c5b3eb,
//                R.drawable.contacts_name_bg_f3b2b2,
//                R.drawable.contacts_name_bg_f7d4a3};
//
//        int randomNum = (int)(Math.random() * 7);
//        return backgroundList[randomNum];
//    }
}
