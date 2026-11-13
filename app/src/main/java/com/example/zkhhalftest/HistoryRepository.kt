package com.example.zkhhalftest

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 保存所有历史记录的内存仓库（单例）。
 *
 * 使用 object 声明保证整个应用只有一份实例：
 * 无论屏幕如何旋转、页面如何切换，只要进程还活着，历史记录就不会丢失。
 */
object HistoryRepository {

    // 存放全部历史记录的列表（私有，外部只能通过 records 读取）
    private val recordList = mutableListOf<HistoryRecord>()

    /**
     * 对外提供只读的历史记录列表。
     */
    val records: List<HistoryRecord>
        get() = recordList

    /**
     * 新增一条记录：带上当前时间，并插入到列表最前面，
     * 这样最新的一次计算总是显示在历史列表顶部。
     */
    fun add(expression: String) {
        // 每次调用时新建格式化器，避免用户运行中切换系统语言导致格式失效
        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val record = HistoryRecord(expression, timeFormat.format(Date()))
        recordList.add(0, record)
    }

    /**
     * 清空所有历史记录。
     */
    fun clear() {
        recordList.clear()
    }
}
