package com.blend.kotlinstudy


/**
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

