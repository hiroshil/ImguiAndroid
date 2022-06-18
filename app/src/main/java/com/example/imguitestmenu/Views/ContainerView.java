package com.example.imguitestmenu.Views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.example.imguitestmenu.App;
import com.example.imguitestmenu.CallData;
import com.example.imguitestmenu.GLES3JNILib;
import com.example.imguitestmenu.Services.FloatService;
import com.example.imguitestmenu.Utils.Orientation;

public class ContainerView extends SurfaceView implements SurfaceHolder.Callback {

    private Orientation orientation;
    //TouchView mtouch;
    private static Context con;
    //int action;
    public static WindowManager.LayoutParams mtouchParams;
    //public static InputMethodManager manager;

    public ContainerView(Context context) {
        this(context, null);
        getHolder().addCallback(this);
        mtouchParams = WindowManagerLayoutParams.getAttributes(false);
        con=context;
        EventClass.Init(context);
        orientation = new Orientation(context);
        //manager = (InputMethodManager) App.GetApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        updateTouchWinSize();

    }

    public ContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    //更新窗口信息
    public void updateTouchWinSize() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //有bug联系我，有时间会修复的 1486609722 记得备注原因
                    //float[] rect = GLES3JNILib.GetImguiwinsize();
                    CallData[] cd=GLES3JNILib.GetImguiwinsize();
                   // Log.e("NDK-java","收到宽高: W:"+cd[0].X1+" H:"+cd[0].Y1);

                    for (int i=0;i<cd.length;i++){
                        mtouchParams.x = (int) cd[i].X;
                        mtouchParams.y = (int) cd[i].Y;
                        mtouchParams.width = (int) cd[i].X1;
                        mtouchParams.height = (int) cd[i].Y1;

                        if (EventClass.ViewList.isEmpty()){
                            //过滤debug窗口
                            if (!cd[i].WinName.equals("Debug##Default")){
                                TouchView mtouch = new TouchView(con);

                               // InputMethodManager manager = (InputMethodManager) mtouch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               // vm.manager=manager;
                                EventClass.ViewList.put(cd[i].ID,mtouch);
                                FloatService.manager.addView(mtouch, mtouchParams);
                            }else {
                                //Log.e("NDK-java","检测到Debug窗口");
                            }

                        }else if (!EventClass.ViewList.containsKey(cd[i].ID)){
                            if (!cd[i].WinName.equals("Debug##Default")){
                                TouchView mtouch = new TouchView(con);
                                InputMethodManager manager = (InputMethodManager) mtouch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               // vm.manager=manager;
                                EventClass.ViewList.put(cd[i].ID,mtouch);
                                FloatService.manager.addView(mtouch, mtouchParams);
                            }else {
                                //Log.e("NDK-java","检测到Debug窗口");
                            }

                        }else {
                            //Log.e("NDK-java","悬浮窗宽高: W:"+EventClass.ViewList.get(cd[i].ID).getWidth()+" H:"+EventClass.ViewList.get(cd[i].ID).getHeight());
                           // Log.e("NDK-java", "窗口数据: 窗口名字:"+cd[i].WinName+ " X:"+cd[i].X+" Y"+cd[i].Y+" X1"+cd[i].X1+" Y1"+cd[i].Y1);
                            if (!cd[i].Action){//如果不处于活动状态说明窗口隐藏了
                                mtouchParams.width=0;
                                mtouchParams.height=0;
                            }

                            FloatService.manager.updateViewLayout(EventClass.ViewList.get(cd[i].ID), mtouchParams);
                        }

                    }
                        //循环判断获取的列表和本地列表key是否一致
                    for (int key : EventClass.ViewList.keySet()){
                        boolean ba=false;
                        for (int i=0;i<cd.length;i++){

                            if (key==cd[i].ID){
                                ba=true;
                            }
                        }
                        //如果不等于就说明有个窗口被删掉了，就从map和窗口里面移除
                        if (!ba){
                            FloatService.manager.removeView(EventClass.ViewList.get(key));
                            EventClass.ViewList.remove(key);
                            Log.e("NDK-java","删除窗口");
                        }
                    }


                    //FloatService.manager.updateViewLayout(mtouch, mtouchParams);
                } catch (Exception e) {
                    Log.e("NDK-java", "跳出");
                }
                handler.postDelayed(this, 20);
            }
        }, 20);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        GLES3JNILib.surfaceCreate(holder.getSurface(), this.getWidth(), this.getHeight());
        Log.e("NDK-java", "surfaceCreated");
    }



    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        GLES3JNILib.surfaceChange(width, height);
        Log.e("NDK-java", "surfaceChanged");
    }



    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        GLES3JNILib.surfaceDestroyed();
        Log.e("NDK-java", "surfaceDestroyed");
    }


}
