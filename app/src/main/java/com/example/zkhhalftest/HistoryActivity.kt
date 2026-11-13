package com.example.zkhhalftest

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 第2个Activity：历史记录页面。
 *
 * 使用 RecyclerView 展示 [HistoryRepository] 中保存的所有计算记录，
 * 每条记录显示"表达式 + 计算时间"。
 */
class HistoryActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        // 设置标题栏文字
        setTitle(R.string.history_title)

        // 找到布局中的 RecyclerView 控件
        val historyList = findViewById<RecyclerView>(R.id.rvHistory)
        // 设置布局管理器：LinearLayoutManager 表示列表纵向排列
        historyList.layoutManager = LinearLayoutManager(this)
        // 设置适配器：把历史记录数据绑定到列表上显示
        historyList.adapter = HistoryAdapter(HistoryRepository.records)
    }
}
