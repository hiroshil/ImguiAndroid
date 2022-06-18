# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

project("imguitestmenu")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( menu  SHARED
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_draw.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_demo.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_tables.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_widgets.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_impl_android.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/imgui_impl_opengl3.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImGui/Imgui_Android_Input.cpp

        ${GL3STUB_SRC}
        ${CMAKE_CURRENT_SOURCE_DIR}/native-lib.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/ImguiEGL.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/MainMemu.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/MyFile.cpp

        # Provides a relative path to your source file(s).
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        menu
        android
        EGL
        GLESv3
        log
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})