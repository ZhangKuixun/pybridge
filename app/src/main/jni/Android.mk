LOCAL_PATH := $(call my-dir)
# CRYSTAX_PATH := /Users/jventura/Library/Android/crystax-ndk-10.3.2
CRYSTAX_PATH := /usr/local/Caskroom/crystax-ndk/10.3.2/crystax-ndk-10.3.2

# 运行：
# cd app/src/main/jni
# ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk

# Build libpybridge.so

include $(CLEAR_VARS)
LOCAL_MODULE    := pybridge
LOCAL_SRC_FILES := pybridge.c
LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := python3.5m
include $(BUILD_SHARED_LIBRARY)


# Include libpython3.5m.so

include $(CLEAR_VARS)
LOCAL_MODULE    := python3.5m
LOCAL_SRC_FILES := $(CRYSTAX_PATH)/sources/python/3.5/libs/$(TARGET_ARCH_ABI)/libpython3.5m.so
LOCAL_EXPORT_CFLAGS := -I $(CRYSTAX_PATH)/sources/python/3.5/include/python/
include $(PREBUILT_SHARED_LIBRARY)


# Include select.so

include $(CLEAR_VARS)
LOCAL_MODULE    := select
LOCAL_SRC_FILES := $(CRYSTAX_PATH)/sources/python/3.5/libs/$(TARGET_ARCH_ABI)/modules/select.so
include $(PREBUILT_SHARED_LIBRARY)