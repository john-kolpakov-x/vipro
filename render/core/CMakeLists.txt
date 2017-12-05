cmake_minimum_required(VERSION 3.8)
project(vipro_render_core)

set(CMAKE_CXX_STANDARD 11)

include(./find_java.cmake)

find_package(JNI REQUIRED)

include_directories(${JAVA_INCLUDE_PATH})
include_directories(${JAVA_INCLUDE_PATH2})

set(SOURCE_FILES kz_pompei_vipro_core_mediator_RenderCore.cpp kz_pompei_vipro_core_mediator_RenderCore.h RenderCore.cpp RenderCore.h)
add_library(vipro_render_core SHARED ${SOURCE_FILES})
