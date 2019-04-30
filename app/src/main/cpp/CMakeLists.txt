# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(PATH_TO_AV_ENGINE ${PATH_TO_MEDIACORE}/src/main/cpp)
set(PATH_TO_THIRDPARTY ${PATH_TO_AV_ENGINE}/3rdparty)
set(PATH_TO_PRE_BUILT ${PATH_TO_THIRDPARTY}/prebuilt/${ANDROID_ABI})

message(${PATH_TO_AV_ENGINE})
message(${ANDROID_ABI})

add_definitions("
-DGL_GLEXT_PROTOTYPES
-DEGL_EGLEXT_PROTOTYPES
")

include_directories(BEFORE ${PATH_TO_THIRDPARTY}/ffmpeg/include/)
include_directories(${PATH_TO_AV_ENGINE}/)
include_directories(${PATH_TO_AV_ENGINE}/common/)
include_directories(${PATH_TO_AV_ENGINE}/decoder/)

file(GLOB FILES_LIB_COMMON "${PATH_TO_AV_ENGINE}/common/*.cpp")
file(GLOB FILES_LIB_AUDIO_DECODER "${PATH_TO_AV_ENGINE}/decoder/*.cpp")
file(GLOB FILES_LIB_AUDIO_DECODER_FFMPEGDECODER "${PATH_TO_AV_ENGINE}/decoder/libffmpeg_decoder/*.cpp")

add_library(av-engine STATIC
        ${FILES_LIB_COMMON}
        ${FILES_LIB_AUDIO_DECODER}
        ${FILES_LIB_AUDIO_DECODER_FFMPEGDECODER}
        )

target_link_libraries(av-engine
                       # 引入系统的动态库
                       log
                        android
                        z
                       # 引入ffmpeg相关静态库
                       ${PATH_TO_PRE_BUILT}/libavfilter.a
                       ${PATH_TO_PRE_BUILT}/libavformat.a
                       ${PATH_TO_PRE_BUILT}/libavcodec.a
                       ${PATH_TO_PRE_BUILT}/libswresample.a
                       ${PATH_TO_PRE_BUILT}/libswscale.a
                       ${PATH_TO_PRE_BUILT}/libavutil.a
                       ${PATH_TO_PRE_BUILT}/libpostproc.a
                       ${PATH_TO_PRE_BUILT}/libfdk-aac.a
                       ${PATH_TO_PRE_BUILT}/libvo-aacenc.a
                       ${PATH_TO_PRE_BUILT}/libx264.a
                       )