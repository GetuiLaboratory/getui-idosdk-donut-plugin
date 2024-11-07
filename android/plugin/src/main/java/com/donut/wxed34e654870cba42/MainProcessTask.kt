import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.util.Log
import com.donut.wxed34e654870cba42.IdoOnForegroundChangedListener
import com.getui.gs.ias.core.GsConfig
import com.getui.gs.sdk.GsManager
import com.getui.gs.sdk.IGtcIdCallback
import com.getui.gtc.base.GtcProvider
import com.getui.gtc.base.util.CommonUtil
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginMainProcessTask
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import java.util.Timer
import java.util.TimerTask

@Parcelize
class MainProcessTask(private var intent: Intent) :
        NativePluginMainProcessTask() {

    companion object {
        private const val TAG = "IdoProvider"
        private var lifecycle = object : IdoOnForegroundChangedListener() {
            override fun onForegroundChanged(isForeground: Boolean) {
                try {
                    Log.d(TAG, "onForegroundChanged isForeground ${isForeground}")
                    GsManager.getInstance().updateAppForegroundState(isForeground)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private var clientCallback: ((Intent) -> Unit)? = null


    fun setClientCallback(callback: (data: Intent) -> Unit) {
        this.clientCallback = callback
    }

    fun setIntent(intent: Intent) {
        this.intent = intent
    }

    /**
     * 运行在主进程的逻辑，不建议在主进程进行耗时太长的操作
     */
    override fun runInMainProcess() {
        Log.d("runInMainProcess", "execute ${intent.action}")
        // 如果需要把主进程的数据回调到小程序进程，就赋值后调用 callback 函数
        when (intent.action) {
            "init" -> {
                init(intent)
            }
            "setDebugEnable" -> {
                setDebugEnable(intent.getBooleanExtra("param", false))
            }
            "getGtcId" -> {
                getGtcId()
            }
            "onEvent" -> {
                onEvent(intent)
            }
            "onBeginEvent" -> {
                onBeginEvent(intent)
            }
            "onEndEvent" -> {
                onEndEvent(intent)
            }
            "setProfile" -> {
                setProfile(intent)
            }
            "setEventUploadInterval" -> {
                setEventUploadInterval(intent)
            }

            "setEventForceUploadSize" -> {
                setEventForceUploadSize(intent)
            }
            "setProfileUploadInterval" -> {
                setProfileUploadInterval(intent)

            }
            "setProfileForceUploadSize" -> {
                setProfileForceUploadSize(intent)
            }
            "onActivityResumed" -> {
                onActivityResumed(intent)
            }
            "onActivityPaused" -> {
                onActivityPaused(intent)
            }
            "onActivityStopped" -> {
                onActivityStopped(intent)
            }

        }
    }

    public fun callbackByMainProcess(obtain: Intent) {
        intent = obtain
        this.callback()
    }

    /**
     * 运行在小程序进程的逻辑
     */
    override fun runInClientProcess() {
        this.clientCallback?.let { callback ->
            callback(intent)
        }
    }

    override fun parseFromParcel(parcel: Parcel?) {
        // 如果需要获得主进程数据，需要重写parseFromParcel，手动解析Parcel
        this.intent = parcel?.readParcelable(Intent::class.java.classLoader) ?: Intent()
    }

    fun init(intent: Intent) {

        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        val appid = if (jsonObject.has("appid")) jsonObject.get("appid") else null
        if (appid != null) {
            GsConfig.setAppId(appid as String)
        }
        GsConfig.setInstallChannel(jsonObject.getString("channel") as String)
        //测试失败
        var callback: IGtcIdCallback = object : IGtcIdCallback {
            override fun onGetGtcId(gtcId: String?) {
                Log.d(TAG, "onGetGtcId:${gtcId}")
                val intent = Intent("gtcId")
                intent.putExtra("gtcId", gtcId)
                callbackByMainProcess(intent)
            }
        }
        GsManager.getInstance().setGtcIdCallback(callback)
        GsManager.getInstance().preInit(GtcProvider.context())
        GsManager.getInstance().init(GtcProvider.context())
    }

    fun setDebugEnable(debugEnable: Boolean) {
        GsConfig.setDebugEnable(debugEnable)
    }

    fun getGtcId() {
        val gtcId = GsManager.getInstance().getGtcId()
        val intent = Intent("getGtcId")
        intent.putExtra("gtcId", gtcId);
        callbackByMainProcess(intent)
    }

    fun onEvent(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        val eventId = jsonObject.getString("eventId")
        val jsonObj = if (jsonObject.has("jsonObject")) jsonObject.getJSONObject("jsonObject") else null
        val ext = if (jsonObject.has("ext")) jsonObject.getString("ext") else null
        GsManager.getInstance().onEvent(eventId, jsonObj, ext)
    }

    fun setProfile(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
//        val ext = if (jsonObject.has("ext")) jsonObject.getString("ext") else null
        GsManager.getInstance().setProfile(jsonObject, null)
    }

    fun onBeginEvent(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        val eventId = jsonObject.getString("eventId")
        val jsonObj = if (jsonObject.has("jsonObject")) jsonObject.getJSONObject("jsonObject") else null
        GsManager.getInstance().onBeginEvent(eventId, jsonObj)
    }

    fun onEndEvent(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        val eventId = jsonObject.getString("eventId")
        val jsonObj = if (jsonObject.has("jsonObject")) jsonObject.getJSONObject("jsonObject") else null
        val ext = if (jsonObject.has("ext")) jsonObject.getString("ext") else null
        GsManager.getInstance().onEndEvent(eventId, jsonObj, ext)
    }

    fun setEventUploadInterval(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        GsConfig.setEventUploadInterval(jsonObject.getLong("timeMillis"))
    }

    fun setEventForceUploadSize(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        GsConfig.setEventForceUploadSize(jsonObject.getInt("size"))
    }

    fun setProfileUploadInterval(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        GsConfig.setProfileUploadInterval(jsonObject.getLong("timeMillis"))
    }

    fun setProfileForceUploadSize(intent: Intent) {
        val param = intent.getStringExtra("param")
        val jsonObject = JSONObject(param)
        GsConfig.setProfileForceUploadSize(jsonObject.getInt("size"))
    }

    private fun onActivityResumed(intent: Intent) {
        Log.d(TAG,"onActivityResumed")
        GsManager.getInstance().onActivityResumed()
        lifecycle.onActivityResumed(null)
   }

    private fun onActivityPaused(intent: Intent) {
        Log.i(TAG, "onActivityPaused isForeground ${CommonUtil.isAppForeground()}")
        GsManager.getInstance().onActivityPaused()

        lifecycle.onActivityPaused(null)
        Log.i(TAG, "onActivityPaused after isForeground ${CommonUtil.isAppForeground()}")
    }

    private fun onActivityStopped(intent: Intent) {
        lifecycle.onActivityStopped(null)
    }

}