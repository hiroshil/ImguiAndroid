package com.example.imguitestmenu.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.example.imguitestmenu.Utils.ClipboardUtil;
import com.example.imguitestmenu.Views.ContainerView;
import com.example.imguitestmenu.Views.WindowManagerLayoutParams;
import com.example.imguitestmenu.Views.mFloatButton;


// AbdroidManifest.xml中添加以下配置:

//<!-- 悬浮窗所需权限 -->
//<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

// 注册当前悬浮窗服务
//<service
//    android:name="com.sci.FloatService"
//    android:enabled="true"
//    android:exported="true" >
//</service>

/**
 * FloatService.java: 安卓悬浮窗 ----- 2018-6-15 上午11:49:11
 */
public class FloatService extends Service {
    public static FloatService Instance;
    public static WindowManager manager = null;
    public static ContainerView containerView;
    public static boolean mcontainerViewIo = false;
    static WindowManager.LayoutParams mcontainerViewParams;

    /**
     * 显示悬浮窗
     */
    public static void ShowFloat(Context context) {
        if (Instance == null) {
            Intent intent = new Intent(context, FloatService.class);
            context.startService(intent);
            Log.e("NDK","启动服务");
        }
    }

    /**
     * 关闭悬浮窗
     */
    public static void HideFloat(Context context) {
        Intent intent = new Intent(context, FloatService.class);
        FloatService.removecontainerView();
        context.stopService(intent);
        if (Instance != null) {
            Instance.Hide();
        }
    }

    //-----------------------


    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        init();
        ClipboardUtil.init(this);
        SetmcontainerView();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 移除悬浮窗，停止服务
     */
    public void Hide() {
        Instance = null;
        manager = null;
        this.stopSelf();                    // 停止服务
        this.onDestroy();
    }


    //AnimationF animation;

    private void init() {
        if (manager == null) {
            manager = (WindowManager) Instance.getSystemService(WINDOW_SERVICE);
            Log.e("NDK","初始化WINDOW_SERVICE");
        }
    }

    public static void removecontainerView() {
        if (mcontainerViewIo && manager != null) {
            manager.removeView(containerView);
            mcontainerViewIo = false;
        }
    }

    static DisplayMetrics dm = new DisplayMetrics();

    public static void SetmcontainerView() {
        // 从布局文件，生成悬浮窗
        manager.getDefaultDisplay().getRealMetrics(dm);
        containerView = new ContainerView(Instance);
        containerView.setZOrderOnTop(true);
        containerView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        Log.e("NDK-JAVA", "开启绘制窗口");
        // 添加悬浮窗至系统服务
        mcontainerViewParams = WindowManagerLayoutParams.getParams(dm.widthPixels, dm.heightPixels);
        if (manager != null) {
            mcontainerViewIo = true;
            manager.addView(containerView, mcontainerViewParams);
        }
    }
    public static mFloatButton mmFloatButton;//复制粘贴按钮
    public static WindowManager.LayoutParams mFloatButtonParams;
    public static boolean ismFloatButton = false;
    public static void Show_mFloatButton(int x, int y) {
        if (ismFloatButton){
            return;
        }
        if ( mmFloatButton == null && mFloatButtonParams == null){
            mmFloatButton = new mFloatButton(Instance,10);
            mmFloatButton.layoutRoot.measure(0,0);
            Log.e("NDK","mmFloatButton.w"+mmFloatButton.layoutRoot.getMeasuredWidth()+"mmFloatButton.h"+mmFloatButton.layoutRoot.getMeasuredHeight());
            mFloatButtonParams = WindowManagerLayoutParams.getParams();
        }

        if (manager != null && mmFloatButton != null) {
            mFloatButtonParams.x = x-mmFloatButton.layoutRoot.getMeasuredWidth()/2;
            mFloatButtonParams.y = y-mmFloatButton.layoutRoot.getMeasuredHeight()*2;
            manager.addView(mmFloatButton.layoutRoot, mFloatButtonParams);
            Log.e("NDK","Show_mFloatButton");
            ismFloatButton = true;

        }

    }

    public static void remove_mFloatButton(){
        if (ismFloatButton && manager != null && mmFloatButton != null && mFloatButtonParams != null)
        {
            manager.removeView(mmFloatButton.layoutRoot);
            Log.e("NDK","remove_mFloatButton");
            ismFloatButton = false;
        }
    }


    //监听屏幕旋转
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 在这里添加屏幕切换后的操作
        if (mcontainerViewParams != null && containerView != null) {
            manager.getDefaultDisplay().getRealMetrics(dm);
            mcontainerViewParams.width = dm.widthPixels;
            mcontainerViewParams.height = dm.heightPixels;
            manager.updateViewLayout(containerView, mcontainerViewParams);
        }

        Log.e("NDK", "screenWidth=" + dm.widthPixels + "screenHeight=" + dm.heightPixels);
        Log.e("NDK", "屏幕旋转了");
    }
}
