# kitx
android 便捷开发库。
包含开发必要使用的组件及共用代码，此库是重构版本，把之前开源的android 库集中到一个项目中。 



## kitx_core  
App 开发核心类

```shell
```

## kitx_tips 

常用方法拓展
```groovy
 implementation 'cn.ymex:kitx:tips-v0.0.6'
```

## kitx_gilde

基于gilde 封装的ImageView.

```shell
```

## kitx_webview 

基于 腾讯X5 WebView。 集成页面加载进度条与Js 与 Java 通信的功能。 

```shell
```

## kitx_widget 

常用组件库

```groovy
```

- banner 
- effect 点击效果组件
- TextLabel  混排标签
- SwipeRefreshLayout 下拉刷新
- FlowLayout 流布局
- RatioLayout 比例布局

## kitx_anhttp

使用okhttp + retrofit2 封装，基于LiveData通知的回调,抛弃使用RxJava。

```groovy
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

## start 

```groovy
implementation 'cn.ymex.kitx:kitx-app-start:_latestVersion'
```

快速开发库，默认已经依赖了 `kitx_core`,`kitx_tips`,`kitx_gilde`,`kitx_webview`,`kitx_widget`


### tps_umeng [![](https://jitpack.io/v/ymex/kitx.svg)](https://jitpack.io/#ymex/kitx)
集成友盟的移动统计与消息推送

```groovy
  implementation 'cn.ymex:kitx:tps.umeng-v0.0.3'
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
