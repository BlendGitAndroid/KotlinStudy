package com.blend.basic.NyetHack


fun main(args: Array<String>) {

    //要调用定义在对象声明里的函数，使用定义的对象名就可以了
    GameMain.play()

    //对象表达式
    val abandonedTownSquare = object : TownSquare() {
        override fun load(): String = "this is a object express"
    }

}

/**
 * object关键字：定义一个只能产生一个实例的类——单例
 *
 * object关键字三种方式：
 * 1.对象声明（object declaration）
 *
 * 2.对象表达式（object expression）:只会用一次，用完就丢弃,连类名都没有
 *
 * 3.伴生对象（companion object）：将某个对象的初始化和一个类实例捆绑在一起，一个类只有一个伴生对象
 *  companion object来定义
 *  初始化时机：包含伴生对象的类初始化时，用来存放和类定义有上下文关系的单例数据，或者直接访问伴生对象的某个属性或者函数
 */
object GameMain {

    private val player = Player("blend", 65, isBlessed = true, isImmortal = false)
//    val player = Player("blend", isBlessed = true, isImmortal = false)
//    val player = Player("blend")

    init {
        println("Welcome,adventure.")
        player.castFireball()

        // Player status
        printPlayerStatus(player)
    }

    fun play() {

        while (true) {
            print("> Enter your command:")
            println(GameInput(readLine()).processCommand())
            break
        }
    }

    private fun printPlayerStatus(player: Player) {
        println(
            "(Aura: ${player.auraColor()}) " +
                    "(Blessed: ${if (player.isBlessed) "YES" else "NO"})"
        )
        println("${player.name} ${player.formatHealthStatus()}")
    }

    /**
     * 嵌套类
     */
    private class GameInput(arg: String?) {

        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.toLowerCase()) {
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }

}

