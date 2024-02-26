package com.blend.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun doSomethingUsefulOne(): Int {
    println("doSomethingUsefulOne")
    //所有kotlinx.coroutines中的挂起函数都是可被取消的。
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    println("doSomethingUsefulTwo")
    delay(1000L)
    return 29
}

//默认顺序调⽤
fun main17() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

//使⽤ async 并发，但是还是在主线程中
//在概念上，async 就类似于 launch。它启动了⼀个单独的协程，这是⼀个轻量级的线程并与其它所有的协程⼀起
//并发的⼯作。不同之处在于 launch 返回⼀个 Job 并且不附带任何结果值，⽽ async 返回⼀个 Deferred ——
// ⼀个轻量级的⾮阻塞 future，这代表了⼀个将会在稍后提供结果的 promise。你可以使⽤ .await() 在
//⼀个延期的值上得到它的最终结果，但是 Deferred 也是⼀个 Job ，所以如果需要的话，你可以取消它。
fun main18() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        // 使用await()获取最终结果
        println("The answer is ${one.await() + two.await()}")
    }
    //这⾥快了两倍，因为两个协程并发执⾏。请注意，使⽤协程进⾏并发总是显式的。
    println("Completed in $time ms")
}

// 惰性启动的 async
// 在计算⼀个值涉及挂起函数时，这个 async(start = CoroutineStart.LAZY)
// 的⽤例⽤于替代标准库中的 lazy 函数。
// Kotlin 协程是一种轻量级的并发编程设计模式，它通过将执行流程切分为多个可暂停和恢复的代码块（协程）来实现并发和
// 异步操作。当一个协程遇到挂起点（如网络请求、IO 操作等）时，它会暂停当前执行，并将控制权返回给调用者，而不会阻塞
// 线程。当挂起点的操作完成后，协程会恢复执行，从挂起点继续往下执行。
fun main19() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) {
            println("one: ${Thread.currentThread().name}")
            doSomethingUsefulOne()
        }
        val two = async(start = CoroutineStart.LAZY) {
            println("two: ${Thread.currentThread().name}")
            doSomethingUsefulTwo()
        }
        delay(3000L)
        one.start()
        two.start()
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

//async⻛格的函数

// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

// 注意，在这个⽰例中我们在 `main` 函数的右边没有加上 `runBlocking`
fun main20() {
    val time = measureTimeMillis {
// 我们可以在协程外⾯启动异步执⾏
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // 但是等待结果必须调⽤其它的挂起或者阻塞
        // 当我们等待结果的时候，这⾥我们使⽤ `runBlocking { …… }` 来阻塞主线程
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

//使⽤ async 的结构化并发
//如果其中⼀个⼦协程（即 two ）失败，第⼀个 async 以及等待中的⽗协程都会被取消
fun main21() = runBlocking<Unit>{
    try {
        failedConcurrentSum()
    }catch (e:ArithmeticException){
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try{
            delay(Long.MAX_VALUE)
            42
        }finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throw an Exception.")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

