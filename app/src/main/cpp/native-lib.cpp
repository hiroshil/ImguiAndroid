#include <jni.h>
#include <string>
#include <stdio.h>



#include <sys/socket.h>
#include <sys/types.h>
#include <time.h>
#include <errno.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <thread>
#include "ImGui/imgui.h"
#include "ImGui/imgui_impl_android.h"
#include "ImGui/imgui_impl_opengl3.h"
#include <android/log.h>
#include <android/asset_manager.h>
#include <EGL/egl.h>
#include <GLES3/gl3.h>
#include "ImguiEGL.h"
#include <random>
#include "android/native_window_jni.h"


string            SetFilePath;
ImguiAndroidInput input;
ImguiEGL       *EGL   = nullptr;
jobject Box(JNIEnv *env,int Wid,char* Winame,float f[],bool at);

//设置文件路径
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_setFilePath(JNIEnv *env, jclass clazz, jstring ph) {
    // TODO: implement setFilePath()
    SetFilePath=env->GetStringUTFChars(ph, JNI_FALSE);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_surfaceDestroyed(JNIEnv *env, jclass clazz) {
    // TODO: implement surfaceDestroyed()
    if (EGL != nullptr) {
        EGL->onSurfaceDestroy();
    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_surfaceChange(JNIEnv *env, jclass clazz, jint width,
                                                         jint high) {
    // TODO: implement surfaceChange()
    if (EGL != nullptr) {
        EGL->onSurfaceChange(width, high);
        // mData->TouchIsChange = true;
    }
}
//创建 初始化
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_surfaceCreate(JNIEnv *env, jclass clazz, jobject surface,
                                                         jint width, jint high) {
    // TODO: implement surfaceCreate()
    if (EGL == nullptr) {
        EGL = new ImguiEGL;
    }
    EGL->onSurfaceCreate(env, surface, width, high);
    //imgui->setDrawData(mData);
    EGL->setSaveSettingsdir(SetFilePath);
    EGL->setinput(&input);
}
//数据容器
//jobject dataclass;
//数据类
jclass cal;

float f[4]={0,0,0,0};
//发送窗口大小
float winData[4];
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_GetImguiwinsize(JNIEnv *env, jclass clazz) {
    // TODO: implement GetImguiwinsize()
    jobjectArray obj;

    cal=env->FindClass("com/example/imguitestmenu/CallData");//有时候会获取失败
    //jfloatArray newFloatArray = env->NewFloatArray(4);
    if (EGL != nullptr) {
            if (EGL->WinList.Size!=0){
                if(cal==NULL){
                    return NULL;
                }
                obj=env->NewObjectArray(EGL->WinList.Size,cal,NULL);
                //LOGE("窗口数据 W:%f H:%f",EGL->g_window->Size.x,EGL->g_window->Size.y);
                int i=0;
                for (ImGuiWindow *win:EGL->WinList) {
                    f[0] =win->Pos.x;
                    f[1] =win->Pos.y;
                    f[2] =win->Size.x;
                    f[3] =win->Size.y;
                    //g_window->Hidden
                   // LOGE("id:%d 窗口是否隐藏:%s",win->ID,win->Hidden? "是":"否");
                    LOGE("id:%d 窗口是否隐藏1:%s",win->ID,win->Active? "是":"否");
                    //设置数据对象
                    env->SetObjectArrayElement(obj,i,Box(env,win->ID,win->Name,f,win->Active));
                    i++;
                }

                return obj;

            }


    }
    //env->ReleaseFloatArrayElements(newFloatArray, winData, JNI_COMMIT);
    return NULL;
}
//数据包装
jobject Box(JNIEnv *env,int Wid,char* Winame,float f[],bool at){
    //LOGE("包装数据 W:%f H:%f",f[2],f[3]);
   // if (cal==NULL){
        //jclass cl=env->FindClass("com/example/imguitestmenu/CallData");//找到数据类
       // jclass cal=env->FindClass("com/example/imguitestmenu/CallData");//找到数据类
   // }

   // if (cal==NULL)return NULL;
    jfieldID id,name,X,Y,X1,Y1,Act;
    id=env->GetFieldID(cal,"ID","I");
    name=env->GetFieldID(cal,"WinName","Ljava/lang/String;");
    X=env->GetFieldID(cal,"X","F");
    Y=env->GetFieldID(cal,"Y","F");
    X1=env->GetFieldID(cal,"X1","F");
    Y1=env->GetFieldID(cal,"Y1","F");
    Act=env->GetFieldID(cal,"Action","Z");
    jobject daclass=env->AllocObject(cal);
    env->SetIntField(daclass,id,Wid);
    env->SetObjectField(daclass, name, env->NewStringUTF(Winame));
    env->SetFloatField(daclass,X,f[0]);
    env->SetFloatField(daclass,Y,f[1]);
    env->SetFloatField(daclass,X1,f[2]);
    env->SetFloatField(daclass,Y1,f[3]);
    env->SetBooleanField(daclass,Act,at);
    return daclass;
}

//窗口移动事件
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_MotionEventClick(JNIEnv *env, jclass clazz,
                                                            jint action, jfloat pos_x,
                                                            jfloat pos_y) {
    // TODO: implement MotionEventClick()
    input.InputTouchEvent(action, pos_x, pos_y);
}
//字符输入
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_inputcharOnJNI(JNIEnv *env, jclass clazz, jstring msg) {
    // TODO: implement inputcharOnJNI()
    const char *tmp1 = env->GetStringUTFChars(msg, NULL);
    input.addUTF8(tmp1);
    env->ReleaseStringUTFChars(msg, tmp1);
}
//按键消息事件
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_sendKeyEvent_1JNI(JNIEnv *env, jclass clazz, jint action,
                                                             jint code) {
    // TODO: implement sendKeyEvent_JNI()
    input.InputKey(action, code);
}
//退出输入法
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_finishComposingText_1JNI(JNIEnv *env, jclass clazz) {
    // TODO: implement finishComposingText_JNI()
    input.Inputio = false;
}
//全选
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_Select_1all(JNIEnv *env, jclass clazz) {
    // TODO: implement Select_all()
    return input.JNI_SelectAll();
}
//剪切
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_Cut(JNIEnv *env, jclass clazz) {
    // TODO: implement Cut()
    return env->NewStringUTF(input.JNI_Cut().c_str());
}
//粘贴
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_Paste(JNIEnv *env, jclass clazz, jstring data) {
    // TODO: implement Paste()
    const char *tmpbuf = env->GetStringUTFChars(data, NULL);
    input.JNI_Paste(tmpbuf);
    env->ReleaseStringUTFChars(data, tmpbuf);
}
//复制
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_imguitestmenu_GLES3JNILib_Copy(JNIEnv *env, jclass clazz) {
    // TODO: implement Copy()
    return env->NewStringUTF(input.JNI_Copy().c_str());
}

//初始化输入事件
extern "C"
JNIEXPORT void JNICALL
Java_com_example_imguitestmenu_Views_EventClass_init(JNIEnv *env, jclass clazz) {
    // TODO: implement init()
    LOGE("开始初始化2");
    input.funMshowinit(clazz, env);
    LOGE("结束初始化2");
}