# kitx-core [ ![Download](https://api.bintray.com/packages/ymexcn/maven/kitx-core/images/download.svg) ](https://bintray.com/ymexcn/maven/kitx-core/_latestVersion)
android 便捷开发库。包含开发要使用的组件及共用代码。


```groovy
implementation 'cn.ymex.kitx:kitx-core:_latestVersion'
```


## permission
对permission-request的重构和优化。

android 6.0  runtime permission request.
从 Android 6.0（API 级别 23）开始，用户开始在应用运行时向其授予权限，而不是在应用安装时授予。本库简化和兼容权限请求过程。
包含所有危险权限、安装权限、浮窗权限。

*** 相关说明 ***

查看危险权限命令：`adb shell pm list permissions -d -g`

//permission-request的重构。
```
compile 'cn.ymex:permission-request:1.2.0'//已废弃。
```

## adapter

a base adapter for listview and recyclerview

这是一个纯净的adapter ，只关注于数据与组件的绑定。

a base adapter for listview and recyclerview


### ListViewAdapter
ListViewAdapter 继承自BaseAdapter, 只抽象出两个方法 `onCreateViewHolder` ,`onBindViewHolder`. <br>
`onCreateViewHolder`用于创建ViewHolder ,`onBindViewHolder`用于组件的绑定 。用法与RecyclerView.Adapter无差别。

```
public class TextAdapter extends ListViewAdapter<String, ListViewAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(int position, View convertView, ViewGroup parent, ViewHolder hold) {
        TextView tvTitle = hold.find(R.id.tv_title);
        tvTitle.setText(getItem(position));
    }
}
```

### RecyclerAdapter
同上

### DelegateAdapter

基于绑定数据的类型加载不同的视图。

```
recyclerView = (RecyclerView) findViewById(R.id.recycler_type);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

DelegateAdapter.create(DataBox.muxData())
        .bind(Video.class, new VideoViewBinder())
        .bind(Cata.class, new CataViewBinder())
        .attachRecyclerView(recyclerView);
        
class CataViewBinder extends ItemViewBinder<Cata, ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return ItemViewHolder.create(inflater, R.layout.item_cata_layout);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull Cata item,int position) {
            TextView tvTitle = holder.find(R.id.tv_title);
            tvTitle.setText(item.getTitle());
        }
    }


class VideoViewBinder extends ItemViewBinder<Video, VideoViewBinder.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_video_layout, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Video item,int position) {
        holder.tvTitle.setText(item.getTitle());
    }


    class ViewHolder extends ItemViewHolder {

        TextView tvTitle;
        ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = find(R.id.tv_title);
            ivPic = find(R.id.iv_pic);
        }
    }
}
```
基于之前的项目pure-adapter 重构：
```
compile 'cn.ymex:pure-adapter:1.3.5'//已废弃，源码请Maven 仓库下载。
```


## cache 
LruCache 和 DiskLruCache


## http 

A simple convenience library for using a HttpURLConnection to make requests and access the response.

https://github.com/kevinsawicki/http-request


## storage

android 存储相关 SharedPreferences





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
