package com.blend.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//我们之前 没有在控制台上看到堆栈跟踪信息的打印。
//这是因为在被取消的协程中 CancellationException 被认为 是协程执⾏结束的正常原因。
fun main7() = runBlocking<Unit> {
    val job = launch {
        repeat(1000) { i ->
            delay(500L)
            println("job:I'm sleeping $i")
        }
    }
    // 主线程延迟1300ms
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel()    //取消协程
    //job.cancelAndJoin()
    println("main:Now I can quit!")
}

// 在这里加入了try catch，可以看到堆栈跟踪信息的打印。
fun main8() = runBlocking<Unit> {
    val job = launch {
        repeat(1000) { i ->
            try {
                delay(500L)
            } catch (e: Exception) {
                println("job: ${e.message}")
                println("job: ${e.printStackTrace()}")
            }
            println("job:I'm sleeping $i")
        }
    }
    // 主线程延迟1300ms
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel()    //取消协程
    //job.cancelAndJoin()
    println("main:Now I can quit!")
}

// 协程的取消是协作的
// 如果协程正在执⾏计算任务，那么它是不能被取消的
// 可以看到它连续打印出了“I'm sleeping”，甚⾄在调⽤取消后，作业仍然执⾏了10次循
// 环迭代并运⾏到了它结束为⽌。
fun main9() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch {
        var nextPrintTime = startTime
        var i = 0
        //这里没有挂起函数
        while (i < 10) {  // ⼀个执⾏计算的循环，只是为了占⽤ CPU
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job:I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main:I'm tired of waiting!")
    job.cancelAndJoin()
    println("main:Now I can quit.")
}

// 使计算代码可取消
// 显式的检查取消状态
fun main10() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        //isActive 是⼀个可以被使⽤在 CoroutineScope 中的扩展属性
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job:I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main:I'm tired of waiting!")
    job.cancelAndJoin()
    println("main:Now I can quit.")
}

fun main11() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job:I'm sleeping $i ...")
                delay(500L)
                //假如在这里释放资源
            }
        } finally {
            //在这里释放资源
            // 任何尝试在 finally 块中调⽤挂起函数的⾏为都会抛出 CancellationException
            // 因为这⾥ 持续运⾏的代码是可以被取消的
            // delay(1000L)
            println("job:I'm running finally")
        }
    }
    delay(1300L)
    println("main:I'm tired of waiting!")
    job.cancelAndJoin()
    println("main:Now I can quit.")
}

// 在finally中使用挂起函数
// 在前⼀个例⼦中任何尝试在 finally 块中调⽤挂起函数的⾏为都会抛出 CancellationException，因为这⾥
// 持续运⾏的代码是可以被取消的。通常，这并不是⼀个问题，所有良好的关闭操作（关闭⼀个⽂件、取消⼀个作
// 业、或是关闭任何⼀种通信通道）通常都是⾮阻塞的，并且不会调⽤任何挂起函数。然⽽，在真实的案例中，当你
// 需要挂起⼀个被取消的协程，你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，并
// 使⽤ withContext 函数以及 NonCancellable 上下⽂，⻅如下⽰例所⽰：
fun main12() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job:I'm sleeping $i ...")
                delay(500L)
                //假如在这里释放资源
            }
        } finally {
            withContext(NonCancellable) {
                println("job:I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L)
    println("main:I'm tired of waiting!")
    job.cancelAndJoin()
    println("main:Now I can quit.")
}
