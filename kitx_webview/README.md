# BrowserView

BrowserView使用腾讯X5 WebView。 集成页面加载进度条与Js 与 Java 通信的功能。 
jsBridge 拓展并兼容 [DSBridge-Android](https://github.com/wendux/DSBridge-Android)。

## 如何使用。
### 引入 script 标签
首先在需要交互的H5页面 引入以下的script 标签。

```html
<script src="/app/kitx/bridge.js"></script>

```
> 也可使用DSBridge script 的标签，`https://cdn.jsdelivr.net/npm/dsbridge/dist/dsbridge.js` 。建议使用本地标签，提高加载速度。

### 相互调用
jsbridge 提供了 js 调用原生和原生调用js 的能力。可参考[DSBridge-Android](https://github.com/wendux/DSBridge-Android)。

#### JS调用原生 

###  原生调用JS

 

