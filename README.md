# kitx
android 便捷开发库。包含开发必要使用的组件及共用代码，此库是重构版本，把之前开源的android 库集中到一个项目中。 


## kitx_core  [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-core/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-core/_latestVersion)
App 开发核心类

```shell
implementation 'cn.ymex.kitx:kitx-core:_latestVersion'
```

## kitx_tips [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-tips/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-tips/_latestVersion)

常用方法拓展
```shell
implementation 'cn.ymex.kitx:kitx-tips:_latestVersion'
```

## kitx_gilde [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-widget-glide/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-widget-glide/_latestVersion)


基于gilde 封装的ImageView.

```shell
implementation 'cn.ymex.kitx:kitx-widget-glide:_latestVersion'
```

## kitx_webview [ ![Download](https://api.bintray.com/packages/ymex/maven/widget-webview/images/download.svg) ](https://bintray.com/ymex/maven/widget-webview/_latestVersion)
基于 腾讯X5 WebView。 集成页面加载进度条与Js 与 Java 通信的功能。 

```shell
implementation 'cn.ymex.kitx:kitx-widget-webview:_latestVersion'
```

## kitx_widget [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-widget/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-widget/_latestVersion)

共用组件库

```shell
implementation 'cn.ymex.kitx:kitx-widget:_latestVersion'
```

- banner 
- effect 点击效果组件
- TextLabel  混排标签
- SwipeRefreshLayout 下拉刷新
- FlowLayout 流布局
- RatioLayout 比例布局

## kitx_anhttp [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-anhttp/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-anhttp/_latestVersion)

使用okhttp + retrofit2 封装，基于LiveData通知的回调,抛弃使用RxJava。

```
implementation 'cn.ymex.kitx:kitx-anhttp:_latestVersion'
//依赖项
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'
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
