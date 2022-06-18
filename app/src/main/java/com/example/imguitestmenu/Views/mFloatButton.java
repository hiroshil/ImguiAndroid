package com.example.imguitestmenu.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.imguitestmenu.GLES3JNILib;
import com.example.imguitestmenu.R;
import com.example.imguitestmenu.Services.FloatService;
import com.example.imguitestmenu.Utils.ClipboardUtil;

public class mFloatButton {

    public Button mButtonCopy = null;
    public Button mButtonPaste = null;
    public Button mButtonSelect_all = null;
    public Button mButtonCut = null;
    public LinearLayout.LayoutParams layoutParamsmButtonCopy;
    public LinearLayout.LayoutParams layoutParamsmButtonPaste;
    public LinearLayout.LayoutParams layoutParamsmButtonSelect_all;
    public LinearLayout.LayoutParams layoutParamsmButtonCut;
    public LinearLayout.LayoutParams layoutParamsRoot;
    public LinearLayout layoutRoot;
    public static Context mContext;
    public mFloatButton floatButton;


    public mFloatButton(Context context, int size){
        mContext = context;
        floatButton = this;

        //根布局参数
        layoutParamsRoot = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsRoot.gravity = Gravity.CENTER;
        //根布局
        layoutRoot = new LinearLayout(context);
        layoutRoot.setLayoutParams(layoutParamsRoot);
        layoutRoot.setOrientation(LinearLayout.HORIZONTAL);

        //Button布局
        layoutParamsmButtonCopy = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsmButtonCopy.setMarginStart(dip2px(context,0.15f));
        layoutParamsmButtonCopy.setMarginEnd(dip2px(context,0.15f));
        mButtonCopy = new Button(context);
        mButtonCopy.setId(View.generateViewId());
        mButtonCopy.setPadding(size+5, size, size, size);//设置padding
        mButtonCopy.setMinWidth(size);//Button中的方法    改变Button(TextView)中的mMinWidth
        mButtonCopy.setMinHeight(size);//Button中的方法   改变Button(TextView)中的mMinHeight
        mButtonCopy.setMinimumHeight(size);//View中的方法 改变View中的mMinHeight
        mButtonCopy.setMinimumWidth(size);//View中的方法  改变View中的mMinWidth


        mButtonCopy.setText("复制");
        mButtonCopy.setTextSize(12);
        mButtonCopy.setTextColor(Color.WHITE);
        mButtonCopy.setBackground(context.getDrawable(R.drawable.edit_back_start));
        mButtonCopy.setAlpha(0.7f);
        layoutRoot.addView(mButtonCopy, layoutParamsmButtonCopy);

        layoutParamsmButtonPaste = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsmButtonPaste.setMarginStart(dip2px(context,0.15f));
        layoutParamsmButtonPaste.setMarginEnd(dip2px(context,0.15f));

        mButtonPaste = new Button(context);
        mButtonPaste.setId(View.generateViewId());
        mButtonPaste.setPadding(size, size, size, size);//设置padding
        mButtonPaste.setMinWidth(size);//Button中的方法    改变Button(TextView)中的mMinWidth
        mButtonPaste.setMinHeight(size);//Button中的方法   改变Button(TextView)中的mMinHeight
        mButtonPaste.setMinimumHeight(size);//View中的方法 改变View中的mMinHeight
        mButtonPaste.setMinimumWidth(size);//View中的方法  改变View中的mMinWidth
        mButtonPaste.setText("粘贴");
        mButtonPaste.setTextSize(12);
        mButtonPaste.setTextColor(Color.WHITE);
        mButtonPaste.setBackground(context.getDrawable((R.drawable.edit_back)));
        mButtonPaste.setAlpha(0.7f);
        layoutRoot.addView(mButtonPaste, layoutParamsmButtonPaste);


        layoutParamsmButtonSelect_all = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsmButtonSelect_all.setMarginStart(dip2px(context,0.15f));
        layoutParamsmButtonSelect_all.setMarginEnd(dip2px(context,0.15f));
        mButtonSelect_all = new Button(context);
        mButtonSelect_all.setId(View.generateViewId());
        mButtonSelect_all.setPadding(size, size, size, size);//设置padding
        mButtonSelect_all.setMinWidth(size);//Button中的方法    改变Button(TextView)中的mMinWidth
        mButtonSelect_all.setMinHeight(size);//Button中的方法   改变Button(TextView)中的mMinHeight
        mButtonSelect_all.setMinimumHeight(size);//View中的方法 改变View中的mMinHeight
        mButtonSelect_all.setMinimumWidth(size);//View中的方法  改变View中的mMinWidth
        mButtonSelect_all.setText("全选");
        mButtonSelect_all.setTextSize(12);
        mButtonSelect_all.setTextColor(Color.WHITE);
        mButtonSelect_all.setBackground(context.getDrawable(R.drawable.edit_back));
        mButtonSelect_all.setAlpha(0.7f);
        layoutRoot.addView(mButtonSelect_all, layoutParamsmButtonSelect_all);

        layoutParamsmButtonCut = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsmButtonCut.setMarginStart(dip2px(context,0.15f));
        layoutParamsmButtonCut.setMarginEnd(dip2px(context,0.15f));
        mButtonCut = new Button(context);
        mButtonCut.setId(View.generateViewId());
        mButtonCut.setPadding(size, size, size+5, size);//设置padding
        mButtonCut.setMinWidth(size);//Button中的方法    改变Button(TextView)中的mMinWidth
        mButtonCut.setMinHeight(size);//Button中的方法   改变Button(TextView)中的mMinHeight
        mButtonCut.setMinimumHeight(size);//View中的方法 改变View中的mMinHeight
        mButtonCut.setMinimumWidth(size);//View中的方法  改变View中的mMinWidth
        mButtonCut.setText("剪切");
        mButtonCut.setTextSize(12);
        mButtonCut.setTextColor(Color.WHITE);
        mButtonCut.setBackground(context.getDrawable(R.drawable.edit_back_end));
        mButtonCut.setAlpha(0.7f);
        layoutRoot.addView(mButtonCut, layoutParamsmButtonCut);


        thislisten();
        Buttonslisten();

    }
    public void thislisten(){
        layoutRoot.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_OUTSIDE:
                        Log.e("NDK","点击外面，关闭mFloatButton");
                        FloatService.remove_mFloatButton();
                        break;
                }
                return true;
            }
        });
    }

    public void Buttonslisten(){
        mButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("NDK","onClick.mButtonCopy");
                copy(GLES3JNILib.Copy());
            }
        });

        mButtonPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("NDK","onClick.mButtonPaste");
                GLES3JNILib.Paste(getCopy());
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
        });

        mButtonSelect_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GLES3JNILib.Select_all();
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
               // Log.e("NDK","Select_all.size="+myJNI.Select_all());
            }
        });
        mButtonCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                );
                Log.e("NDK","onClick.mButtonShear");
                copy(GLES3JNILib.Cut());
            }
        });
    }








    //系统剪切板  复制      s为要复制的内容
    public static void copy(CharSequence aa) {
        // 把数据集设置（复制）到剪贴板
        ClipboardUtil.getInstance().copyText("text",aa);
        Log.e("NDK","copy.data="+aa);
    }


    //系统剪贴板-获取:
    public static String getCopy() {
        // 返回数据
        return ClipboardUtil.getInstance().getClipText();
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
