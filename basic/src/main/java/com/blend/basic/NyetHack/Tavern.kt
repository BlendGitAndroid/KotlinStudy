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

