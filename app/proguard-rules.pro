# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#异步Http请求android-async-http-1.4.3.jar配置
#-keep class com.loopj.android.http.** { *; }

#不混淆org.apache.http.legacy.jar（android 6.0没有该jar包，需要手动导入，避免混淆）
#-dontwarn android.net.compatibility.**
#-dontwarn android.net.**
#-dontwarn com.android.internal.http.multipart.**
#-dontwarn org.apache.commons.**
#-dontwarn org.apache.http.**
#-keep class android.net.compatibility.**{*;}
#-keep class android.net.**{*;}
#-keep class com.android.internal.http.multipart.**{*;}
#-keep class org.apache.commons.**{*;}
#-keep class org.apache.http.**{*;}

# 自定义控件及组件不要混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application {*;}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View {*;}

# 数据模型不要混淆
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {*;}

# adapter不能混淆
-keep public class * extends android.widget.Adapter {*;}
-keep public class com.ybkj.videoaccess.mvp.view.adapter.** {*;}
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.ybkj.videoaccess.util.http.** { *; }
-keep class com.ybkj.videoaccess.util.security.** { *; }
-keep class com.ybkj.videoaccess.util.rxbus.** { *; }
-keep class com.ybkj.videoaccess.mvp.data.bean.** { *; }
-keep class com.ybkj.videoaccess.websocket.** { *; }

-keep class com.ybkj.videoaccess.mvp.data.** { *; }
-keep class com.ybkj.videoaccess.mvp.view.fragment.** { *; }

#java.lang.invoke相关类异常混淆
-dontwarn java.lang.invoke.*

# Glide(removes such information by default, so configure it to keep all of it.)
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.ybkj.videoaccess.util.MyGlideModules

# ButterKnife混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# ProGuard configurations for Bugtags
-keepattributes LineNumberTable,SourceFile
-keep class com.bugtags.library.** {*;}
-dontwarn com.bugtags.library.**
-keep class io.bugtags.** {*;}
-dontwarn io.bugtags.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
# End Bugtags


# 图片选择器GalleryFinal混淆
-keep class cn.finalteam.galleryfinal.widget.*{*;}
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}

# 环信即时通信混淆
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

-keep class com.easemob.* {*;}
-keep class com.easemob.chat.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils,注意前面的包名，
#不要SmileUtils复制到自己的项目下keep的时候还是写的demo里的包名
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

#忽略警告
-ignorewarnings

# 网络框架---------Retrofit+Rxjava+okHttp
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature-keepattributes
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
# Gson
#-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# OkHttp持久化登录
-dontwarn com.franmontiel.persistentcookiejar.**
-keep class com.franmontiel.persistentcookiejar.**
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#EventBus混淆配置
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# 极光推送混淆
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
