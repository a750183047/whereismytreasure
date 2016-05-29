package com.yan.whereismytreasure.Utils;

import android.app.Activity;
import android.app.ProgressDialog;

import com.yan.whereismytreasure.App.Global;

/**
 * Util
 * Created by a7501 on 2016/5/28.
 */
public class ShowProgressBar {

    private ProgressDialog mProgressDialog;

    /**
     * 弹窗提示正在进行操作，已作了处理可以在子线程中调用
     *
     * @param mess 要提示的内容
     * @param cancelable 是否可取消
     */
    public  void showProgressDialog(final Activity activity, String mess, boolean cancelable) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true); //
        mProgressDialog.setCancelable(cancelable); // 用户是否可以取消
        mProgressDialog.setMessage(mess); // 设置提示的内容
        mProgressDialog.setCanceledOnTouchOutside(false); // 点击外部不会销毁

        // 主线程中操作
        Global.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // activity销毁了弹窗会报错
                if (!activity.isFinishing())
                    mProgressDialog.show();
            }
        });
    }

    /**
     * 重载
     * @param mess
     */
    public void showProgressDialog(Activity activity,String mess){
        showProgressDialog(activity,mess,false);
    }

    /** 销毁加载提示框，已作了处理，可以在子线程中调用 */
    public void dismissProgressDialog() {
        Global.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 主线程中操作
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        });
    }
}
