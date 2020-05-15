package com.blend.basic.NyetHack

import java.util.*


/**
 * 接口，可以定义属性和函数
 */
interface Fightable {

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
    override fun attack(opponent: Fightable): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty-looking goblin",
    healthPoints: Int = 30
) : Monster(
    name, description, healthPoints
) {

    override val diceCount: Int = 2
    override val diceSides: Int = 8

}