package com.blend.kotlinstudy.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blend.kotlinstudy.R
import kotlinx.coroutines.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class CoroutinesActivity : AppCompatActivity() {

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)


        //线程切回来的操作，叫做resume

        //线程开启
//        startThread()

        //避免的回调地狱，来使用线程
//        thread {
//            ioCode1()
//            runOnUiThread {
//                uiCode1()
//                thread {
//                    ioCode2()
//                    runOnUiThread {
//                        uiCode2()
//                    }
//                }
//            }
//        }

        //被suspend修复的方法，也就是挂起函数，必须在一个协程中运行，然后再切换回来
        //这个launch里面的，就是在一个协程中运行
        //在这里指定一个线程组，指定为主线程
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()   //自动切回来，不用管
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }

        classicIoCode(::uiCode1, true)

        scope.launch {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //协程的取消
        scope.cancel()
    }

    private fun startThread() {
        GlobalScope.launch {
            println("Coroutines Camp: ${Thread.currentThread().name}")
        }

        Thread {
            println("Thread Camp 1: ${Thread.currentThread().name}")
        }.start()

        thread {
            println("Thread Camp 2: ${Thread.currentThread().name}")
        }
    }

    //不使用协程，则可以这样写，切换到主线程
    //但是这个方法只能切换到主线程，可以增加一个布尔值
    //但是这也不能自动切换回，顺序保证不了
    private fun classicIoCode(block: () -> Unit, toUiThread: Boolean = true) {
        val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.SECONDS, LinkedBlockingQueue(100))
        executor.execute {
            Thread.sleep(1000)
            println("Classic io1:${Thread.currentThread().name}")
            if (toUiThread) {
                runOnUiThread {
                    block()
                }
            } else {
                block.invoke()
            }
        }
    }

    //suspend只是一个标记，并不会切线程，只是说明被耗时挂起，需要在协程中使用
    //只是一个提醒，如果这个挂起函数中没有使用正在挂起函数，编译器会告诉你这个suspend是无用的
    private suspend fun ioCode1() {
        //在这里进行真正线程的切换
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
            println("Coroutines io1:${Thread.currentThread().name}")
        }

    }

    private suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
            println("Coroutines io2:${Thread.currentThread().name}")
        }
    }

    private suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
            println("Coroutines io3:${Thread.currentThread().name}")
        }
    }

    private fun uiCode1() {
        println("Coroutines ui1:${Thread.currentThread().name}")
    }

    private fun uiCode2() {
        println("Coroutines ui2:${Thread.currentThread().name}")

    }

    private fun uiCode3() {
        println("Coroutines ui3:${Thread.currentThread().name}")
    }
}