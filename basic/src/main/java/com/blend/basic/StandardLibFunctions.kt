package com.blend.basic

import java.io.File

/**
 * 6个标准库函数：
 * apply、let、run、with、also、takeIf
 */
fun main(args: Array<String>) {

    applyFunc()

    letFunc()

    runFunc()

    alsoFunc()

    takeIfFunc()

}

/**
 * also和let函数功能相似，also也是把接收者作为值参传给Lambda，also返回接收者对象，而let返回Lambda结果
 *
 * also尤其适合针对同一原始对象，在类里面能防止竞态条件
 */
fun alsoFunc() {

    val also = File("test.class").also {
        println(it.name)
    }

}

/**
 * 配置函数：传入一个接收者，然后调用一系列函数来配置它以便使用，并返回接收者
 *
 * val menuFile = File("menu-file.text")
 * menuFile.setReadable(true)
 * menuFile.setWritable(true)
 * menuFile.setExecutable(true)
 */
fun applyFunc() {
    val menuFile = File("menu-file.text").apply {
        setReadable(true)
        setWritable(true)
        setExecutable(true)
    }
}

/**
 * let函数能使唯一值参变量作用于其Lambda表达式里，让it关键字能引用它，并返回最后一行的Lambda结果
 */
fun letFunc() {
    val firstItemSquared = listOf(1, 2, 3).first().let { it * it }
}

/**
 *与apply函数不同，run函数不返回接收者，run返回的是Lambda的结果，下面的例子就是true或者false
 */
fun runFunc() {
    try {
        val serverDragons = File("menu-file.text").run { readText().contains("Dragon") }
    } catch (e: Exception) {

    }


    "Blend".run(::nameIsLong)
        .run(::playerCreateMeaasge)
        .run(::print)
}

fun nameIsLong(name: String) = name.length >= 20
fun playerCreateMeaasge(nameTooLong: Boolean): String {
    return if (nameTooLong) {
        "Name is too long"
    } else {
        "Welcome"
    }
}

/**
 * takeIf需要判断Lambda中提供的条件表达式（predicate），给出true或false的结果，若为true，从takeIf函数中返回接受对象，
 * 为false，返回null
 *
 *  val file = File("myfile.text")
 *  val fileContents = if (file.canRead()) {
 *      file.readLines()
 *  } else {
 *      null
 *  }
 */
fun takeIfFunc() {
    val fileContents = File("myfile.text")
        .takeIf { it.canRead() }
        ?.readText()
}

