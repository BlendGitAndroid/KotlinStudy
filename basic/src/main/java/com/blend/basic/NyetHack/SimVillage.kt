package com.blend.basic.NyetHack


/**
 * 匿名函数：定义时不取名字的函数，通常整体传递给其他函数，或者从其他函数返回。
 *
 * 定义函数就是把表达式或语句放在一对花括号里，最后的()表示调用匿名函数。
 *
 * 函数类型：匿名函数的类型(function type)，匿名函数可以当做变量赋值给函数类型变量
 */
fun main(args: Array<String>) {

    println({
        val currentYear = 2018
        "Welcome to SimVillage,Blend!(copyright $currentYear)"
    }())

    /**
     * :() -> String:表示变量存储的是哪种类型的函数,以箭头分隔符隔开（一对圆括号里面的函数参数和紧跟着的返回类型参数）
     *
     * 任何不需要参数，能返回String的函数，都可以赋值给greetingFunction
     *
     * 注意到没有，没有return关键字，匿名函数不需要return关键字返回数据，而是会隐式或者自动返回函数体最后一行语句，
     * 不用return的原因是，编译器不知道返回数据究竟是来自于调用匿名函数的函数，还是匿名函数本身。
     */
    val greetingFunction: () -> String = {
        val currentYear = 2018
        "Welcome to SimVillage,Blend!(copyright $currentYear)"
    }
    println(greetingFunction())

    /**
     *函数参数：在匿名函数体内，左花括号的后面，写上String类型的参数名，后面再跟上一个箭头符号
     */
    val greetingFunctionPara: (String) -> String = { playerName ->
        val currentYear = 2018
        "Welcome to SimVillage,$playerName!(copyright $currentYear)"
    }
    println(greetingFunctionPara("Jiang Zuo Xiao Hai"))

    /**
     *it关键字：定义有且只有一个参数的匿名函数时，可以使用it关键字来表示参数名
     */
    val greetingFunctionIt: (String) -> String = {
        val currentYear = 2018
        "Welcome to SimVillage,$it!(copyright $currentYear)"
    }
    println(greetingFunctionIt("Jiang Zuo It"))

    /**
     * 多个参数
     */
    val greetingFunctionMorePara: (String, Int) -> String = { playerName, numBuildings ->
        val currentYear = 2018
        println("Adding $numBuildings houses")
        "Welcome to SimVillage,$playerName!(copyright $currentYear)"
    }
    println(greetingFunctionMorePara("Jiang zuo", 5))

    /**
     * 类型推断：定义变量时，所以已把匿名函数作为变量赋值给它，就不需要显式指明变量类型了
     *
     * 为了帮助编译器更准确的推断变量类型，匿名函数的参数名和参数类型必须有
     */
    val greetingFunctionInfer = { playerName: String, numBuildings: Int ->
        val currentYear = 2018
        println("Adding $numBuildings houses")
        "Welcome to SimVillage,$playerName!(copyright $currentYear)"
    }
    println(greetingFunctionInfer("Jiang zuo", 5))

    /**
     * 分清楚函数调用和函数定义
     *
     * 定义参数是函数的函数：函数支持包括函数在内的任何类型的参数
     * 定义：在函数名后的一对圆括号内列出，再加上类型。
     *
     * 这里省略了圆括号
     *
     * 匿名函数：Lambda
     * 定义：Lambda表达式
     * 返回数据：Lambda结果
     */
    runSimulation("xiaohai", ::printConstructionCost) { playerName, numBuilding ->
        val currentYear = 2018
        println("Adding $numBuilding houses")
        "Welcome to SimVillage,$playerName!(copyright $currentYear)"
    }

    /**
     * 函数内联：使用inline，将Lambda表达式的函数体复制到函数运行的地方
     *
     * 内联出现原因：定义的Lambda会以对象实例的形式存在，JVM会为所有同Lambda打交道的变量分配内存，这就产生了内存开销，带来严重的性能问题
     *
     */

    /**
     *
     * 前面的列子中，要把函数作为参数传递给其他函数使用，先定义一个Lambda，然后把它作为参数传递给另外一个函数使用。
     * 除了Lambda表达式，Kotlin还提供了函数引用。
     *
     * 函数引用：可以把一个具名函数（使用fun关键字定义的函数）转换成一个值参。凡是使用Lambda的地方，都可以使用函数引用。
     * 要想获得函数引用，使用::操作符，后要跟引用的函数名
     *
     * 函数引用主要是将具名函数作为值参传递给其他函数，弥补了Lambda的不足
     */

    /**
     * 函数类型作为返回类型：定义一个能返回函数的类型
     */
    runSimulation()

    /**
     * Kotlin中的Lambda就是闭包
     *
     * 匿名函数能修改并引用定义在自己的作用域之外的变量
     *
     * 可以看到，println(greetingFunction("Blend runSimulation"))的调用方式，building的值增加了
     *
     * 但是println(configureGreetingFunction()("Blend runSimulation"))的却还是原来的值
     */
    runSimulation()


    //TODO("不知道原因")
    fun runMyRunnable(runnabl: () -> Unit) = { runnabl() }
    runMyRunnable { println("Hi Blend") }

    /*-----------------------以下是各种用法---------------------------------*/

    /**
     * 注：这里的函数后面省略了圆括号
     *
     * 如果一个函数的lambda参数排在最后，或者是唯一的函数，那么括住Lambda值参的一对圆括号就可以省略
     */
    val numLetters = "Mississippi".count { letter ->
        letter == 's'
    }
    println(numLetters)


    println("Mississippi".count { it == 's' })

}

/**
 * 函数定义时，建议把函数类型的参数放在最后，方便函数调用者使用。
 */
inline fun runSimulation(
    playerName: String,
    costPrinter: (Int) -> Unit,
    greetingFunction: (String, Int) -> String
) {
    val numBuildings = (1..3).random()
    costPrinter(numBuildings)
    println(greetingFunction(playerName, numBuildings))
}

fun printConstructionCost(numBuildings: Int) {
    val cost = 500
    println("construct cost:${numBuildings * cost}")
}

fun runSimulation() {
    val greetingFunction = configureGreetingFunction()
    println(greetingFunction("Blend runSimulation"))
    println(greetingFunction("Blend runSimulation"))
    //同样也能写成下面这样的，但是下面这个函数是匿名的
    println(configureGreetingFunction()("Blend runSimulation"))

}

fun configureGreetingFunction(): (String) -> String {
    val structureType = "hospitals"
    var numBuildings = 5
    return {
        val currentYear = 2018
        numBuildings += 1
        println("Adding $numBuildings $structureType")
        "Welcome to SimVillage,$it!(copyright $currentYear)"
    }
}