package com.example.zkhhalftest

/**
 * 一条计算历史记录的数据类。
 *
 * @param expression 完整的表达式，例如 "12 + 34 = 46"
 * @param time       执行计算的时间，格式为 "yyyy-MM-dd HH:mm:ss"
 */
data class HistoryRecord(
    val expression: String,
    val time: String
)
