
## permission
android 6.0  runtime permission request.
从 Android 6.0（API 级别 23）开始，用户开始在应用运行时向其授予权限，而不是在应用安装时授予。本库简化和兼容权限请求过程。



*** 相关说明 ***

- 正常权限

正常权限不会直接给用户隐私权带来风险。如果您的应用在其清单中列出了正常权限，系统将自动授予该权限。

- 危险权限

查看危险权限命令：`adb shell pm list permissions -d -g`
<br>

在所有版本的 Android 中，您的应用都需要在其应用清单中同时声明它需要的正常权限和危险权限，不过，该声明的影响因系统版本和应用的目标 SDK 级别的不同而有所差异：

Android 5.1 或更低版本，或者应用的目标 SDK 为 22 或更低：如果您在清单中列出了危险权限，则用户必须
在安装应用时授予此权限；如果他们不授予此权限，系统根本不会安装应用。

Android 6.0 或更高版本，或者应用的目标 SDK 为 23 或更高：应用必须在清单中列出权限，并且它必须在运行
时请求其需要的每项危险权限。用户可以授予或拒绝每项权限，且即使用户拒绝权限请求，应用仍可以继续运行有限的功能。

***
注：从 Android 6.0（API 级别 23）开始，用户可以随时从任意应用调用权限，即使应用面向较低的 API 级别也可以调用。
无论您的应用面向哪个 API 级别，您都应对应用进行测试，以验证它在缺少需要的权限时行为是否正常。
***

## 使用



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