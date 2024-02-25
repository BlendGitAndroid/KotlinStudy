package com.blend.basic.NyetHack

import java.util.Random


/**
 *
 * Kotlin的接口,可以定义属性
 * 接口，可以定义一组未实现的公共属性和行为。
 * 类之间想要共享属性或函数，但又无法建立继承关系时，就非常适合使用这种只定义而不实现的接口特性。
 * 也就是说，通过接口，无须继承或被继承，一组类就可以拥有共同的属性和函数。
 *
 * 抽象类很特殊，兼有接口和一般类的某些特性。
 */
interface Fightable {


    /**
     * 接口只会定义要干什么，不管具体怎么实现。
     * 所以这四个属性没有构造函数等初始化工具，attack函数没有函数体
     */
    var healthPoints: Int
    val diceCount: Int


    val diceSides: Int

    /**
     * 在接口里提供默认属性的getter方法和函数实现，然后实现接口的类自己决定是使用默认实现还是定义自己的实现版本
     */
    val damageRoll: Int
        get() = (0 until diceCount).map { Random().nextInt(diceSides) + 1 }.sum()


    fun attack(opponent: Fightable): Int

}

abstract class Monster(val name: String, val description: String, override var healthPoints: Int) :
    Fightable {

    fun getTest(): String {
        return description
    }

    override fun attack(opponent: Fightable): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}

/**
 * 抽象类和接口的区别：
 * 1)接口里不能定义构造函数。
 * 2)一个类只能继承一个抽象类，但可以实现多个接口。
 */
class Goblin(
    name: String,
    description: String = "A nasty-looking goblin",
    healthPoints: Int = 30,
) : Monster(
    name, description, healthPoints
) {

    override val diceCount: Int = 2
    override val diceSides: Int = 8

    fun test2() {
        ::test.invoke(5)
    }


    fun test(n: Int) {
        val b: String.() -> Unit = String::sss
        val c: (String) -> Unit = String::sss
    }

}

fun String.sss() {
    println("sss: $this")
}

class AAA<T : Math> {

}
