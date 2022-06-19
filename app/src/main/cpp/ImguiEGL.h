//
// Created by admin on 2022/6/10.
//

#ifndef IMGUIMENU_IMGUIEGL_H
#define IMGUIMENU_IMGUIEGL_H

#include "pch.h"

class ImguiEGL { ;
    std::condition_variable cond;
    std::mutex              Threadlk;
   // int                     FPS;
   // float                   FPStime;
    FILE                    *Fsave         = nullptr;
    FILE                    *numSave       = nullptr;
    FILE                    *ColorSave     = nullptr;
    FILE                    *KeySave       = nullptr;
    //EGL
    EGLDisplay              mEglDisplay;
    EGLSurface              mEglSurface;
    EGLConfig               mEglConfig;
    EGLContext              mEglContext;
    EGLNativeWindowType     SurfaceWin;
    std::thread             *SurfaceThread = nullptr;
    ImguiAndroidInput       *input;
    int                     FPS=90;
    //初始化Egl函数
    int initEgl();

    //绘制函数、线程
    void clearBuffers();

    int swapBuffers();

    bool ThreadIo;

    void EglThread();

    //菜单相关
    void Menu1();
    void Dialog(int type);
    void MainView();

    bool  isStyle= false;//弹窗状态

    //Imgui
    int initImgui();

    void imguiMainWinStart();

    void imguiMainWinEnd();


    //是否绘制菜单
    bool login = false;

    //是否登錄
    bool isLogin = false;


   // int      pos = 0;
    ImFont   *imFont;
    string           SaveDir;
public:
    ImGuiIO      *io;
    ImGuiStyle   *style;
    ImGuiWindow  *g_window          = nullptr;
    ImGuiContext *g                 = nullptr;
    int          surfaceWidth       = 0;
    int          surfaceHigh        = 0;
    int          surfaceWidthHalf   = 0;
    int          surfaceHighHalf    = 0;
    int          StatusBarHeight    = 0;
    ImVector<ImGuiWindow*>       WinList;

    ImguiEGL();

    //Surface生命周期
    void onSurfaceCreate(JNIEnv *env, jobject surface, int SurfaceWidth, int SurfaceHigh);

    bool isChage = false;

    void onSurfaceChange(int surfaceWidth, int SurfaceHigh);

    bool isDestroy = false;

    void onSurfaceDestroy();
    void ShowStyleEditor(ImGuiStyle* ref);

    void setSaveSettingsdir(string &dir);

    void setinput(ImguiAndroidInput *input_);

    ~ImguiEGL() {
    }

    void ListItemPage_4();
};
#endif