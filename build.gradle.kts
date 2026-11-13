// 顶层构建文件：声明所有模块共用的插件（apply false 表示只在此声明版本，由子模块实际应用）
plugins {
    alias(libs.plugins.android.application) apply false
}
