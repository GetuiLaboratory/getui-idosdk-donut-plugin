// pages/ios/ios.js
const {
  miniAppPluginId
} = require('../../constant');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    myPlugin: undefined,
    quickStartContents: [
      '在「设置」->「安全设置」中手动开启多端插件服务端口',
      '在「工具栏」->「运行设备」中选择 iOS 点击「运行」，快速准备运行环境',
      '在打开的 Xcode 中点击「播放」运行原生工程',
      '保持开发者工具开启，修改小程序代码和原生代码仅需在 Xcode 中点击「播放」查看效果',
    ]
  },

  onLoadPlugin() {

    const listener1 = (param) => {
      console.log('onMiniPluginEvent listener:', param)
    }

    const listener2 = (param) => {
      console.log('onMiniPluginEvent listener2:', param)
    }

    wx.miniapp.loadNativePlugin({
      pluginId: miniAppPluginId,
      success: (plugin) => {
        console.log('load plugin success', plugin)
        //监听native的事件
        plugin.onMiniPluginEvent(listener1)
        //可以设置多个监听
        //plugin.onMiniPluginEvent(listener2)
        this.setData({
          myPlugin: plugin
        })
      },
      fail: (e) => {
        console.log('load plugin fail', e)
      }
    })
  },
  onStartSdk() {
    const {
      myPlugin
    } = this.data;
    console.log("onStartSdk", myPlugin);
    myPlugin.startSdk({
      'appId': 'xXmjbbab3b5F1m7wAYZoG2',
      'channelId': ''
    })
  },
  setDebugEnable() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setDebugEnable({
      'debugEnable': 1
    })
  },
  getGtcId() {
    const {
      myPlugin
    } = this.data;
    console.log('gtcid=', myPlugin.getGtcId());
  },
  onBeginEvent() {
    const {
      myPlugin
    } = this.data;
    myPlugin.onBeginEvent({
      'eventId': '001'
    })
  },
  onEndEvent() {
    const {
      myPlugin
    } = this.data;
    myPlugin.onEndEvent({
      'eventId': '001',
      'jsonObject': {
        'name': 'superman',
        'age': 18
      },
      'withExt': 'this is ext string'
    })
  },
  trackCountEvent() {
    const {
      myPlugin
    } = this.data;
    myPlugin.trackCountEvent({
      'eventId': '002',
      'jsonObject': {
        'name': 'superman2',
        'age': 19
      },
      'withExt': 'this is ext string2'
    })
  },

  setProfile() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setProfile({
      'jsonObject': {
        'property1': 'value1',
        'property2': 100
      },
      'withExt':'this is ext string3'
    })
  },

  setApplicationGroupIdentifier() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setApplicationGroupIdentifier({
      'identifier': 'group.ent.com.getui.www'
    })
  },

  setEventUploadInterval() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setEventUploadInterval({
      'timeMillis': 5000
    })
  },

  setEventForceUploadSize() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setEventForceUploadSize({
      'size': 30
    })
  },
  setProfileUploadInterval() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setProfileUploadInterval({
      'timeMillis':5000
    })
  },
  setProfileForceUploadSize() {
    const {
      myPlugin
    } = this.data;
     myPlugin.setProfileForceUploadSize({
       'size':5
     })

  },
  getVersion() {
    const {
      myPlugin
    } = this.data;
    let ver = myPlugin.getVersion()
    console.log(ver)
  },
  onUsePlugin() {
    const {
      myPlugin
    } = this.data
    if (!myPlugin) {
      console.log('plugin is undefined')
      return
    }
    const ret = myPlugin.mySyncFunc({
      a: 'hello',
      b: [1, 2]
    })
    console.log('mySyncFunc ret:', ret)

    myPlugin.myAsyncFuncwithCallback({
      a: 'hello',
      b: [1, 2]
    }, (ret) => {
      console.log('myAsyncFuncwithCallback ret:', ret)
    })
  },

  copyLink() {
    wx.setClipboardData({
      data: 'https://dev.weixin.qq.com/docs/framework/dev/plugin/iosPlugin.html',
    })
  }
})