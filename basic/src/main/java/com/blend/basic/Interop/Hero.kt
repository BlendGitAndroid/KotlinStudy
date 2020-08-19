package com.blend.basic.Interop

import java.io.IOException

/**
 * Kotlin和java互操作
 * 1.互操作性和可空性。
 * 2.类型映射。有些类型映射在Kotlin和java之间并非一一对应。对应Kotlin中的基本数据类型，Java用的是原始数据类型。
 * Java里的原始类型不是对象，但是在Kotlin中，包括基本数据类型在内的所有类型都是对象。不过，Kotlin编译器会将Java
 * 原始数据类型和最相似的Kotlin类型做映射。
 * 3.getter和setter方法和互操作性。如何管理类变量，kotlin和java走的是不同的路。java使用字段并通过方法来控制字段
 * 值读写。kotlin使用的是属性，访问属性值只能读写支持字段，有时会自动暴露读写方法。
 * 4.类之外
 * 5.异常与互操作。Kotlin所有的异常都是未检异常，但Java里面的异常都是已检查异常，为了防止应用奔溃，必须处理他们。
 * 6.java中的函数类型。函数类型和匿名函数能提供高效的语法用于组件间交互，但是在java8之前并不支持Lambda表达式。
 * 对于函数类型，又是如何处理的呢。在Java里，Kotlin函数类型是通过FunctionN这样名字的接口来表示的，FunctionN中
 * 的N代表参数目。
 */

fun main() {
    val adversary = Jhava()
    println(adversary.utterGreeting())

    //这里，friendShipLevel是String类型还是String?类型，编译器并不知道，这叫做平台类型
    //我们总不能给kotlin调用java代码的地方都是用非空判断
    //幸运的是，用可空性注解明确标记，java代码的返回值是有可能为空
    val friendShipLevel = adversary.determineFriendShipLevel()
    println(friendShipLevel?.toLowerCase() ?: "It's complicated.")

    //代码运行时，所有的映射类型都会重新映射回对应的java类型
    val adversaryHitPoint: Int = adversary.hitPoints
    println(adversaryHitPoint.dec())
    println(adversaryHitPoint.javaClass) //int

    //使用setter赋值
    adversary.greeting = "Hello Blend."
    println(adversary.utterGreeting())

    //异常，都是未检查异常，所以从kotlin调用java，需要搞清楚有哪些异常
    adversary.extendHandInFriendShip()
}


//Kotlin的顶层函数
fun makeProclamation() = "Greetings,beast!"


//Kotlin使用默认参数，可以由函数调用者自由选择怎么调用这个函数，不用java中繁重的重载
//Kotlin可以使用命名参数来调用
@JvmOverloads
fun handOverFood(leftHand: String = "berries", rightHand: String = "beef") {
    println("Mmmm... you hand over some delicious $leftHand and $rightHand")
}

//使用@Throws注解，就是要抛出这个异常叫调用者知道
@Throws(IOException::class)
fun acceptApology() {
    throw IOException()
}

//定义translator函数，它的类型是(String) -> Unit
val translator = { utterance: String ->
    println(utterance.toLowerCase().capitalize())
}


class Spellbook {
    //在Java中，不能直接访问spells字段，所以必须调用getSpells。
    //可以给Kotlin属性添加@JvmField注解，暴露它的支持字段给java调用者，从而避免使用getter方法
    //下面两个属性，一个加了支持字段注解，另外一个没有加
    @JvmField
    val spells = listOf<String>("Magic", "Lay");
    val spellsNo = listOf<String>("Magic", "Lay");


    //定义伴生对象
    //伴生对象的属性添加@JvmField注解，就能够像调用静态值一样进行调用了
    //同理，函数也一样，使用@JvmStatic注解
    companion object {
        @JvmField
        val MAX_SPELL_COUNT = 10

        val MAX_SPELL_COUNT_NO = 10

        @JvmStatic
        fun getSpellBook() = println("I am the great grimoire!")
    }
}