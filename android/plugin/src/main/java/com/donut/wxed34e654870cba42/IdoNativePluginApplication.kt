package com.donut.wxed34e654870cba42

import MainProcessTask
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.util.Log
import com.getui.gs.sdk.GsManager
import com.getui.gtc.base.GtcProvider
import com.getui.gtc.base.util.OnForegroundChangedListener
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginApplicationInterface

class IdoNativePluginApplication : NativePluginApplicationInterface {
    private val TAG = "application"
    private val mainProcessTask: MainProcessTask = MainProcessTask(Intent())
    override fun getPluginID(): String {
        android.util.Log.d(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }

    override fun onCreate(application: Application) {
        GtcProvider.setContext(application)
        android.util.Log.e(TAG, "oncreate!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            android.util.Log.e(TAG, "process : " + Application.getProcessName())
        }


        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
//                android.util.Log.d(TAG, "onActivityCreated ${p0.javaClass.simpleName}")
            }

            override fun onActivityStarted(p0: Activity) {
//                android.util.Log.d(TAG, "onActivityStarted")
            }

            override fun onActivityResumed(activity: Activity) {
                mainProcessTask.setIntent(Intent("onActivityResumed"))
                mainProcessTask.execAsync()
            }

            override fun onActivityPaused(p0: Activity) {
                mainProcessTask.setIntent(Intent("onActivityPaused"))
                mainProcessTask.execAsync()
            }

            override fun onActivityStopped(p0: Activity) {
                mainProcessTask.setIntent(Intent("onActivityStopped"))
                mainProcessTask.execAsync()
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
//                android.util.Log.d(TAG, "onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(p0: Activity) {
//                android.util.Log.d(TAG, "onActivityDestroyed  ${p0.javaClass.simpleName}")
            }
        })
    }

}