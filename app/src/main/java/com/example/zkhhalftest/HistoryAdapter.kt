package com.example.zkhhalftest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 历史记录的 RecyclerView 适配器。
 *
 * 作用：把 [HistoryRecord] 列表数据"翻译"成屏幕上一个个列表项。
 * RecyclerView 只创建屏幕可见的少量 item 视图并循环复用，
 * 即使历史记录很多也能流畅滚动。
 *
 * @param records 要显示的历史记录列表（来自 HistoryRepository）
 */
class HistoryAdapter(private val records: List<HistoryRecord>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    /**
     * ViewHolder：缓存单个列表项里控件的引用，
     * 避免每次绑定数据时重复执行 findViewById。
     */
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expressionText: TextView = itemView.findViewById(R.id.tvExpression) // 表达式
        val timeText: TextView = itemView.findViewById(R.id.tvTime)             // 时间
    }

    /**
     * 创建列表项视图：把 item_history.xml 膨胀成一个 View，
     * 再包装进 ViewHolder 返回。
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    /**
     * 把第 position 条记录的数据填充到 ViewHolder 的控件上。
     */
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val record = records[position]
        holder.expressionText.text = record.expression // 显示表达式，如 "12 + 34 = 46"
        holder.timeText.text = record.time             // 显示计算时间
    }

    /**
     * 返回列表总条数，RecyclerView 据此知道一共要显示多少项。
     */
    override fun getItemCount(): Int = records.size
}
