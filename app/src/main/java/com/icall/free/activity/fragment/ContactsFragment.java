package com.icall.free.activity.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;
import com.icall.free.adapter.ContactsAdapter;
import com.icall.free.adapter.RecentsAdapter;
import com.icall.free.entity.CallLogEntity;
import com.icall.free.entity.ContactsEntity;
import com.icall.free.event.MessageType;
import com.icall.free.util.CallLogUtil;
import com.icall.free.util.CallLogsNameSyncloader;
import com.icall.free.util.ContactsUtil;
import com.icall.free.util.InputMethodUtil;
import com.icall.free.util.StringUtil;

import java.util.ArrayList;

public class ContactsFragment extends BaseFragment  {
    @InjectAll
    Views views;

    class Views{
        public ListView contacts_listview;
    }

    private View mView;
    private View mAtoZView;
    private ArrayList<ContactsEntity> mContacts = new ArrayList<ContactsEntity>();
    private View delView;

    private int oldid = -100;
    public int now_index = 0;

    private ContactsAdapter contactsAdapter;

    private View mCurrentLetterView;

    private DisapearThread disapearThread = new DisapearThread();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.contacts_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;
    }

    @InjectInit
    private void init() {
        views.contacts_listview.setFocusable(false);

        mContacts.clear();
        // mContacts = ContactsUtil.getSortContacts(mContext);
        // ContactsUtil.mContacts.addAll(mContacts);
        contactsAdapter = new ContactsAdapter(getActivity(), mContacts);
        views.contacts_listview.setAdapter(contactsAdapter);
        refreshContacts();

        // a-z
        // View lettersView = mView.findViewById(R.id.aazz);
//        new GetContacts().execute();
        SetUPLetterNavio();
        populateFastClick(); //
        views.contacts_listview.setOnScrollListener(new CoolScrollListener());
    }

    private void refreshContacts() {
        new AsyncTask<Void, Integer, ArrayList<ContactsEntity>>() {

            protected void onPreExecute() {
                // showProgressDialog();
            };

            @Override
            protected ArrayList<ContactsEntity> doInBackground(Void... params) {
                mContacts.clear();
                mContacts = ContactsUtil.getSortContacts(getActivity());
                ContactsUtil.mContacts.clear();
                ContactsUtil.mContacts.addAll(mContacts);
                return mContacts;
            }

            @Override
            protected void onPostExecute(ArrayList<ContactsEntity> result) {
                super.onPostExecute(result);
                contactsAdapter.setData(result);
            }
        }.execute();
    }

    public void onEventMainThread(Message msg) {
        switch (MessageType.values()[msg.what]) {
            case LOCAL_CONTACTS_CHANGE:
                refreshContacts();
                break;
            default:
                break;
        }
    }

    private class CoolScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            InputMethodUtil.hide(view);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    //字母导航相关

    private void populateFastClick() {
//        if (mCurrentLetterView == null) {
//            mCurrentLetterView = LayoutInflater.from(getActivity()).inflate(R.layout.list_popup_char_hint, null);
//            tv_content1 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num1);
//            tv_content2 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num2);
//            tv_content3 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num3);
//            tv_content4 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num4);
//            tv_content5 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num5);
//            tv_content6 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num6);
//            tv_content7 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num7);
//            tv_content8 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num8);
//            tv_content9 = (TextView) mCurrentLetterView.findViewById(R.id.tv_num9);
//        }
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            handler.post(new UpdateUi());
        }
    }

    private class UpdateUi implements Runnable {
        public void run() {
            SetUPLetterNavio();// 字母导航
        }
    }

    /**
     * 设置字母导航数据
     */
    public void SetUPLetterNavio() {
        mAtoZView = mView.findViewById(R.id.aazz);// 父
        final int count = ((LinearLayout) mAtoZView).getChildCount();
        mAtoZView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x1 = (int) event.getX();
                int y1 = (int) event.getY();
                Rect frame = new Rect();
                v.getHitRect(frame);
                if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                    for (int index = 0; index < count; index++) {
                        View view = ((ViewGroup) mAtoZView).getChildAt(index);
                        view.getHitRect(frame);
                        if (frame.contains(x1, y1)) {
                            InputMethodUtil.hide(v);
                            showUpLetter(view);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void showUpLetter(View view) {
        String mCurrentLetter = (String) view.getTag();
        if ("#".equals(mCurrentLetter)) {
            views.contacts_listview.setSelection(views.contacts_listview.getCount() - 1);
            if (oldid != -100) {
//                ((TextView) mView.findViewById(oldid)).setTextColor(this.getResources().getColor(R.color.vs_gray_deep));
            }
        } else {
            if (oldid == -100) {
                oldid = view.getId();
//                ((TextView) mView.findViewById(oldid)).setTextColor(this.getResources().getColor(R.color.croci));
            } else {
//                ((TextView) mView.findViewById(oldid)).setTextColor(this.getResources().getColor(R.color.vs_gray_deep));
                oldid = view.getId();
//                ((TextView) mView.findViewById(oldid)).setTextColor(this.getResources().getColor(R.color.croci));
            }
        }
//        for (int i = 0; i < mAzStr.length; i++) {
//            if (mCurrentLetter.equals(mAzStr[i])) {
//                now_index = i;
//            }
//        }
//        if (now_index == 2) {
//            tv_content1.setVisibility(View.GONE);
//            tv_content2.setVisibility(View.VISIBLE);
//            tv_content3.setVisibility(View.VISIBLE);
//            tv_content2.setText(mAzStr[now_index - 2]);
//            tv_content2.setTextColor(CommonUtil.setTransparency(3));
//            tv_content3.setText(mAzStr[now_index - 1]);
//            tv_content3.setTextColor(CommonUtil.setTransparency(2));
//            setAD();
//        } else if (now_index == 1) {
//            tv_content1.setVisibility(View.GONE);
//            tv_content2.setVisibility(View.GONE);
//            tv_content3.setVisibility(View.VISIBLE);
//            tv_content3.setText(mAzStr[now_index - 1]);
//            tv_content3.setTextColor(CommonUtil.setTransparency(2));
//            setAD();
//        } else if (now_index == 0) {
//            tv_content1.setVisibility(View.GONE);
//            tv_content2.setVisibility(View.GONE);
//            tv_content3.setVisibility(View.GONE);
//            setAD();
//        } else if (now_index == 26) {
//            tv_content5.setVisibility(View.GONE);
//            tv_content6.setVisibility(View.GONE);
//            tv_content7.setVisibility(View.GONE);
//            tv_content8.setVisibility(View.GONE);
//            tv_content9.setVisibility(View.GONE);
//            setSZ();
//        } else if (now_index == 25) {
//            tv_content5.setVisibility(View.VISIBLE);
//            tv_content6.setVisibility(View.GONE);
//            tv_content7.setVisibility(View.GONE);
//            tv_content8.setVisibility(View.GONE);
//            tv_content9.setVisibility(View.GONE);
//            tv_content5.setText(mAzStr[now_index + 1]);
//            tv_content5.setTextColor(CommonUtil.setTransparency(2));
//            setSZ();
//        } else if (now_index == 24) {
//            tv_content5.setVisibility(View.VISIBLE);
//            tv_content6.setVisibility(View.VISIBLE);
//            tv_content7.setVisibility(View.GONE);
//            tv_content8.setVisibility(View.GONE);
//            tv_content9.setVisibility(View.GONE);
//            tv_content5.setText(mAzStr[now_index + 1]);
//            tv_content5.setTextColor(CommonUtil.setTransparency(2));
//            tv_content6.setText(mAzStr[now_index + 2]);
//            tv_content6.setTextColor(CommonUtil.setTransparency(3));
//            setSZ();
//        } else if (now_index == 23) {
//            tv_content5.setVisibility(View.VISIBLE);
//            tv_content6.setVisibility(View.VISIBLE);
//            tv_content7.setVisibility(View.VISIBLE);
//            tv_content8.setVisibility(View.GONE);
//            tv_content9.setVisibility(View.GONE);
//            tv_content5.setText(mAzStr[now_index + 1]);
//            tv_content5.setTextColor(CommonUtil.setTransparency(2));
//            tv_content6.setText(mAzStr[now_index + 2]);
//            tv_content6.setTextColor(CommonUtil.setTransparency(3));
//            tv_content7.setText(mAzStr[now_index + 3]);
//            tv_content7.setTextColor(CommonUtil.setTransparency(4));
//            setSZ();
//        } else if (now_index == 22) {
//            tv_content5.setVisibility(View.VISIBLE);
//            tv_content6.setVisibility(View.VISIBLE);
//            tv_content7.setVisibility(View.VISIBLE);
//            tv_content8.setVisibility(View.VISIBLE);
//            tv_content9.setVisibility(View.GONE);
//            tv_content5.setText(mAzStr[now_index + 1]);
//            tv_content5.setTextColor(CommonUtil.setTransparency(2));
//            tv_content6.setText(mAzStr[now_index + 2]);
//            tv_content6.setTextColor(CommonUtil.setTransparency(3));
//            tv_content7.setText(mAzStr[now_index + 3]);
//            tv_content7.setTextColor(CommonUtil.setTransparency(4));
//            tv_content8.setText(mAzStr[now_index + 4]);
//            tv_content8.setTextColor(CommonUtil.setTransparency(5));
//            setSZ();
//        } else {
//            tv_content1.setVisibility(View.VISIBLE);
//            tv_content2.setVisibility(View.VISIBLE);
//            tv_content3.setVisibility(View.VISIBLE);
//            tv_content1.setText(mAzStr[now_index - 3]);
//            tv_content1.setTextColor(CommonUtil.setTransparency(4));
//            tv_content2.setText(mAzStr[now_index - 2]);
//            tv_content2.setTextColor(CommonUtil.setTransparency(3));
//            tv_content3.setText(mAzStr[now_index - 1]);
//            tv_content3.setTextColor(CommonUtil.setTransparency(2));
//            setAD();
//        }
        // cl_dialog.setVisibility(View.VISIBLE);
//        handler.removeCallbacks(disapearThread);
//        handler.postDelayed(disapearThread, 300);
        int localPosition = binSearch(ContactsUtil.mContacts, mCurrentLetter); // 接收返回值
        if (localPosition != -1) {
            // 让List指向对应位置的Item
            // modified by WangGaozhuo
            // since 2015-04-28
            views.contacts_listview.setSelection(localPosition);
        }
    }

    private int binSearch(ArrayList<ContactsEntity> list, String s) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (s.equalsIgnoreCase("" + list.get(i).getFirstLetterFromSortKey())) { // 不区分大小写
                return i;
            }
        }
        return -1;
    }

    private class DisapearThread implements Runnable {
        public void run() {
//            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                // cl_dialog.setVisibility(View.INVISIBLE);
//            }
        }
    }

}
