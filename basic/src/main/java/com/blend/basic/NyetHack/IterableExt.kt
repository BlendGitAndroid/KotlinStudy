package com.blend.basic.NyetHack


/**
 * 定义扩展文件
 *
 * Iterable<T>:扩展接收者类型
 */
fun <T> Iterable<T>.random(): T = this.shuffled().last()