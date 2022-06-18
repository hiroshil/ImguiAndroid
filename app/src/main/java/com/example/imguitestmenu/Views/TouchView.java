package com.example.imguitestmenu.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.imguitestmenu.App;
import com.example.imguitestmenu.GLES3JNILib;
import com.example.imguitestmenu.Services.FloatService;

public class TouchView extends View {

    public static View mtouch;
    public static Context mContext;
   // static InputMethodManager manager;
    //public MyInputConnection inputConnection;

    public TouchView(Context context) {
        super(context);
        mContext = context;
        mtouch = this;
        //inputConnection=

    }

    public void initView() {

       // FloatService.manager.addView(mtouch, mtouchParams);

    }

    int action;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //x = event.getRawX();
        //y = event.getRawY();
        EventClass.onTouchEvent(event);
        //GLES3JNILib.MotionEventClick(event.getAction(), x = event.getRawX(), y = event.getRawY());
        return false;
    }




    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    //方法，需要返回一个InputConnect对象，这个是和输入法输入内容的桥梁。
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        // outAttrs就是我们需要设置的输入法的各种类型最重要的就是:
        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_FULLSCREEN |EditorInfo.IME_ACTION_DONE;
        outAttrs.inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE |InputType.TYPE_MASK_FLAGS ;
        return new MyInputConnection(this,true);
    }










    /**
     * 重写BaseInputConnection获取输入法按键KEY以及输入内容
     */
    public  class MyInputConnection extends BaseInputConnection {
        //一般我们都是些一个BaseInputConnection的子类，而BaseInputConnection是实现InputConnection接口的。

        //需要注意的就是几个方法注意重写。
        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }


        /**
         * @param text 获取按键输入内容，中文输入模式不可用
         * @param newCursorPosition 输入光标偏移，通常为1，如果为0无输入
         * @return
         */
        @Override
        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            //note:获取到输入的字符
            Log.e("NDK", "setComposingText:" + text + "\t" + newCursorPosition);

            // postInvalidate();
            return true;
        }


        /**
         * @param text 选择输入法候选栏输入字符
         * @param newCursorPosition 通常为1
         * @return
         */
        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            //note:获取到输入的字符
            Log.e("NDK", "commitText:" + text + "\t" + newCursorPosition);
            EventClass.onInputchar(text);
            // postInvalidate();
            return true;
        }


        /**
         * @param event 获取按键KeyEvent
         * @return
         */
        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            /** 当手指离开的按键的时候 */
            EventClass.onKeyEvent(event);
            //GLES3JNILib.sendKeyEvent_JNI(event.getAction(),event.getKeyCode());

            return true;
        }



        //当然删除的时候也会触发
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            Log.e("NDK", "deleteSurroundingText " + "beforeLength=" + beforeLength + " afterLength=" + afterLength);
            //处理有些输入法会只执行deleteSurroundingText 而不执行sendKeyEvent
            if (beforeLength == 1 || afterLength == 0 || beforeLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN ,KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return true;
        }

        /**
         * @return 手动关闭输入法，结束输入
         */
        @Override
        public boolean finishComposingText() {
            //结束组合文本输入的时候，这个方法基本上会出现在切换输入法类型，点击回车（完成、搜索、发送、下一步）点击输入法右上角隐藏按钮会触发。
            Log.e("NDK", "finishComposingText");
            GLES3JNILib.finishComposingText_JNI();
            return true;
        }
    }




}
