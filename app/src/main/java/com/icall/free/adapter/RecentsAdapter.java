package com.icall.free.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icall.free.R;
import com.icall.free.entity.CallLogEntity;
import com.icall.free.util.CallLogsNameSyncloader;
import com.icall.free.util.CallLogsNameSyncloader.OnNameLoadListener;
import com.icall.free.util.LocalNameFinder;
import com.icall.free.util.TimesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecentsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<CallLogEntity> mCallLogsList;

    private int status = 0; // 0-默认状态 1-删除状态
    private OnNameLoadListener nameLoadListener;

    /**
     * add by wanghao 2015-9-8 key-记录位置 value-状态 false-未选择 true-选中
     */


    private Holder holder;

    public RecentsAdapter(Context context, ArrayList<CallLogEntity> mCallLogsList,
                               OnNameLoadListener nameLoadListener) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mCallLogsList = mCallLogsList;
        this.nameLoadListener = nameLoadListener;
    }

    @Override
    public int getCount() {
        return mCallLogsList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mCallLogsList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) { // viewsMap.get(position) == null
            holder = new Holder();
            convertView = inflater.inflate(R.layout.recents_item_layout, null);
            holder.recents_item_call_iv = (ImageView) convertView.findViewById(R.id.recents_item_call_iv);
            holder.recents_item_name_tv = (TextView) convertView.findViewById(R.id.recents_item_name_tv);
            holder.recents_item_add_tv = (TextView) convertView.findViewById(R.id.recents_item_add_tv);
            holder.recents_item_time_tv = (TextView) convertView.findViewById(R.id.recents_item_time_tv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final CallLogEntity item = (CallLogEntity) getItem(position);

//        if (status == 0) {
//            // holder.tipsLayout.setVisibility(View.VISIBLE);
//            holder.tipsIV.setImageResource(R.drawable.home_icon_details);
//        } else {
//            if (item.isSelected()) {
//                holder.tipsIV.setImageResource(R.drawable.call_list_choice);
//            } else {
//                holder.tipsIV.setImageResource(R.drawable.call_list_hollow);
//            }
//        }

        String tmpName = item.getName();
        String tmpPhone = item.getPhone();
        int tmpType = item.getType();
        if (tmpType == CallLog.Calls.INCOMING_TYPE) {

//            holder.typeIV.setVisibility(View.VISIBLE);
//            holder.upTV.setTextColor(mContext.getResources().getColor(R.color.rgb_000000));
        } else if (tmpType == CallLog.Calls.OUTGOING_TYPE) {
//            holder.typeIV.setVisibility(View.VISIBLE);
//            holder.typeIV.setImageResource(R.drawable.home_icon_dail);
//            holder.upTV.setTextColor(mContext.getResources().getColor(R.color.rgb_000000));
        } else {
//            holder.typeIV.setVisibility(View.INVISIBLE);
//            holder.upTV.setTextColor(mContext.getResources().getColor(R.color.rgb_ffff3c30));
        }

        CallLogsNameSyncloader syncLoader = CallLogsNameSyncloader.getInstance();
        String newName = syncLoader.getCacheName(tmpPhone);
        if (newName == null) {
            holder.recents_item_name_tv.setTag(tmpPhone + "up");
            syncLoader.loadImage(position, tmpPhone, nameLoadListener);
        } else {
            holder.recents_item_name_tv.setTag(null);
            tmpName = newName;
        }
        if (tmpName != null && tmpName.length() > 0) {
            holder.recents_item_name_tv.setText(tmpName);
        } else {
            if (tmpPhone == null || tmpPhone.length() < 3) {
                holder.recents_item_name_tv.setText(mContext.getResources().getString(R.string.unkonwn_phone));
            } else {
                holder.recents_item_name_tv.setText(tmpPhone);
            }
        }
        // holder.downTV设置归属地
        String area = LocalNameFinder.getInstance().findLocalName(tmpPhone, false);
        if (area != null && area.length() > 0) { // CallLogs.allAreaMaps.containsKey(tmpPhone)
            holder.recents_item_add_tv.setText(area);
        } else {
            holder.recents_item_add_tv.setText("");
        }
        holder.recents_item_time_tv.setText(dateShow(item.getDate()));

//        holder.tipsLayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                if (status == 0) {
//                    Intent intent = new Intent(mContext, CallLogDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("detail_calls_phone", item.getPhone());
//                    intent.putExtras(bundle);
//                    mContext.startActivity(intent);
//                } else {
//                    Message msg = Message.obtain();
//                    msg.what = MessageType.CHANGE_DELETE_CALLLOGS_COUNTS.ordinal();
//                    msg.arg1 = position;
//                    EventBus.getDefault().post(msg);
//                }
//            }
//        });
        return convertView;
    }


    public class Holder {
        public ImageView recents_item_call_iv;
        public TextView recents_item_name_tv;
        public TextView recents_item_add_tv;
        public TextView recents_item_time_tv;
    }

    public void setStatus(int flag) {
        status = flag;
    }

    /**
     * add by wanghao 2015-9-8
     *
     * @param flag
     *            adapter状态
     */
    public void setSelectedStatus(int flag) {
        status = flag;
    }

    private String dateShow(long time) {
        switch (TimesUtils.countDate(time)) {
            case 0:
                SimpleDateFormat hour = TimesUtils.getSimpleFormat("HH:mm");
                return hour.format(new Date(time));
            case -1:
                return mContext.getString(R.string.yesterday);
            case -2:
            case -3:
            case -4:
            case -5:
            case -6:
                return getWeekOfDate(new Date(time));
            case 0x999:
                SimpleDateFormat year = TimesUtils.getSimpleFormat("yyyy/MM/dd");
                return year.format(new Date(time));
            default:
                SimpleDateFormat more = TimesUtils.getSimpleFormat("MM/dd");
                return more.format(new Date(time));
        }
    }

    public String getWeekOfDate(Date dt) {
        int[] weekDays = {R.string.sunday, R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday, R.string.friday, R.string.saturday};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return mContext.getString(weekDays[w]);
    }

}
