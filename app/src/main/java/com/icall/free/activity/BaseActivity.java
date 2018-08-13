package com.icall.free.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icall.free.R;
import com.icall.free.util.StringUtil;

public class BaseActivity extends FragmentActivity {
    protected Dialog mTipsDialog; // MyDialog ProgressDialog
    protected TextView mDialogTipTV;

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivity(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void showTipsDialog(String title) {
        showTipsDialog(title, true);
    }

    protected void showTipsDialog(String title, boolean cancel) {
        if (mTipsDialog != null && mTipsDialog.isShowing() && mDialogTipTV != null) {
            mDialogTipTV.setVisibility(View.VISIBLE);
            mDialogTipTV.setText(title);
            return;
        }

        mTipsDialog = createLoadingDialog(this, title);//new MyDialog(this, R.style.MyDialogStyle, MyDialog.PROGRESS_TIPS);
        if (!cancel) {
            mTipsDialog.setCancelable(false);
            mTipsDialog.setCanceledOnTouchOutside(false);
        }
        mTipsDialog.show();

        setProgressDialogSizeAndPosition(mTipsDialog);
    }

    protected void hideTipsDialog() {
        if (mTipsDialog != null) {
            mTipsDialog.dismiss();
        }
    }

    protected boolean isShow() {
        if (mTipsDialog != null) {
            return mTipsDialog.isShowing();
        }
        return false;
    }

    protected void displayToast(String message) {
        displayToast(message, Toast.LENGTH_SHORT);
    }

    protected void displayToast(int resId) {
        displayToast(getText(resId).toString(), Toast.LENGTH_SHORT);
    }

    protected void displayToast(int resId, int duration) {
        displayToast(getText(resId).toString(), duration);
    }

    protected void displayToast(String message, int duration) {
        Context context = getApplicationContext();
        if (context == null || message == null || message.length() == 0) {
            return;
        }
        Toast toast = Toast.makeText(this, message, duration);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
    public Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        mDialogTipTV = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (StringUtil.isNull(msg)) {
            mDialogTipTV.setVisibility(View.GONE);
        } else {
            mDialogTipTV.setText(msg);// 设置加载信息
        }


        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    protected void setProgressDialogSizeAndPosition(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
}
