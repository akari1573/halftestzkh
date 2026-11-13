package com.example.zkhhalftest

/**
 * 计算类：实现两个数的加减乘除运算。
 *
 * 该类不依赖任何界面，只负责纯计算逻辑，便于复用和测试。
 */
class Calculator {

    /**
     * 加法：返回 first + second
     */
    fun add(first: Double, second: Double): Double = first + second

    /**
     * 减法：返回 first - second
     */
    fun subtract(first: Double, second: Double): Double = first - second

    /**
     * 乘法：返回 first * second
     */
    fun multiply(first: Double, second: Double): Double = first * second

    /**
     * 除法：返回 first / second
     *
     * @throws IllegalArgumentException 当除数为 0 时抛出，
     *         由调用方捕获并提示用户"除数不能为0"
     */
    fun divide(first: Double, second: Double): Double {
        require(second != 0.0) { "除数不能为0" }
        return first / second
    }
}
