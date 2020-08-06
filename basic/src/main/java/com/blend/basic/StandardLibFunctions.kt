package com.blend.basic

import java.io.File

/**
 * 6个标准库函数：
 * apply、let、run、with、also、takeIf
 *
 * 不支持自定义变量名：apply、run(with)
 * 能自定义变量名：also、let、takeIf
 *
 * 返回结果是接收者对象：apply、also、takeIf(可空接收者对象)
 * 返回结果是Lambda结果：let、run(with)
 *
 * 使用下来相似点：apply和also、run和let
 */
fun main(args: Array<String>) {

    applyFunc()

    letFunc()

    runFunc()

    withFunc()

    alsoFunc()

    takeIfFunc()

}

/**
 * with函数是run的变体，它们的功能都是一样的，但是with的调用方式不同，调用时需要值参作为其第一个参数传入。
 * 也不能自定义变量名
 */
fun withFunc() {
    val serverDragons = with(File("menu-file.text")) {
        readText().contains("Dragon")
    }
}


/**
 * also和let函数功能区别，also也是把接收者作为值参传给Lambda（也就是可以自定义变脸），also返回接收者对象，而let返回Lambda结果
 * also和apply很相似，都是返回接收者对象，区别就是also能自定义变量，但是apply不能
 *
 * also尤其适合针对同一原始对象，在类里面能防止竞态条件。
 */
fun alsoFunc() {

    val also = File("test.class").also {
        println(it.name)
    }

    val alsoAA = File("test.class").also { aa ->
        println(aa.name)
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
 *
 * let函数与apply函数的不同：
 * 1)let函数会把接收者传递给Lambda，也就是可以自己自定义变量名，但是apply函数不可以自己定义变量名，只能使用默认的this
 * 2)apply函数会返回当前的接收者，也就是this，但是let会返回Lambda的最后一行，也就是Lambda的结果值给调用者
 */
fun letFunc() {
    val firstItemSquared = listOf(1, 2, 3).first().let { it * it }

    val firstItemSquaredAA = listOf(1, 2, 3).first().let { aa -> aa * aa }
}

/**
 *与apply函数不同，run函数返回的不是接收者，而是返回的是Lambda的结果，下面的例子就是true或者false
 *这个Lambda就是匿名函数，返回的就是最后一行语句的结果值
 *同样，run函数也不能自定义变量名
 */
fun runFunc() {
    try {
        val serverDragons = File("menu-file.text").run {
            readText().contains("Dragon")
        }
    } catch (e: Exception) {

    }


    /**
     * 使用run，可以进行链式调用
     */
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

