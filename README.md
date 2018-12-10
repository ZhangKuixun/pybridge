# PyBridge

PyBridge is a JNI implementation that allows you to reuse your existing Python code in a native
Android Java application. It allows you to send String or JSON messages to your Python interpreter
without the need for network frameworks. Instead of using web applications disguised as native
applications, you can reuse your Python backend code and implement truly native Android applications.
PyBridge uses the Python 3.5 distribution bundled with [Crystax NDK](https://www.crystax.net/).

PyBridge is being used in production on [one of my Android apps](https://play.google.com/store/apps/details?id=com.flatangle.charts)
and it shares a large amount of code with [one of my web applications](http://elements.flatangle.com/).

*Shameless plug:* I do contract work, check out my website at http://joaoventura.net/ or buy my apps!


## Overview

This repository shows the source code of an empty Android application with a TextView.
When the main activity is started, it simply extracts all the necessary Python files to the device,
initializes the Python interpreter and sets a message on the TextView.

![App image](https://github.com/joaoventura/pybridge/blob/master/pybridge.png)


## Running the project

Clone this project and open it on the latest Android Studio.

To build the pybridge shared library you will need to download the Crystax NDK from
https://www.crystax.net/en/download. Open the `app/src/main/jni/Android.mk` file and change the
`CRYSTAX_PATH` to match the path of your Crystax NDK installation. Finally, open the terminal,
cd to `app/src/main/jni`, and run `path/to/crystax/ndk-build`. You should have libcrystax, 
libpython3.5 and libpybridge in `src/main/libs`.

关于上面这一段操作流程的执行遇到的问题：

翻译：

克隆此项目并在最新的Android Studio上打开它。

要构建pybridge共享库，您需要从 https://www.crystax.net/en/download 下载 Crystax-NDK。打开`app/src/main/jni/Android.mk`文件
并更改 CRYSTAX_PATH 以匹配 Crystax-NDK 安装的路径。最后，打开终端，cd到`app/src/main/jni`，然后运行`path/to/crystax/ndk-build`。
在`src/main/libs`中有 libcrystax，libpython3.5和libpybridge。

问题：

1、下载过程中会很漫长2小时左右

2、Mac Os 下载 crystax-ndk-...-darwin-x86_64.tar

3、"cd到`app/src/main/jni`，然后运行`path/to/crystax/ndk-build`"，这句话主要是编译so库。运行`path/to/crystax/ndk-build`会有异常，
    所以换一句话执行：`ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk`。
    完成之后在`src/main/jni`目录下多了一个`libs`和`obj`文件。将`libs`拷贝到`src/main/`下面。

在Android Studio中运行项目，您会在屏幕上看到一条消息"Hello Python 3.5"。


Run the project in the Android Studio and you should see a `Hello Python 3.5` message in the screen.


## How it works

All the relevant changes from an empty Android base application can be found in [this commit
](https://github.com/joaoventura/pybridge/commit/723b7e463ff1a8a3b6ff2bfcae272ce9c07bf800).
The real meat are in the following files:

* [AssetExtractor.java](https://github.com/joaoventura/pybridge/blob/master/app/src/main/java/com/jventura/pybridge/AssetExtractor.java) -
 Extracts the python files from the APK assets folder to the device. We must extract the files to
the device as the Python import mechanism does not recognize files inside the APK file.

* [PyBridge.java](https://github.com/joaoventura/pybridge/blob/master/app/src/main/java/com/jventura/pybridge/PyBridge.java) -
 Implements the Java wrapper for the pybridge.c file. You will use the methods of this class to
start, stop, and send messages to your Python interpreter.

* [pybridge.c](https://github.com/joaoventura/pybridge/blob/master/app/src/main/jni/pybridge.c) -
 Implements the JNI C interface and it is where we really handle the CPython API.

* [bootstrap.py](https://github.com/joaoventura/pybridge/blob/master/app/src/main/assets/python/bootstrap.py) -
 Python script that runs when the Python interpreter is initialized. This file must be used to
configure all necessary Python code.

* [MainActivity.java](https://github.com/joaoventura/pybridge/blob/master/app/src/main/java/com/jventura/pyapp/MainActivity.java) -
 This file just shows how you can use PyBridge to run a Python function. It basically extracts the
Python standard lib and bootstrap file from the APK assets to the device, starts the interpreter,
gets the result from a Python function and updates the TextView accordingly.

The AssetExtractor class provides some utilities that you can use to handle application updates,
such as setting and retrieving the version of the assets or to confirm if the assets are already
extracted on the device. In a production application you will want to extract the files from the APK
only when it runs on the first time or after the application updates.


## Limitations

PyBridge uses the Python 3.5 distribution bundled with [Crystax NDK](https://www.crystax.net/).
The Crystax NDK allows you, in theory, to use or compile any C python module out there.
Bundle the compiled modules in the python assets folder together with the standard library, import
them and you're done.

The performance of the Python interpreter on modern smartphones is more than enough for most use cases,
but you should always consider wrapping PyBridge calls in a separate thread so that you do not block
the main UI thread.

If you have a pure python module with lots of python files, consider adding them to a zip file
and adding the zip file to sys.path in bootstrap.py. It will save time when you extract the module
from the APK assets and it will prevent the creation of pycache files which will only increase the
size of the data consumed by your app. For best performance, consider using only bytecode compiled
files inside the zip file (check [this script](https://github.com/flatangle/flatlib/blob/master/scripts/build.py)
for ideas how to automatically build bytecode compiled zip files).


## License

You can use this project if you want, a simple acknowledgment is enough but not required.
