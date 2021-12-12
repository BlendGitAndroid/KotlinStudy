package com.blend.coroutines

import kotlin.concurrent.thread
import kotlin.coroutines.*


class BaseInstallation {
}

fun main(args: Array<String>) {
    continuation.resume(Unit)
}

//协程的创建
val continuation = suspend {
    println("In Coroutine.")
    5
}.createCoroutine(object : Continuation<Int> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    //协程体的返回值，会作为resumeWith的入参
    override fun resumeWith(result: Result<Int>) {
        println("Coroutine End: $result")
    }

})

/**
 * Kotlin协程的挂起和恢复能力本质上就是挂起函数的挂起和恢复
 * 使用suspend关键字修饰的函数叫做挂起函数，挂起函数只能在协程体内或其他挂起函数内调用
 * 挂起函数可以调用任何函数，普通函数只能调用普通函数
 */
//函数类型为：suspend(Int) -> Unit
suspend fun suspendFunc01(a: Int) {
    return
}

//函数类型：suspend(String,String) -> Int
suspend fun suspendFunc02(a: String, b: String) = suspendCoroutine<Int> {
    thread {
        it.resumeWith(Result.success(5))
    }
}


