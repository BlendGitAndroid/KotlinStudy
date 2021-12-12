package com.blend.coroutines

/**
 * 协程的概念，最核心的点就是函数或者一段程序能够被挂起，稍后再在挂起的位置恢复。挂起和恢复是开发者的程序
 * 逻辑自己控制的，协程是通过主动挂起出让运行权来实现协程的，因此本质上就是讨论程序流程控制的机制。
 */
fun main(args: Array<String>) {
    println("Hello Coroutines!")
}