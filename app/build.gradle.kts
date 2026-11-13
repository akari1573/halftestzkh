// app 模块的构建配置

plugins {
    // 应用 Android Application 插件，使本模块可以打包成 APK
    alias(libs.plugins.android.application)
}

android {
    // 代码包名（R 类所在包）
    namespace = "com.example.zkhhalftest"
    // 编译所用 SDK 版本（36，使用已安装的 android-36.1 平台）
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        // 应用唯一 id
        applicationId = "com.example.zkhhalftest"
        // 最低支持的 Android 版本（7.0）
        minSdk = 24
        // 目标 SDK 版本
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // 测试运行器
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                // 关闭发布包优化（当前项目用不到，保持简单）
                enable = false
            }
        }
    }

    compileOptions {
        // 使用 Java 11 字节码编译
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // AndroidX 核心扩展库
    implementation(libs.androidx.core.ktx)
    // 生命周期运行时库
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // RecyclerView：第2个Activity 展示历史记录列表所用
    implementation(libs.androidx.recyclerview)

    // 本地单元测试
    testImplementation(libs.junit)
    // 设备上的仪器测试
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
