package com.blend.basic.NyetHack


/**
 *扩展可以在不直接修改类定义的情况下增加类功能：本质就是一个静态方法，扩展对象就是它的一个值参
 *
 * 扩展可以用于自定义类，比如List、String以及Kotlin标准库里的其他类，并且和继承类似，扩展也能共享类行为
 *
 * 当无法接触某个类定义时，或者某个类没有使用open修饰符，无法继承时，扩展就是增加类功能的最好选择
 */

//和一般函数定义差不多，但是扩展函数需要定义接受功能扩展的接收者类型，this关键字指接收者实例，这里指String
fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

fun Any.easyPrintTest() = println(this)

/**
 * 要是想在addEnthusiasm函数之前和之后都打印"!"字符串，怎么办？
 * 首先，应该支持链式调用，但是普通链式调用不能满足需求，因此需要泛型扩展函数
 */

/**
 * 使用泛型扩展函数后，可以支持任意类型的接收者
 * 新的泛型扩展函数不仅可以支持任何类型的接收者，还保留了接收者的类型信息。
 * 使用扩展函数后，能够支持更多类型的接收者，适应范围更广了
 */
fun <T> T.easyPrint(): T {
    println(this)
    return this
}


/**
 * 还能给类定义扩展属性，必须自己定义get或者set函数
 */
val String.numVowels
    get() = count { "aeiou".contains(it) }


/**
 * 可空类扩展：在扩展函数体内解决可能出现的空值问题
 *
 * infix关键字适用于有单个参数的扩展和类函数，可以省略点操作符和一对括号
 */
infix fun String?.printWithDefault(default: String) = println(this ?: default)

/**
 * 带接受者的函数字面量
 *
 * 比如我们使用的高阶函数apply
 *
 * @kotlin.internal.InlineOnly
 * public inline fun <T> T.apply(block: T.() -> Unit): T {
 * contract {
 * callsInPlace(block, InvocationKind.EXACTLY_ONCE)
 * }
 * block()
 * return this
 * }
 *
 * block函数参数不仅是一个Lambda表达式，它还是个泛型类型的扩展T:T.() - >Unit，这就是自己定义的Lambda表达式能隐式访
 * 问接收者实例的属性和函数的奥秘。通过定义为一个扩展，lambda函数的接收者同时也是apply函数的接收者----允许在Lambda表达式
 * 里访问接收者实例的函数和属性。
 * block函数表示：
 */

fun main(args: Array<String>) {
    println("Blend has left the building".addEnthusiasm(3))

    "Blend has left the building".easyPrint().addEnthusiasm(2).easyPrint()

    53.easyPrint()

    "How many vowels?".numVowels.easyPrint()

    val nullableString: String? = null
    nullableString.printWithDefault("Default string")

}