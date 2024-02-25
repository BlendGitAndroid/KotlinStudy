package com.blend.basic.NyetHack


/**
 * 数据类，使用data关键字，默认重写了toString和equals函数
 *
 * 经常需要比较，复制或者打印自身内容的类，数据类比较合适（必须有主构造函数、不能用abstract，open，sealed，inner修饰）
 *
 * toString():默认提供主构造函数里面声明的属性（Coordinate(x=1, y=1)）
 *
 * equals():允许基于主构造函数中的属性来判断相等性
 *
 * copy():复制一个对象
 */

// constructor可以省略
data class Coordinate(val x: Int, val y: Int) {
    val isInBound = x >= 0 && y >= 0

    //运算符重载(plus和+)
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}

/**
 * 枚举类：定义常量集合的一种特殊类
 */
enum class Direction(private val coordinate: Coordinate) {
    NORTH(Coordinate(0, -1)),
    EAST(Coordinate(1, 0)),
    SOUTH(Coordinate(0, 1)),
    WEST(Coordinate(-1, 0));

    fun updateCoordinate(playerCoordinate: Coordinate) = playerCoordinate + coordinate
}

/**
 * 运算符重载：
 * +   ----- plus
 * +=  ----- plusAssign
 * ==  ----- equals
 * >   ----- compareTo：左边大于右边，返回true
 * []  ----- get
 * ..  ----- rangeTo
 * in  ----- contains
 */

fun main(args: Array<String>) {

    println(Coordinate(1, 1))
    println(Coordinate(1, 1) == Coordinate(1, 1))
    println(Direction.EAST.updateCoordinate(Coordinate(1, 0)))

}