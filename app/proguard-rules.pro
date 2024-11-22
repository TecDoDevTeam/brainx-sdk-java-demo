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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5 # 指定代码的压缩级别
-dontusemixedcaseclassnames # 是否使用大小写混合
-dontpreverify # 混淆时是否做预校验
#-dontshrink
-verbose # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # 混淆时所采用的算法# 保持子类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
##添加以下混淆规则可解决
##保留继承于BaseViewHolder的内部类不被混淆
#-keep class com.chad.library.adapter.base.viewholder.BaseDataBindingHolder {*;}
#-keepclassmembers class * extends com.chad.library.adapter.base.viewholder.BaseDataBindingHolder {
#public void *(android.view.View);
#}
#-keepclassmembers class com.chad.library.adapter.base.viewholder.BaseDataBindingHolder {
#public void *(android.view.View);
#}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {public void *(android.view.View);
}

-keep class com.td.** { *; }
-keepclassmembers class com.td.** {
    @com.td.* public *;
}