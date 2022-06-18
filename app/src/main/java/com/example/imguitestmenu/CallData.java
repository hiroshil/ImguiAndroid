package com.example.imguitestmenu;
public class CallData {
    //窗口id
    public int ID;
    //窗口名
    public String WinName;
    //窗口坐标宽高数据
    public float X;
    public float Y;
    public float X1;
    public float Y1;
    //是否处于活动状态
    public boolean Action;

    public CallData(){
        ID=0;
        WinName="";
        X=0;
        Y=0;
        X1=0;
        Y1=0;
        Action=false;

    }

}
