package com.example.imguitestmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.imguitestmenu.Services.FloatService;
import com.example.imguitestmenu.Utils.ErrorActivity;
import com.example.imguitestmenu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'imguitestmenu' library on application startup.
    /*----------------------------------------------*/
    /*----------------------------------------------*/
    /*---------------请编译成release版本--------------*/
    /*---------------否则输入文本就会报错---------------*/
    /*----------------------------------------------*/

    //private ActivityMainBinding binding;
    public static boolean IsRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLES3JNILib.setFilePath(getFilesDir().toString());
       // binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
        // Example of a call to a native method
        //TextView tv = binding.sampleText;
        //tv.setText(stringFromJNI());
        Button bt=findViewById(R.id.my_button1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(MainActivity.this, ErrorActivity.class);
                //startActivity(intent);
                if (!IsRun) {
                    if (!FloatService.mcontainerViewIo) {
                        Log.e("NDK","准备开启悬浮窗");
                        FloatService.ShowFloat(App.GetApp());
                        moveTaskToBack(true);//返回桌面
                    }

                }
            }
        });
    }

    /**
     * A native method that is implemented by the 'imguitestmenu' native library,
     * which is packaged with this application.
     */

}