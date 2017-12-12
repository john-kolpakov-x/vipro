cmake_minimum_required(VERSION 3.8)
project(vipro_render_core)

set(CMAKE_CXX_STANDARD 11)

include(./find_java.cmake)

#
# JNI
#

find_package(JNI REQUIRED)

include_directories(${JAVA_INCLUDE_PATH})
include_directories(${JAVA_INCLUDE_PATH2})

#
# Vulkan
#

find_package(Vulkan REQUIRED)
find_package(glfw3 3.2.1 REQUIRED)
find_package(glm REQUIRED)

get_target_property(glfw3_include glfw INTERFACE_INCLUDE_DIRECTORIES)
get_target_property(glfw3_deps glfw INTERFACE_LINK_LIBRARIES)
get_target_property(glfw3_lib glfw IMPORTED_LOCATION_NOCONFIG)

link_libraries(${Vulkan_LIBRARY} ${glfw3_lib} ${glfw3_deps})

#
# Sources
#

set(CORE_FILES kz_pompei_vipro_core_mediator_RenderCore.cpp kz_pompei_vipro_core_mediator_RenderCore.h)
set(MODULE_FILES RenderCore.cpp RenderCore.h vk_util.cpp vk_util.h shaders/shader_data.cpp shaders/shader_data.h)
set(SHADER_FILES shaders/tri.frag.spv.hpp shaders/tri.vert.spv.hpp)
set(HEAD_FILES VDeleter.h use_glfw3.h options.h)
set(SOURCE_FILES ${CORE_FILES} ${MODULE_FILES} ${SHADER_FILES} ${HEAD_FILES} draw_data.cpp draw_data.h use_glm.h)
add_library(vipro_render_core SHARED ${SOURCE_FILES})
