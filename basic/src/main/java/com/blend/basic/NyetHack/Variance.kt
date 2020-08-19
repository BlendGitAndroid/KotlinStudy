package com.blend.basic.NyetHack


/**
 *为了进一步定制化类型参数，Kotlin提供了in和out两个关键字，进行智能类型转换
 *
 * 这就类似于java中的通配符
 *
 * out:可读而不可写的生产者角色（协变）
 * in:可写而不可读的消费者角色（逆变）
 */
class Barrel<out T>(val item: T)


fun main(args: Array<String>) {

    var fedoraBarrel: Barrel<Fedora> = Barrel(Fedora("a generic-looking fedora", 15))

    var lootBarrel: Barrel<Loot> = Barrel(Coin(15))

    /**
     *既然泛型参数是生产者，item变量值就不能变，这样就可以安全的把fedoraBarrel赋值给lootBarrel，因为lootBarrel.item现在
     * 已经表示Fedora了，且不能改变
     *
     * Fedora和Coin都是Loot类的子类，但是两者并没有关系，只是他们的父类都有item属性
     */
    lootBarrel = fedoraBarrel

    val myFedora: Fedora = lootBarrel.item  //这里发生了智能类型转换

}