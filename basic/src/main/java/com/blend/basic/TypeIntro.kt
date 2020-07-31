package com.blend.basic


/**
 * 编译时常量：保证只读，只能定义在函数之外
 *
 * 编译时常量支持的基本数据类型：
 * String，Int，Double，Float，Short，Byte，Char，Boolean
 */
const val MAX_EXPERIENCE: Int = 5

fun main(args: Array<String>) {
    /**
     *
     * 声明只读变量：val关键字,只要有必要，建议都是用val关键字
     *
     * Kotlin的内置数据类型：
     * String,Char,Boolean,Int,Double,List,Set,Map
     *
     * 类型推断：String和Int都是灰色的，是冗余的。对于已声明并赋值的变量，允许省略类型定义
     */
    val playName = "Blend"
    var experiencePoints = 5
    experiencePoints += 5
    println(experiencePoints)
    println(playName)

    /**
     * 在java中，所有的基本数据类型，都有引用类型。
     * 但是在Kotlin中，只提供引用类型这一种数据类型。-->为了性能，Kotlin编译器会在java字节码中改用基本数据类型。
     */

}