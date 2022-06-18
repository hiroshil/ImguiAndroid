package com.example.imguitestmenu.Utils;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.OrientationEventListener;

public class Orientation {
    //传感器监听，判断手机当前方向
    public final String TAG = "NDK";
    public CameraOrientationListener orientationListener;
    public static int mOrientation = 0;
    public static long aLong = 0;
    public OrientationEventListener mOrEventListener;
    public Context context;

    public Orientation(Context context_) {
        context = context_;

        startOrientationChangeListener();
        Thread syncTask1 = new Thread() {
            @Override
            public void run() {
                // 执行耗时操作
                funOrientationChangeListenerloop();
            }
        };
        syncTask1.start();
    }


    void funOrientationChangeListenerloop() {
        long bLong = 0;
        while (true) {
            bLong = aLong;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (aLong == bLong) {
                bLong = 0;
                aLong = 0;
                if (mOrEventListener != null) {
                    mOrEventListener.disable();
                }
                startOrientationChangeListener();
            }
        }
    }



    private void startOrientationChangeListener() {
        mOrEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int rotation) {
                ++aLong;
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    rotation = 0;
                } else if ((rotation > 45) && (rotation <= 135)) {
                    rotation = 90;
                } else if ((rotation > 135) && (rotation <= 225)) {
                    rotation = 180;
                } else if ((rotation > 225) && (rotation <= 315)) {
                    rotation = 270;
                } else {
                    rotation = 0;
                }

                if (rotation != mOrientation) {
                    mOrientation = rotation;
                    if (mOrientation == 90 || mOrientation == 270) {
                        //myJNI.setmOrientation(mOrientation);
                    }
                    Log.e(TAG, "当前屏幕手持方向:" + mOrientation + "°");
                }
            }
        };
        mOrEventListener.enable();
    }

    public static class CameraOrientationListener extends OrientationEventListener {

        private int mCurrentNormalizedOrientation;
        private int mRememberedNormalOrientation;

        public CameraOrientationListener(Context context) {
            super(context, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onOrientationChanged(final int orientation) {
//            Log.e(TAG, "当前屏幕手持角度:" + orientation + "°");
            if (orientation != ORIENTATION_UNKNOWN) {
                mCurrentNormalizedOrientation = normalize(orientation);
            }

        }

        private int normalize(int degrees) {
            if (degrees > 315 || degrees <= 45) {
                return 0;
            }

            if (degrees > 45 && degrees <= 135) {
                return 90;
            }

            if (degrees > 135 && degrees <= 225) {
                return 180;
            }

            if (degrees > 225 && degrees <= 315) {
                return 270;
            }

            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
        }

        /**
         * 记录方向
         */
        public void rememberOrientation() {
            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
        }

        /**
         * 获取当前方向
         *
         * @return
         */
        public int getRememberedNormalOrientation() {
            return mRememberedNormalOrientation;
        }
    }

}
