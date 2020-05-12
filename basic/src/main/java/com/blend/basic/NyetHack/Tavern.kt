package com.blend.basic.NyetHack


const val TAVERN_NAME = "Taernyl's Folly"

fun main(args: Array<String>) {

    placeOrder("shandy,Dragon's Breath,5.91")

    /**
     * 使用forEach遍历字符
     */
    "Dragon's Breath".forEach { println("$it") }

    /**
     * 数值转换：toFloat,toDouble等
     *
     * 安全的数值转化：toFloatOrNull,toIntOrNull等
     */

    val gold: Int = "5.91111111".toIntOrNull() ?: 0
    println(gold)

    /**
     * Double类型的格式化
     *
     * roundToInt:四舍五入计算
     */
    println("format code:${"%.2f".format(5.91111111)}")

    /**
     * list集合
     */
    listFun()


    /**
     * set集合
     */
    setFun()

    /**
     * map集合
     */
    mapFun()

}

fun mapFun() {

    /**
     * mapOf()和mutableMapOf()两种创建类型
     *
     */
    val patrolGold = mapOf("Eli" to 10.5, "Blend" to 8.0, "Sophie" to 5.5)

    val patrolGoldPair = mapOf(Pair("Eli", 10.75), Pair("Blend", 8.0), Pair("Sophie", 5.5))

    //根据键读取相应的值
    print(patrolGold["Eli"])

    /**
     * Map 存取函数
     * 1.[]取值函数
     * 2.getValue
     * 3.getOrElse
     * 4.getOrDefault
     *
     * Map mutator函数
     * 1.=
     * 2.+=
     * 3.put
     * 4.putAll
     * 5.getOrPut
     * 6.remove
     * 7.-(删除指定运算符)
     * 8.-=(删除指定运算符)
     * 9.clear
     */

}

fun setFun() {
    /*
    与List集合的不同之处在于：
    1.Set集合的值是唯一的
    2.不支持基于索引的存取值函数，因为它里面的元素顺序不固定
     */

    val planets = setOf("Mercury", "Venus", "Earth")
    planets.elementAt(2)

    /**
     * 常用的mutator函数
     * 1.add
     * 2.addAll
     * 3.+=(添加元素运算符)
     * 4.-=（删除元素运算符）
     * 5.remove
     * 6.removeAll
     * 7.clear
     */

    /**
     * 使用toSet()和toList()进行集合转换
     */

    /**
     *
     * 数组类型：主要目的是支持和java互操作
     * 原则上不使用，除非与Java互操作
     *
     * 常见的数组类型及创建他们的函数
     * IntArray --- intArrayOf
     * DoubleArray --- doubleArrayOf
     * LongArray --- longArrayOf
     * ShortArray --- shortArrayOf
     * ByteArray --- byteArrayOf
     * FloatArray --- floatArrayOf
     * BooleanArray --- booleanArrayOf
     * Array --- arrayOf
     */
    listOf(1, 2, 3).toIntArray()    //转化为Int数组


}

fun listFun() {
    /**
    list越界取值：安全索引
     */
    //listOf函数不可变
    val patronList = listOf("Eli", "Mordoc", "Blend")

    //安全索引
    patronList.getOrElse(4) { "Unknown Patron else" }

    patronList.getOrNull(4) ?: "Unknown Patron null"

    if (patronList.contains("Blend")) {
        println("is OK")
    } else {
        print("is not Ok")
    }


    //可变列表
    val patronMutableList = mutableListOf("Eli", "Mordoc", "Blend")
    patronMutableList.add("blend")
    patronMutableList.add(0, "blendVip")
    patronMutableList.remove("mordoc")


    //可变列表和不可变列表之间的互换
    patronList.toMutableList()
    patronMutableList.toList()
    patronMutableList[0] = "BlendVip"

    /**
     * 常用的mutator函数
     * 1.[]=(元素设置运算符)
     * 2.add
     * 3.add(指定索引位置)
     * 4.addAll
     * 5.+=(添加元素运算符)
     * 6.-=（删除元素运算符）
     * 7.clear
     * 8.removeIf
     */


    /**
     * 遍历
     */
    for (patron in patronList) {
        println("Good evening,$patron")
    }

    patronList.forEach { println("Good evening,$it") }

    patronList.forEachIndexed { index, value -> println("Good evening,$index --- $value") }

    /**
     * 支持解构，能获取其前5个参数
     *
     * _:能过滤掉不想要的元素
     */
    val (gold, _, bronze) = patronList

}

fun placeOrder(menuData: String) {

    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')   //先找到'符号的位置
    //substring支持IntRange参数
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("Madrigal speaks with $tavernMaster about their order.")


    //split返回list集合，能使用解构来简化变量的赋值（只要是集合结果，就可以使用解构赋值）
    val (type, name, price) = menuData.split(",")
    val message = "Blend buys a $name ($type) for $price."
    println(message)


    /**
     * 在kotlin中，==表示解构比较，在Java中使用equals函数
     *
     * 在kotlin中，===表示引用比较，在java中使用==
     */
    val phrase = if (name == "Dragon's Breath") {
        "Blend exclaims:${toDragonSpeak("Ah，delicious $name!")}"
    } else {
        "Blend says:Thanks for the $name"
    }
    println(phrase)
}

/**
 * replace函数需要两个值参：
 * 第一个是正则表达式，用来决定要替换哪些字符
 * 第二个是匿名函数，用来确定如何替换正则表达式搜索到的字符
 */
private fun toDragonSpeak(phrase: String) =
    phrase.replace(Regex("[aeiou]")) {
        when (it.value) {
            "a" -> "4"
            "e" -> "3"
            "i" -> "1"
            "o" -> "0"
            "u" -> "|_|"
            else -> it.value
        }
    }

