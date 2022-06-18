package com.example.imguitestmenu.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.imguitestmenu.App;
import com.example.imguitestmenu.GLES3JNILib;
import com.example.imguitestmenu.Services.FloatService;

import java.util.HashMap;
import java.util.Map;

public class EventClass {
    private static native void init();
    private static Context mContext;
    private static Bundle bundle;

    private static Toast toast;
   // private static InputMethodManager manager;
    public static WindowManager.LayoutParams mtouchParams;
    public static Map<Integer, View>ViewList;
    public static InputMethodManager manager;

    private static float x,y;

    //初始化
    public static void Init(Context con){
        Log.e("NDK","开始初始化");
        init();
        Log.e("NDK","初始化结束");
        mContext=con;
        bundle=new Bundle();
        ViewList=new HashMap<>();//创建view表
        toast = new Toast(mContext);//创建Toast
        //mtouchParams = WindowManagerLayoutParams.getAttributes(false);
        mtouchParams=ContainerView.mtouchParams;

    }

    //触屏事件
    public static boolean onTouchEvent(MotionEvent event){
        x = event.getRawX();
        y = event.getRawY();
        GLES3JNILib.MotionEventClick(event.getAction(),event.getRawX(),event.getRawY());
        return false;
    }
    //点击事件
    public static boolean onKeyEvent(KeyEvent event){
        GLES3JNILib.sendKeyEvent_JNI(event.getAction(),event.getKeyCode());
        return false;
    }

    //输入事件
    public static void onInputchar(CharSequence text){
        GLES3JNILib.inputcharOnJNI(text.toString());
    }

    //Toast相关
    public static void mIO(String psmsg,int pos, int io,int id){
        int[] ioi = new int[2];
        ioi[0] = pos;
        ioi[1] = io;
        if (io>100){
            ioi[1] -= 100;
            FloatService.containerView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
        }
        Message message = handlerm.obtainMessage();

//        Log.e("NDK","ioset"+"pos="+pos+"io="+io);
        bundle.putIntArray(psmsg,ioi);
        bundle.putInt("psid",id);
        message.setData(bundle);//通过setData将字符串发送
        handlerm.sendMessage(message);

    }

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            toast = Toast.makeText(mContext, msg.getData().getString("JNI_msg") , Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    public static void mShow(String msg){
        Message message = handler.obtainMessage();
        bundle.putString("JNI_msg",msg);
        message.setData(bundle);
        handler.sendMessage(message);
    }


    @SuppressLint("HandlerLeak")
    public static Handler handlerm = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int[] io = new int[2];
            if ((io = msg.getData().getIntArray("psio")) != null) {
                int id=msg.getData().getInt("psid");
                ioset(io,id);
            }
        }
    };
    //是否显示输入法
    public static void ioset(int[] io,int id){
        switch (io[0]) {
            case 2: {
                if ( io[1] == 1 ){
                    setmyFocusable(true,id);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                    openInput(id);
                }else {
                    closeInput(id);

                    setmyFocusable(false,id);
                }
            }
            break;
            case 5: {
                if ( io[1] == 1 ){
                    FloatService.containerView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                    );
                }else {
                    FloatService.containerView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                }
            }
            break;
            case 3: {
                isLongTouch((int)x,(int)y,id);
            }
            break;
        }
    }
    private static int rerun=0;//如果超过三次失败就不调用 显示输入法了
    private static boolean Inputio = false;
    private static View lookView;

    //如果输入法关，则开启输入法
    public static boolean openInput(int id){
        Log.e("NDK", "唤醒输入法ID:"+id);
        //manager = (InputMethodManager) ViewList.get(id).v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // return showSoftInput();
        if (!Inputio){
            lookView=ViewList.get(id);
            setmyFocusable(true,id);
            Log.e("NDK", "唤醒输入法");
            //        获取 接受焦点的资格
            lookView.setFocusable(true);

            //        获取 焦点可以响应点触的资格
            lookView.setFocusableInTouchMode(true);
            //        请求焦点
            boolean ck = lookView.requestFocus();
            showSoftInput();//增加成功率
            Log.e("NDK","requestFocus:"+ck);
            //        弹出键盘
            //重新创建一次 否则有时候输入法会重复弹出
            manager=(InputMethodManager) lookView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            ck = manager.showSoftInput(lookView, InputMethodManager.SHOW_FORCED);
//            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            Log.e("NDK","showSoftInput:"+ck);
            if (manager.isActive()) {
                Inputio = true;
                Log.e("NDK", "唤醒输入法成功");
                rerun=0;
                lookView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                return Inputio;
            } else {
                GLES3JNILib.finishComposingText_JNI();
                Log.e("NDK", "唤醒输入法失败"+rerun);

                return Inputio;
            }
        }
        return Inputio;
    }


    /**
     * 如果输入法开，则关闭输入法
     */
    public static boolean closeInput(int id){
        if (Inputio){
            Log.e("NDK", "关闭输入法");
            if (lookView==null){
                Inputio=false;
                return false;
            }
            //        取消焦点
            lookView.setFocusable(false);
            //        取消 焦点可以响应点触的资格
            lookView.setFocusableInTouchMode(false);
            //       清除焦点
            lookView.clearFocus();
            hideSoftInput();
            //       隐藏悬浮窗
            //重新创建一次 否则有时候输入法会重复弹出
            //0ContainerView.manager=(InputMethodManager) ViewList.get(id).getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(lookView.getWindowToken(), 0);

            if (manager.isActive()) {
                Inputio = false;
                setmyFocusable(false,id);
                lookView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                Log.e("NDK", "关闭输入法成功");
                lookView=null;
            } else {
                Log.e("NDK", "关闭输入法失败");
            }
            return Inputio;
        }
        return Inputio;
    }
    //是否长按
    public static void isLongTouch(int x,int y,int id){
        ViewList.get(id).performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        FloatService.Show_mFloatButton((int)x,(int)y);
        ViewList.get(id).performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }

    //判断输入法是否显示在屏幕上面
    private static boolean isSoftShowing(int id) {
        Log.e("NDK","被调用");
        if (ViewList.get(id)==null)return false;
        //获取当屏幕内容的高度
        int screenHeight = ViewList.get(id).getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        ViewList.get(id).getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        // Log.i(TAG,"screenHigh: " + screenHeight + " rectViewBom : " + rect.bottom );
        return screenHeight * 2 / 3 > rect.bottom;
    }

    //貌似是是否设置界面全屏
    public static void setmyFocusable(boolean bl,int id){
        if (bl){
            mtouchParams.flags  = WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }else {
            mtouchParams.flags  =WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }
        FloatService.manager.updateViewLayout(lookView, mtouchParams);
    }


    //显示输入法
    public static boolean showSoftInput() {
        if (lookView==null)return false;
        InputMethodManager inputMethodManager = (InputMethodManager) App.GetApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.showSoftInput(lookView, 0);
    }
    //隐藏输入法
    public static boolean hideSoftInput() {
        if (lookView==null)return false;
        InputMethodManager inputMethodManager = (InputMethodManager) App.GetApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            Log.d("hickey", "hideSoftInput:" + "hideSoftInputFromWindow");
            return  inputMethodManager.hideSoftInputFromWindow(lookView.getWindowToken(), 0);
        }
        return false;
    }
}
