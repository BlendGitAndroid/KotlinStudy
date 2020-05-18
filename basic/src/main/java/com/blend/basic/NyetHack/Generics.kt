package com.blend.basic.NyetHack


/**
 * 定义泛型类
 *
 * 泛型类的构造函数可以接受任意Loot类型
 *
 *
 *LootBox能存放任何类型的Loot实例，但一次只能存放一个。如果往LootBox里面存放多个呢，使用vararg关键字，接受多个值参。
 */
class LootBox<T : Loot>(vararg item: T) {
    var open = false

    //out关键字必须有，只有要vararg关键字。注意这里接收的时候，是一个Array数组
    private var loot: Array<out T> = item

    /**
     * 泛型函数
     */
    fun fetch(item: Int): T? {
        return loot[item].takeIf { open }
    }

    /**
     * 重写get运算符，就能在vararg定义的参数中使用get运算符函数
     */
    operator fun get(index: Int): T? = loot[index].takeIf { open }

    /**
     * 多泛型参数
     *
     * 参数是一个奖品修改函数：接受T类型的值参，返回R类型的结果值
     */
    fun <R> fetch(item: Int, lootModFunction: (T) -> R): R? {
        return lootModFunction(loot[item]).takeIf { open }
    }
}

/**
 * 泛型约束
 */
open class Loot(val value: Int)

class Fedora(val name: String, value: Int) : Loot(value)

class Coin(value: Int) : Loot(value)


fun main(args: Array<String>) {
    val lootBoxOne: LootBox<Fedora> =
        LootBox(Fedora("a generic-looking fedora", 15), Fedora("a dazzling magenta fedora", 15))
    val lootBoxTwo: LootBox<Coin> = LootBox(Coin(15))


    lootBoxOne.open = true
    lootBoxOne.fetch(1)?.run {
        println("you retrieve $name from the box！")
    }

    val dedora = lootBoxOne[1]
    dedora?.let { println(it.name) }

    //在这里，这里的it指的就是上面函数调用时的loot
    val coin = lootBoxOne.fetch(0) {
        Coin(it.value * 3)
    }

    coin?.let { println(it.value) }
}