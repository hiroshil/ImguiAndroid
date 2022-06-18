# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes Signature #范型
#native方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class  com.example.imguitestmenu.Views.EventClass {
  public static boolean openInput(int);
  public static boolean closeInput(int);
  public static void mIO(java.lang.String,int,int,int);
  public static void mShow(java.lang.String);
  public static void isLongTouch(int ,int ,int);
 }

 -keep class com.example.imguitestmenu.CallData{ *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile