LOCAL_PATH       := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE     := newton
LOCAL_SRC_FILES  := newton.cpp
include $(BUILD_SHARED_LIBRARY)
