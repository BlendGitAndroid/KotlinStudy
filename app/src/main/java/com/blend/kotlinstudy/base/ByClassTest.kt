package com.blend.kotlinstudy.base


/**
 *
 * by的委托机制,这里和dart的mixin有点像
 *
 * Kotlin的`by`机制和Dart的mixin机制都是用于在类中实现代码重用的特性，但它们有一些不同之处。
 *
 * Kotlin的`by`机制是通过委托实现代码重用。在Kotlin中，类可以通过实现接口或委托给其他对象来实现代码的复用。通过使用`by`关键字，
 * 我们可以将具体的实现委托给另一个对象，并在主类中调用该对象的方法。这种委托机制可以用于实现单一继承和复合的效果，同时还可以实现
 * 属性的委托。通过`by`机制，我们可以将一些通用的功能从类中提取出来，并将其委托给其他类来处理，以减少重复代码。
 *
 * Dart的mixin机制则是通过多继承实现代码重用。在Dart中，一个类可以使用`with`关键字引入一个或多个mixin，从而继承其方法和属性。
 * Mixin是一个类，它包含了一组能够被其他类复用的方法和属性。通过使用mixin，我们可以在不使用继承的情况下，将可复用的功能注入到
 * 一个或多个类中。Dart的mixin机制可以用于在类中添加特定的行为，而无需通过继承来扩展类的功能。
 *
 * 总的来说，Kotlin的`by`机制是通过委托实现代码重用，而Dart的mixin机制则是通过多继承实现代码重用。它们都提供了一种灵活的方式来
 * 组合和复用代码，使得我们可以更好地设计和组织类的功能。
 *
 * 定义人类
 */
open class People(val name: String) {
    fun eat() {
        println("$name  chifan ")
    }

    fun sleep() {
        println("$name  sleep")
    }
}

/**
 * 定义程序员接口
 */

interface ICode {
    fun codeing()
}

/**
 * 可以写代码技能
 */
class Code(val name: String) : ICode {
    override fun codeing() {
        println("$name  写代码")
    }
}


/**
 * 可以看到,我通过接口+by 关键词 来进行了一次委托
 *
 * 这个委托，by后面的就是一个对象Code(name)，就是一个对象
 */
class CodePeople(name: String) : People(name), ICode by Code(name)

//类委托的本质是静态代理
fun main() {

    /**
     * 创建 CodePeople 对象
     */
    val codePeople = CodePeople("阿文")

    /**
     * 调用吃饭睡觉方法
     */
    codePeople.eat()
    codePeople.sleep()

    /**
     * 调用委托过来的方法
     */
    codePeople.codeing()

    //类委托必须是一个接口
    val test = object : ICode by Code("bke") {

    }
    test.codeing()

}

