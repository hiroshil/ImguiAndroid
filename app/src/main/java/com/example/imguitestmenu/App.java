package com.example.imguitestmenu;

import android.app.Application;
import com.example.imguitestmenu.Utils.CrashHandler;
//应用入口类
public class App extends Application {
    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        //错误处理
        //CrashHandler crashHandler = CrashHandler.getInstance();
       // crashHandler.init(getApplicationContext());
    }
    public static App GetApp(){
        return sApp;
    }
}
