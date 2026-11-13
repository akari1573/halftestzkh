package com.example.zkhhalftest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

/**
 * 第1个Activity：计算器主界面。
 *
 * 功能：
 * 1. 用户输入两个数，并通过单选按钮选择 + - × ÷ 运算；
 * 2. 点击"计算"按钮后，调用 [Calculator] 计算类完成运算并显示结果；
 * 3. 屏幕旋转（横竖屏切换）时结果不丢失；
 * 4. 标题栏提供"历史"菜单，点击后跳转到第2个Activity（[HistoryActivity]）。
 */
class MainActivity : Activity() {

    companion object {
        // 保存/恢复实例状态时，存放"结果文本"所用的键
        private const val KEY_RESULT = "key_result"
    }

    // 计算类的对象，负责实际的加减乘除运算
    private val calculator = Calculator()

    // 界面上的控件引用（在 onCreate 中初始化）
    private lateinit var firstNumberInput: EditText   // 第一个数输入框
    private lateinit var secondNumberInput: EditText  // 第二个数输入框
    private lateinit var operatorGroup: RadioGroup    // 运算符单选按钮组
    private lateinit var resultText: TextView         // 结果文本

    /**
     * 创建界面：加载布局、绑定控件、恢复屏幕旋转前的结果。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 设置标题栏为"姓名+学号"
        setTitle(R.string.main_title)

        // 通过 findViewById 绑定布局中的控件
        firstNumberInput = findViewById(R.id.etFirstNumber)
        secondNumberInput = findViewById(R.id.etSecondNumber)
        operatorGroup = findViewById(R.id.rgOperator)
        resultText = findViewById(R.id.tvResult)

        // 点击"计算"按钮时执行 calculate() 方法
        findViewById<Button>(R.id.btnCalculate).setOnClickListener { calculate() }

        // 屏幕旋转后 Activity 会重建，这里从 savedInstanceState 恢复上次的结果
        if (savedInstanceState != null) {
            resultText.text = savedInstanceState.getString(KEY_RESULT)
        }
    }

    /**
     * 加载标题栏菜单（"历史"菜单项）。
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * 处理菜单点击事件：点击"历史"时跳转到 HistoryActivity。
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                // 启动第2个Activity，展示历史记录
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Activity 销毁前保存状态：把结果文本存入 Bundle，
     * 屏幕旋转后即可在 onCreate 中恢复，保证结果不丢失。
     * （两个输入框和单选按钮因为有 id，系统会自动保存和恢复）
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RESULT, resultText.text.toString())
    }

    /**
     * 执行计算：
     * 1. 读取并校验两个输入框的数字；
     * 2. 根据选中的单选按钮确定运算符；
     * 3. 调用计算类对象执行运算（捕获除数为0的异常）；
     * 4. 显示"表达式 = 结果"，并把记录存入历史仓库。
     */
    private fun calculate() {
        // 把输入框内容转成 Double，输入不合法（为空/非数字）时返回 null
        val first = firstNumberInput.text.toString().toDoubleOrNull()
        val second = secondNumberInput.text.toString().toDoubleOrNull()
        if (first == null || second == null) {
            // 输入无效：用 Toast 提示用户
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show()
            return
        }

        // 根据选中的单选按钮 id 确定运算符符号
        val operator = when (operatorGroup.checkedRadioButtonId) {
            R.id.rbAdd -> getString(R.string.operator_add)          // +
            R.id.rbSubtract -> getString(R.string.operator_subtract) // -
            R.id.rbMultiply -> getString(R.string.operator_multiply) // ×
            R.id.rbDivide -> getString(R.string.operator_divide)     // ÷
            else -> {
                // 一个都没选：提示后直接返回
                Toast.makeText(this, R.string.error_no_operator, Toast.LENGTH_SHORT).show()
                return
            }
        }

        // 调用计算类对象执行对应的运算
        val result = try {
            when (operator) {
                getString(R.string.operator_add) -> calculator.add(first, second)
                getString(R.string.operator_subtract) -> calculator.subtract(first, second)
                getString(R.string.operator_multiply) -> calculator.multiply(first, second)
                else -> calculator.divide(first, second)
            }
        } catch (e: IllegalArgumentException) {
            // 除数为0：计算类的 divide 方法会抛出异常，这里提示用户
            Toast.makeText(this, R.string.error_divide_by_zero, Toast.LENGTH_SHORT).show()
            return
        }

        // 拼出完整表达式，例如："12 + 34 = 46"
        val expression = getString(
            R.string.result_format,
            formatNumber(first), operator, formatNumber(second), formatNumber(result)
        )
        // 显示结果，并写入历史记录仓库
        resultText.text = expression
        HistoryRepository.add(expression)
    }

    /**
     * 格式化数字：若结果是整数则去掉小数点（46 而不是 46.0），
     * 否则按原样显示。
     */
    private fun formatNumber(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}
