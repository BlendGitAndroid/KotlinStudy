package com.blend.basic.NyetHack

/**
 * 除非有规定，变量不可能为null值
 *
 * ?:表示这个变量可空，可以为null值。如果一个变量可空，就会存在潜在的危险，所以Kotlin不允许在可空类型上调用函数，除非手动
 * 安全管理。
 *
 * 一：安全调用操作符(?.):无法知道变量会不会为null，若为null，就会跳过函数调用
 *
 * 二：使用!!.操作符：不管为不为null，都要执行，当确信不为null的时候调用
 *
 * 三：使用if判断
 *
 * 四：使用先决条件函数，内置了5个先决条件函数
 *
 */
fun main(args: Array<String>) {


    var beverage = readLine()?.capitalize()
    println(beverage)

    /**
     * 空合并操作符（?:），若昨天求值为null，使用右边的值
     */
    val beverageServed: String = beverage ?: "Buttered Ale"
    println(beverageServed)
}
