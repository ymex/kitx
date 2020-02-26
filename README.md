# kitx
android 便捷开发库。包含开发必要使用的组件及共用代码。


## kitx_core  [ ![Download](https://api.bintray.com/packages/ymex/maven/core/images/download.svg) ](https://bintray.com/ymex/maven/core/_latestVersion)
App 开发核心类

```shell
implementation 'cn.ymex.kitx:core:1.0.0'
```

## kitx_utils [ ![Download](https://api.bintray.com/packages/ymex/maven/utils-ktx/images/download.svg) ](https://bintray.com/ymex/maven/utils-ktx/_latestVersion)
常用方法拓展
```shell
implementation 'cn.ymex.kitx:utils-ktx:1.0.0'
```

## kitx_gilde [ ![Download](https://api.bintray.com/packages/ymex/maven/widget-glide/images/download.svg) ](https://bintray.com/ymex/maven/widget-glide/_latestVersion)
基于gilde 封装的ImageView.

```shell
implementation 'cn.ymex.kitx:widget-glide:1.0.0'
```

## kitx_webview [ ![Download](https://api.bintray.com/packages/ymex/maven/widget-webview/images/download.svg) ](https://bintray.com/ymex/maven/widget-webview/_latestVersion)
基于 腾讯X5 WebView。 集成页面加载进度条与Js 与 Java 通信的功能。 

```shell
implementation 'cn.ymex.kitx:widget-webview:1.0.0'
```

## kitx_widget [ ![Download](https://api.bintray.com/packages/ymex/maven/widget/images/download.svg) ](https://bintray.com/ymex/maven/widget/_latestVersion)

共用组件库

```shell
implementation 'cn.ymex.kitx:widget:1.0.0'
```

- banner 
- effect 点击效果组件
- TextLabel  混排标签
- SwipeRefreshLayout 下拉刷新
- FlowLayout 流布局
- RatioLayout 比例布局

## kitx_anhttp [ ![Download](https://api.bintray.com/packages/ymex/maven/anhttp/images/download.svg?version=1.2.0) ](https://bintray.com/ymex/maven/anhttp/1.2.0/link)

使用okhttp + retrofit2 封装，基于LiveData通知的回调,抛弃使用RxJava。

```
implementation 'cn.ymex.kitx:anhttp:1.2.0'

//依赖项
api "com.squareup.okhttp3:okhttp:4.4.0"
api "com.squareup.okhttp3:logging-interceptor:4.4.0"
api "com.squareup.retrofit2:retrofit:2.7.2"
api "com.squareup.retrofit2:converter-gson:2.7.2"
api "com.squareup.retrofit2:converter-scalars:2.7.2"
```

License
-------

    Copyright 2017 ymex.cn

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.