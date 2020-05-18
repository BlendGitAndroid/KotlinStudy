package com.blend.basic.NyetHack


/**
 *扩展可以在不直接修改类定义的情况下增加类功能：本质就是一个静态方法，扩展对象就是它的一个值参
 *
 * 扩展可以用于自定义类，并且和继承类似，扩展也能共享类行为
 *
 * 当无法接触某个类定义时，或者某个类没有使用open修饰符，无法继承时，扩展就是增加类功能的最好选择
 */

//和一般函数定义差不多，但是扩展函数需要定义接受功能扩展的接收者类型
fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

/**
 * 使用泛型扩展函数后，可以支持任意类型的接收者
 */
fun <T> T.easyPrint(): T {
    println(this)
    return this
}


/**
 * 扩展属性，必须自己定义get或者set函数
 */
val String.numVowels
    get() = count { "aeiou".contains(it) }


/**
 * 可空类扩展：在扩展函数体内解决可能出现的空值问题
 *
 * infix关键字适用于有单个参数的扩展和类函数，可以省略点操作符和一对括号
 */
infix fun String?.printWithDefault(default: String) = println(this ?: default)

fun main(args: Array<String>) {
    println("Blend has left the building".addEnthusiasm(3))

    "Blend has left the building".easyPrint().addEnthusiasm(2).easyPrint()

    53.easyPrint()

    "How many vowels?".numVowels.easyPrint()

    val nullableString: String? = null
    nullableString.printWithDefault("Default string")

}