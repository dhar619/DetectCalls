package com.example.gateway_olahive.detectincomingcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PhoneStateReceiver extends BroadcastReceiver {

    private WindowManager wm;
    private static LinearLayout ly1;
    private WindowManager.LayoutParams params1;
    TextView callerNo;
    View hidden;

    public void onReceive(Context arg0, Intent arg1) {
        String state = arg1.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            wm = (WindowManager) arg0.getSystemService(Context.WINDOW_SERVICE);
            params1 = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);

            params1.height = 300;
            params1.width = WindowManager.LayoutParams.MATCH_PARENT;
            params1.gravity= Gravity.TOP;
            ly1 = new LinearLayout(arg0);
            ly1.setOrientation(LinearLayout.VERTICAL);
            ly1.setBackgroundColor(Color.WHITE);
            hidden = LayoutInflater.from(arg0).inflate(R.layout.call_screen_overlay,ly1,false);
            callerNo = (TextView) hidden.findViewById(R.id.textView);
            callerNo.setText(arg1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
            ly1.addView(hidden);
            wm.addView(ly1, params1);
        }

        if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
            WindowManager wm = (WindowManager) arg0.getSystemService(Context.WINDOW_SERVICE);
            if(ly1!=null)
            {
                ly1.removeView(hidden);
                wm.removeView(ly1);
                ly1 = null;
            }
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            WindowManager wm = (WindowManager) arg0.getSystemService(Context.WINDOW_SERVICE);
            if(ly1!=null)
            {
                ly1.removeView(hidden);
                wm.removeView(ly1);
                ly1 = null;
            }
        }


    }
}
