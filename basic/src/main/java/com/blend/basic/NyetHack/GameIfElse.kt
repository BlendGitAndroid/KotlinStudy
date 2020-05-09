package com.blend.basic.NyetHack


fun main(args: Array<String>) {
    val name = "Blend"

    var healthPoints = 80

    val isBlessed = true

    val race = "gnome"

    val auraVisible = isBlessed && healthPoints > 50;

    val auraColor = if (auraVisible) "GREEN" else "NONE"

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

    /**
     *条件表达式
     */
    val healthState = if (healthPoints == 100) {
        "is in excellent condition!"
    } else if (healthPoints >= 50) {
        "has some minor wounds but is healing quite quickly!"
    } else {
        "has some minor wounds."
    }

    val healthSummary = if (healthPoints != 100) "Need healing!" else "Looking good."

    println("$name $healthState");
    println(healthSummary)

    /**
     * range:使用..表示某个范围，范围从..操作符左侧的值到..操作符右侧的一系列值。范围也可以是一系列字符
     *
     * 使用range，解决了else if条件语句排序的问题，现在各个分支顺序随便写，结果都一样
     */
    val healthStateUseIn = when (healthPoints) {
        100 -> {
            "is in excellent condition!"
        }
        in 50..99 -> {
            "has some minor wounds but is healing quite quickly!"
        }
        else -> {
            "has some minor wounds."
        }
    }
    println(healthStateUseIn)

    /**
     * when:只要代码包含else if语句，都建议改用When表达式
     * when的逻辑表现力更强，代码更简洁
     */
    val faction = when (race) {
        "dwarf" -> "Keepers of the Mines"
        "gnome" -> "Keepers of the Mines"
        "orc" -> "Free People of the Rolling Hills"
        "human" -> "Free People of the Rolling Hills"
        else -> "other race"
    }

    /**
     * String模板：使用美元$符号作为前缀。
     *
     * 还支持在字符串里计算表达式的值并插入结果，添加在${}中的任何表达式，都会作为字符串的一部分求值
     */

    println("(Blend: $auraColor)" + "(Blessed: ${if (isBlessed) "YES" else "NO"})")

}