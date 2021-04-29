package com.blend.kotlinstudy


class CodeTest {
    fun eat(food: String = "米饭") {
        synchronized(this) {
            println("吃$food")
        }
    }
}

val c by myLazyCall {
    CodeTest()
}

fun <T> myLazyCall(block: () -> T) = MyLazy<T>(block)

class MyLazy<out T>(val block: () -> T) :Lazy<T>{
    val tmp: T? = null


    override fun isInitialized(): Boolean {
        return tmp != null
    }

    override val value: T
        get() {
            // 判断是否初始化,如果未初始化,调用 lambda 创建返回
            return if (!isInitialized()) {
                block()
            } else {
                tmp!!
            }
        }
}
