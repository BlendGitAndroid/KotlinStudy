package com.blend.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

// 在实践中绝⼤多数取消⼀个协程的理由是它有可能超时。
// withTimeout 抛出了 TimeoutCancellationException ，它是 CancellationException 的⼦类。
// 我们之前 没有在控制台上看到堆栈跟踪信息的打印。
// 这是因为在被取消的协程中 CancellationException 被认为 是协程执⾏结束的正常原因。
fun main13() = runBlocking {
    withTimeout(1330L) {
        repeat(1000) { i ->
            println("I'm sleeping $i")
            delay(500L)
        }
    }
}

// 如果在timeout内完成了任务，那么就不会抛出异常，会返回OK
// 否则就是返回null
fun main14() = runBlocking {
    //withTimeoutOrNull 通过返回 null 来进⾏超时操作，从⽽替代抛出⼀个异常：
    val result = withTimeoutOrNull(1330L) {
        repeat(1000) { i ->
            println("I'm sleeping $i")
            delay(500L)
        }
        "OK"
    }
    println(result ?: "Done")
}

var acquired = 0

//伪代码
class Resource {
    init {
        acquired++
    }

    fun close() {
        acquired--
    }
}

fun main15() {
    runBlocking {
        repeat(100000) {
            launch {
                val resource = withTimeout(60) {
                    delay(59)
                    // 这一行代码执行时间只有1ms，有可能还没执行完就会超时，抛出异常
                    // 导致下面的close方法不会执行，acquired就不会减1，所以最后acquired不是0
                    Resource()
                }
                resource.close()
            }
        }
    }
    //非0，有内存泄漏
    //0代表资源都释放了，没有内存泄漏
    println(acquired) //期待值是0
}

// 释放资源的操作，放入finally当中
fun main16() {
    runBlocking {
        repeat(100000) {
            launch {
                var resource: Resource? = null
                try {
                    withTimeout(60) {
                        delay(59)
                        resource = Resource()
                    }
                } finally {
                    resource?.close()
                }
            }
        }

    }
    //0代表资源都释放了，没有内存泄漏
    println(acquired) //期待值是0
}