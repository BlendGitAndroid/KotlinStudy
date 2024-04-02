package com.blend.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 协程本质上是轻量级的线程
fun main0() {
    GlobalScope.launch {    // 在后台开启一个新的协程并继续
        delay(1000)
        println("World!")
    }
    println("Hello,")
    //协程已在等待时主线程还在继续，阻塞主线程2秒钟来保证JVM存活
    Thread.sleep(2000)

    // 可以达成一样的效果
//    thread {
//        Thread.sleep(1000)
//        print("World!")
//    }
//    print("hello,")

    // 这个表示阻塞了主线程
    // 调用了 runBlocking 的主线程会一直阻塞直到 runBlocking 内部的协程执行完毕。
    runBlocking {
        delay(2000)
    }
}

// 这个示例可以使用更合乎惯用法的方式重写，使用 runBlocking 来包装 main 函数的执行：
// 把主线程包装了一个Kotlin的协程
// 为了在主线程中创建一个顶层协程作用域。
// runBlocking是一个顶级函数，它创建了一个新的协程作用域，并在主线程中阻塞，直到作用域内
// 的所有协程执行完成。它通常用于在顶层代码中启动协程，或者用于在测试代码中模拟顶层协程作用域。
// main函数本身是一个普通的阻塞函数，无法直接使用协程构建器函数。因此，我们使用 `runBlocking`
// 创建一个顶层协程作用域，使得 `main` 函数可以在其中使用协程。
fun main1() = runBlocking {
    GlobalScope.launch { // 在后台启动⼀个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这⾥会⽴即执⾏
    delay(2000L) // 延迟 2 秒来保证 JVM 存活
}

//等待一个作业
//延迟一段时间来等待另一个协程运行并不是一个好的选择。
//让我们显式（以非阻塞方式）等待所启动的后台 Job 执行结束：
fun main2() {
    runBlocking {
        val job = GlobalScope.launch { // 启动⼀个新协程并保持对这个作业的引⽤
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到⼦协程执⾏结束
    }
}

//Cooperation 合作，协作
//Routine 日常，常规的事物

//结构化的并发
//在我们的示例中，我们使用 runBlocking 协程构建器将 main 函数转换为协程。
//包括 runBlocking 在内的每个协程构建器都将 CoroutineScope 的实例添加到其代码块所在的作用域中。
//我们可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）
//直到在其作用域中启动的所有协程都执行完毕后才会结束。
fun main3() = runBlocking {
    // 这里的this就是CoroutineScope,因为整个runBlocking就是Coroutine的扩展
    this.launch { // 在 runBlocking 作⽤域中启动⼀个新协程
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

// runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有⼦协程结束。主要
// 区别在于，runBlocking ⽅法会阻塞当前线程来等待，⽽ coroutineScope 只是挂起，会释放底层线程⽤于其他
// ⽤途。由于存在这点差异，runBlocking 是常规函数，⽽ coroutineScope 是挂起函数。
// 主协程（`main` 函数中的代码）和子协程（通过 `launch` 函数创建的协程）都是在主线程中运行的。
// 在协程中使用 `launch` 函数启动的协程也会默认运行在调用它的协程的上下文中。因为 `runBlocking` 函数
// 是在主线程中调用的，所以其中的协程也会在主线程中运行。
fun main4() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("1 this name is ${Thread.currentThread().name}")
        println("Task from runBlocking")
    }
    coroutineScope {
        println("2 this name is ${Thread.currentThread().name}")
        // 创建⼀个协程作⽤域
        launch {
            extracted()
        }
        delay(100L)
        println("Task from coroutine scope") // 这⼀⾏会在内嵌 launch 之前输出

        delay(5000L)
        println("Task from coroutine scope 5000") // 这⼀⾏会在内嵌 launch 之前输出
    }
    println("Coroutine scope is over") // 这⼀⾏在内嵌 launch 执⾏完毕后才输出,因为要等到coroutineScope执行完毕
}

// 前面加上了suspend关键字，表示这个函数是一个挂起函数，可以在协程中使用
private suspend fun extracted() {
    println("3 this name is ${Thread.currentThread().name}")
    delay(500L)
    println("Task from nested launch")
}

// 它启动了 10 万个协程，并且在 5 秒钟后，每个协程都输出⼀个点。
fun main5() = runBlocking {
    repeat(100_000) {
        // 这里launch了10万个协程
        launch {
            delay(5000L)
            print(".")
        }
    }
}

fun main5_5() = runBlocking<Unit> {
    // 这里是launch了一个协程,但是有10万次循环,注意区分不一样
    val job = launch {
        repeat(100_000) { i ->
            delay(500L)
            println("job:I'm sleeping $i")
        }
    }
    println("main:Now I can quit!")
}

// 全局协程像守护线程，如果主线程结束，那么全局协程也会结束
fun main6() = runBlocking {
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}
