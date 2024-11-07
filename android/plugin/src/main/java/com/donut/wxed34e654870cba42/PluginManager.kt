package com.donut.wxed34e654870cba42

import MainProcessTask
import android.app.Activity
import android.content.Intent
import android.util.Log
import com.getui.gs.ias.core.GsConfig
import com.getui.gs.sdk.GsManager
import com.tencent.luggage.wxa.SaaA.plugin.AsyncJsApi
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginBase
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginInterface
import com.tencent.luggage.wxa.SaaA.plugin.SyncJsApi
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class PluginManager : NativePluginBase(), NativePluginInterface {
    private val TAG = "PluginManager"
    private val mainProcessTask: MainProcessTask = MainProcessTask(Intent())

    init {
        mainProcessTask.setClientCallback { intent: Intent ->
            Log.d(TAG, "response ${intent.action}")
            val map = HashMap<String, Any>()
            when (intent.action) {
                "gtcId" -> {
                    map["method"] = "gtcId"
                    map["param"] = intent.getStringExtra("gtcId")!!
                }
            }
            if (map["method"] != null) {
                this.sendMiniPluginEvent(map)
            }
        }
    }

    override fun getPluginID(): String {
        android.util.Log.e(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }


    @SyncJsApi(methodName = "init")
    fun init(data: JSONObject, activity: Activity) {
        val intent = Intent("init")
        intent.putExtra("param", data.toString())
        this.mainProcessTask.setIntent(intent)
        this.mainProcessTask.execAsync()
    }

    @SyncJsApi(methodName = "setDebugEnable")
    fun setDebugEnable(data: JSONObject?, activity: Activity) {
        try {
            val debugEnable = if (data?.has("debugEnable") == true) data.get("debugEnable") as Boolean else false
            Log.d(TAG, "setDebugEnable: ${debugEnable}")
            val intent = Intent("setDebugEnable")
            intent.putExtra("param", debugEnable)
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    @SyncJsApi(methodName = "getGtcId")
    fun getGtcId(data: JSONObject?, activity: Activity) {
        try {
            val intent = Intent("getGtcId")
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    @SyncJsApi(methodName = "onEvent")
    fun onEvent(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("onEvent")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    @SyncJsApi(methodName = "onBeginEvent")
    fun onBeginEvent(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("onBeginEvent")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    /**
     * 计时统计结束
     *  eventId：自定义事件 Id ，用于标识事件的唯一
     *  map: 自定义属性，用于扩展统计需求
     */

    @SyncJsApi(methodName = "onEndEvent")
    fun onEndEvent(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("onEndEvent")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 用户属性设置
     *  map: 自定义用户属性，用于扩展统计需求
     */


    @SyncJsApi(methodName = "setProfile")
    fun setProfile(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("setProfile")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    /**
     * 设置计数事件上传频率
     * timeMillis：设置的eventUploadInterval值，单位毫秒。
     * 默认值为10秒
     */
    @SyncJsApi(methodName = "setEventUploadInterval")
    fun setEventUploadInterval(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("setEventUploadInterval")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 设置计数事件事件强制上传条数
     * size：设置计数事件的强制上传条数eventForceUploadSize
     * 默认数量为30条；
     */
    @SyncJsApi(methodName = "setEventForceUploadSize")
    fun setEventForceUploadSize(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("setEventForceUploadSize")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 设置用户属性事件上传频率
     * timeMillis：单位毫秒。设置用户属性事件传频率profileUploadInterval
     * 默认值为5秒
     */
//    IdoFlutter().setProfileUploadInterval(Long timeMillis)

    @SyncJsApi(methodName = "setProfileUploadInterval")
    fun setProfileUploadInterval(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("setProfileUploadInterval")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 设置用户属性事件强制上传条数
     * size 设置用户属性事件的强制上传条数profileForceUploadSize
     * 默认数量为5条
     */


    @SyncJsApi(methodName = "setProfileForceUploadSize")
    fun setProfileForceUploadSize(data: JSONObject, activity: Activity) {
        try {
            val intent = Intent("setProfileForceUploadSize")
            intent.putExtra("param", data.toString())
            this.mainProcessTask.setIntent(intent)
            this.mainProcessTask.execAsync()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * IOS 特有
     * 设置App Groups ID
     * 通过苹果开发者后台创建Group Identify， 用于App主包和Extension之间的数据打通。
     *
     */


    fun toMap(bean: Any): Map<String, Any?> {
        val memberProperties = bean::class.memberProperties
        val hashMap = HashMap<String, Any?>()
        for (property in memberProperties) {
            property.isAccessible = true
            // 获取属性的名称和值
            val name = property.name
            var value = property.getter.call(bean)

            hashMap.put(name, value ?: "")
            Log.d(TAG, "toMap  ${bean::class.simpleName} ${name} : ${value}")
        }
        return hashMap
    }
}