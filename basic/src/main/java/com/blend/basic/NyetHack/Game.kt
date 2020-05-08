package com.blend.basic.NyetHack


fun main(args: Array<String>) {
    val name = "Blend"

    var healthPoints = 80

    val isBlessed = true

    /**
     * 比较运算符中，注意的就是===：计算两个实例是否指向同一个引用
     */
    if (healthPoints == 100) {
        println("$name is in excellent condition!")
    } else if (healthPoints >= 50) {
        if (isBlessed) {
            println("$name has some minor wounds but is healing quite quickly!")
        } else {
            println("$name has some minor wounds.")
        }
    } else {
        println("$name is in awful condition!")
    }
}