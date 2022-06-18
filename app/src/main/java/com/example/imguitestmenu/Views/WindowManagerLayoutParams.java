package com.example.imguitestmenu.Views;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.imguitestmenu.Services.FloatService;

public class WindowManagerLayoutParams {


    public WindowManagerLayoutParams() {

    }

    public static WindowManager.LayoutParams getAttributes(boolean isWindow) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        if (isWindow) {
            params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
        params.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        params.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶
        params.x = params.y = 0;
        params.width = params.height = isWindow ? WindowManager.LayoutParams.MATCH_PARENT : 0;
//        params.width = params.height =  WindowManager.LayoutParams.MATCH_PARENT ;
        return params;
    }

    public static WindowManager.LayoutParams getParams(int width, int height) {
        // 设置window type
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;    // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        // 设置悬浮窗口长宽数据
        wmParams.width = width;
        wmParams.height = height;
        /*wmParams.width = width;
        wmParams.height = height;*/

        return wmParams;
    }

    public static WindowManager.LayoutParams getFloatDragViewParams() {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;    // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        wmParams.x = 100;
        wmParams.y = 100;
        // 设置悬浮窗口长宽数据
		/*wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;*/
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return wmParams;
    }

    public static WindowManager.LayoutParams getFloatDragViewTwoParams(int y, int x) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;    // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        wmParams.x = y;
        wmParams.y = x;
        // 设置悬浮窗口长宽数据
		/*wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;*/
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return wmParams;
    }


    public static WindowManager.LayoutParams getmMenuViewParams(int x, int y, int wh, int w, int h) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        if (FloatService.Instance.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            wmParams.x = x;
            wmParams.y = y + wh;
        } else {
            wmParams.x = x + wh;
            wmParams.y = y;
        }

        // 设置悬浮窗口长宽数据
		/*wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;*/
        wmParams.width = w;//宽高
        wmParams.height = h;
        return wmParams;
    }

    public static WindowManager.LayoutParams getmListMenuViewParams(int x, int y, int wh) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;        // 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        if (FloatService.Instance.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            wmParams.x = x;
            wmParams.y = y + wh;
        } else {
            wmParams.x = x + wh;
            wmParams.y = y;
        }
        // 设置悬浮窗口长宽数据
//		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.width = wh * 6;//宽高
        wmParams.height = wh * 6;
        return wmParams;
    }

    public static WindowManager.LayoutParams getParams()
    {
        // 设置window type
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wmParams.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

//		wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.format = PixelFormat.RGBA_8888;   			// 设置图片格式，效果为背景透明
        wmParams.flags  = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;		// 调整悬浮窗显示的停靠位置为左侧置顶

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity


        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        /*wmParams.width = width;
        wmParams.height = height;*/

        return wmParams;
    }


}
