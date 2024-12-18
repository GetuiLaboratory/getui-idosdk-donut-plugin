// pages/android/android.js
const { miniAppPluginId } = require('../../constant');

Page({
  /**
   * 页面的初始数据
   */
  data: {
    myPlugin: undefined,
    quickStartContents: [
      '在「设置」->「安全设置」中手动开启多端插件服务端口',
      '在「工具栏」->「运行设备」中选择 Android 点击「运行」，快速准备运行环境',
      '在打开的 Android Stuido 中点击「播放」运行原生工程',
      '保持开发者工具开启，修改小程序代码和原生代码仅需在 Android Stuido 中点击「播放」查看效果',
    ]
  },

  onLoadPlugin() {
    const listener = (param) => {
      console.log('onMiniPluginEvent listener '+JSON.stringify(param))
      }
      
    wx.miniapp.loadNativePlugin({
      pluginId: miniAppPluginId,
      success: (plugin) => {
        console.log('load plugin success', plugin)
        plugin.onMiniPluginEvent(listener)
        this.setData({
          myPlugin: plugin
        })
      },
      fail: (e) => {
        console.log('load plugin fail', e)
      }
    })
  },

  onUsePlugin() {
    const { myPlugin } = this.data
    if (!myPlugin) {
      console.log('plugin is undefined')
      return
    }
    myPlugin.setDebugEnable({"debugEnable":1})
    
    myPlugin.setEventUploadInterval({
      'timeMillis': 5000
    }) 
  
    myPlugin.setEventForceUploadSize({
      'size': 30
    }) 
  
    myPlugin.setProfileUploadInterval({
      'timeMillis':5000
    })
    
    myPlugin.setProfileForceUploadSize({
       'size':5
    })
    
    myPlugin.init({"channel":"donut","appid":"djYjSlFVMf6p5YOy2OQUs8"})
    const gtcid =  myPlugin.getGtcId()
    console.log('getGtcId '+gtcid)
    let ver = myPlugin.getVersion()
    console.log(ver)

    myPlugin.trackCountEvent(
      {"eventId":"idididid","jsonObject":{"a":1,"b":"你好"},"withExt":"dddddddd"})

      myPlugin.setProfile({'jsonObject': {
        'property1': 'value1',
        'property2': 100
      },
      'withExt':'this is ext string3'})
    
      myPlugin.onBeginEvent({"eventId":"qqqq","jsonObject":{"aaa":"ddddd"}})
      myPlugin.onEndEvent({"eventId":"qqqq","jsonObject":{"aaa":"ddddd"}})
  
  
  
    },


  copyLink() {
    wx.setClipboardData({
      data: 'https://dev.weixin.qq.com/docs/framework/dev/plugin/androidPlugin.html',
    })
  }
})