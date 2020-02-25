# BrowserView

BrowserView使用腾讯X5 WebView。 集成页面加载进度条与Js 与 Java 通信的功能。 
jsBridge 拓展并兼容 [DSBridge-Android](https://github.com/wendux/DSBridge-Android)。



## DEBUG

开启BrowserView的debug 模式，可以使用 `chrome://inspect`与x5 debug 工具。

```
Browser.debug(true)
```

关于 x5 debug 参考：https://x5.tencent.com/

chrome://inspect 参考：https://developers.google.cn/web/tools/chrome-devtools/



## 如何使用
### 初始化

在Application 中或其他地方尽量早的初始化Browser，其会初始化x5核心。

```java
 Browser.create(this)
```

### 引入 script 标签
首先在需要交互的H5页面 引入以下的script 标签。

```html
<script src="/app/kitx/bridge.js"></script>
```
> 也可使用DSBridge script 的标签，`https://cdn.jsdelivr.net/npm/dsbridge/dist/dsbridge.js` 。建议使用本地标签，提高加载速度。

### 相互调用
jsbridge 提供了 js 调用原生和原生调用js 的能力。可参考[DSBridge-Android](https://github.com/wendux/DSBridge-Android)。

在相互调用时约定如下：

- Java API 签名 格式

  **同步API : **`public any handler(Object msg)`

  **异步 API：**`public void handler(Object arg, CompletionHandler handler)`

 参数必须是 Object,(Kotlin为 Any ) 类型，并且必须申明（如果不需要参数，申明后不适用即可）。返回值类型没有限制，可以是任意类型。



#### JS调用原生 


1. 新建一个Java类，实现API

```
public class JsApi{
    //同步API
    @JavascriptInterface
    public String hiSyn(Object msg)  {
        return "java return : "+msg;
    }

    //异步API
    @JavascriptInterface
    public void hiAsyn(Object msg, CompletionHandler<String> handler) {
        handler.complete("java return : "+msg);
    }
}

//kotlin 
public class JsApi{
    /**
     * 同步方法，
     *
     *
     */
    @JavascriptInterface
    fun hiSyn(msg: Any): String {
        return "java return : $msg"
    }

    /**
     * 异步方法
     */
    @JavascriptInterface
    fun hiAsyn(
        msg: Any,
        handler: CompletionHandler<String>
    ) {
        handler.complete("java return : $msg")
    }
}
```

2，h5页面调用
在页面引入的javascript 执行后， 将产生 `dsBridge`（也可以自定义） 可用对象域。
```html
//同步调用
var str=dsBridge.call("hiSyn","hello !");

//异步调用
dsBridge.call("hiAsyn","hello !", function (v) {
  alert(v);
})

```



###  原生调用js 
1,In Javascript

```script
dsBridge.register('addValue',function(l,r){
     return l+r;
})
dsBridge.registerAsyn('append',function(arg1,arg2,arg3,responseCallback){
     responseCallback(arg1+" "+arg2+" "+arg3);
})
```

2，In Java

```java

webView.callHandler("addValue",new Object[]{1,6},new OnReturnValue<String>(){
  @Override
  public void onValue(String retValue) {
    Log.d("jsbridge","call succeed,return value is: "+retValue);
  }
});

webView.callHandler("append",new Object[]{"I","love","you"},new OnReturnValue<String>(){
   @Override
   public void onValue(String retValue) {
     Log.d("jsbridge","call succeed, append string is: "+retValue);
   }
});
​
```

参考：DSBridge-Android ： https://github.com/wendux/DSBridge-Android


