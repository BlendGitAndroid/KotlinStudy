package com.blend.basic.NyetHack


/**
 * 类默认是封闭的，不允许其他类来继承自己。
 * 要让类开放，必须使用open关键字来修饰它
 */
open class Room(val name: String) {

    protected open val dangerLevel = 5

    var monster: Monster? = Goblin()

    fun description() =
        "Room:$name Danger level:$dangerLevel + Creature:${monster?.description ?: "none."}"

    open fun load() = "Nothing much to see here..."
}

open class TownSquare : Room("Town Square") {

    //子类复写了父类的属性
    override val dangerLevel: Int = super.dangerLevel - 3
    private var bellSound = "GWONG"

    override fun load(): String = "The villagers rally and cheer as you enter!"

    private fun ringBell() = "The bell tower announces your arrival.$bellSound"
}

class House : TownSquare() {

    //能覆写的方法默认是open的，除非其父类加上final
    override fun load(): String {
        return "House"
    }

    override fun toString(): String {
        return "this is House"
    }

}

fun main(args: Array<String>) {

    var currentRoom: Room = House()
    println(currentRoom.description())
    println(currentRoom.load())

    /**
     *类型检测：is
     */
    println(currentRoom is Room)

    /**
     * 类型转换：as（要是不兼容会出现问题）
     *
     * 智能类型转换：只要is成功，就能调用
     */

}