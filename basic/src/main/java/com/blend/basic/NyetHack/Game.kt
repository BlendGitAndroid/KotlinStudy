package com.blend.basic.NyetHack


/**
 * 定义的方法都是文件级别的方法，在Java中对应的是类的静态方法，Java类名就是Kotlin函数定义所在文件的文件名
 */

fun main(args: Array<String>) {
    val name = "Blend"

    var healthPoints = 80

    val isBlessed = true

    val isImmortal = false

    val auraColor = auraColor(isBlessed, healthPoints, isImmortal)


    val healthState = formatHealthStatus(healthPoints, isBlessed)


    printPlayerStatus(auraColor, isBlessed, name, healthState)

    /**
     * 具名函数参数:可以不用管值参的顺序
     */
    printPlayerStatus(
        healthState = healthState,
        auraColor = "NONE",
        name = "Blend",
        isBlessed = false
    )

    castFireball()
}

private fun printPlayerStatus(
    auraColor: String,
    isBlessed: Boolean,
    name: String,
    healthState: String
) {
    println("(Blend: $auraColor)" + "(Blessed: ${if (isBlessed) "YES" else "NO"})")
    println("$name $healthState")
}

private fun auraColor(isBlessed: Boolean, healthPoints: Int, isImmortal: Boolean): String {
    val auraVisible = isBlessed && healthPoints > 50 || isImmortal

    return if (auraVisible) "GREEN" else "NONE"
}

/**
 *函数参数总是只读的，不能在函数体内再给他们赋值
 */
private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean): String {
    return when (healthPoints) {
        100 -> {
            "is in excellent condition!"
        }
        in 50..99 -> {
            if (isBlessed) {
                "has some minor wounds but is healing quite quickly!"
            } else {
                "has some minor wounds."
            }
        }
        in 15..49 -> {
            "looks pretty hurt."
        }
        else -> {
            "has some awful wounds."
        }
    }

}

/**
 * 默认值传参
 *
 * 单表达式函数：函数只有一个表达式语句，或者说只有一条求值语句，其返回类型、花括号、返回语句都可以省略掉
 *
 * formatHealthStatus这个函数也可以
 *
 * Unit返回类型：函数没有返回值，同时能兼容需要和一些类型打交道的泛型函数
 */
private fun castFireball(numFireballs: Int = 2) =
    println("A glass of Fireball springs into existence.(x$numFireballs)")

/**
 * Nothing:不返回任何东西，但也永远别指望它运行成功
 * 编译器认为：Nothing就意味着不可能成功执行完成，要么抛出异常，要么因某个原因再也不返回调用处。
 */
fun shouldReturnAString() {
    TODO("implement the string building functionality here to return a string.")
    print("unreachable") //这一行代码不会执行到
}

/**
 * 使用反引号，解决函数名命名冲突的问题
 * */