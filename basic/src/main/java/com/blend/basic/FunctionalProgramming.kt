package com.blend.basic


/**
 * 函数式编程：主要依赖于高阶函数（以函数为参数或者返回函数）返回的数据
 *
 * 函数式应用通常由三大类函数组成：变换、过滤和合并，每类函数都针对集合数据类型设计，目标是产生一个最终结果,但是原始的
 * 数据是不回变化的
 */

/**
 * 变换：遍历集合内容，用一个以值参形式传入的变换器函数变换每一个元素，然后返回包含一修改元素的集合给链上的其他函数
 */
fun transformTest() {

    val animals = listOf<String>("zebra", "giraffe", "elephant", "rat")
    val babies = animals.map { "A baby $it" }.map { it.length } //[12, 14, 15, 10]
    println(babies)
    println(animals)    //数据没有改变
}


/**
 * 过滤：接受一个predicate函数，用给定的条件检查接收者集合里的元素并给出true或者false的判定
 * 如果predicate函数返回结果为true，受检查的元素会添加到过滤函数返回的新集合里，为false，则被移除新集合。
 */
fun filterTest() {

    val itemsOfManyColors =
        listOf(
            listOf("red apple", "green apple", "blue apple"), listOf("red fish", "blue fish"),
            listOf("yellow banana", "teal banana")
        )

    val readItems = itemsOfManyColors.flatMap { it.filter { it.contains("red") } }
    println(readItems)


    /*
     *找素数，filter函数的predicate条件是map函数的返回结果。
     * 针对numbers集合里的每个元素，map函数会用它除以2到它本身之间的每个数并返回余数。如果返回的余数都不为0，none函数返回true，
     * 则filter函数的predicate条件检查就是true，就是被找到的素数。
     */
    val numbers = listOf<Int>(7, 4, 8, 4, 3, 22, 18, 11)
    val primes = numbers.filter { number -> (2 until number).map { number % it }.none { it == 0 } }
    println(primes)

}

/**
 * 合并：将不同的集合合并成一个新集合
 */
fun combineTest() {

    val employees = listOf<String>("Denny", "Claudette", "Peter")
    val shirtSize = listOf<String>("Large", "x-large", "medium")

    val employeeShirtSizes = employees.zip(shirtSize).toMap()
    println(employeeShirtSizes)


    val foldedValue = listOf<Int>(1, 2, 3, 4).fold(2) { accumulator, number ->
        println("Accumulated value:$accumulator")
        accumulator + number
    }
    println("Final value:$foldedValue")

}

fun main(args: Array<String>) {

    transformTest()

    filterTest()

    combineTest()


    /**
     * 高阶函数：
     *
     * map:统一变换处理,将List中每个元素转换成新的元素,并添加到一个新的List中,最后将新List返回
     *
     * flatMap:根据实参给定的函数对集合中的每一个元素做变换，然后把多个列表合并成一个列表
     *
     * filter:将List中的元素遍历,把符合要求的元素添加到新的list中,并将新list返回
     *
     * none:如果集合中没有元素，则返回true，否则返回false
     * none(predicate):如果集合中没有符合匹配条件的元素，返回true，否则返回false
     *
     * fold:给定一个初始值,对集合的元素按照某个逻辑进行一一累计
     *
     * zip:合并函数来合并两个集合，返回的是一个包含键值对的新集合，调用toMap，将键值对转换为map集合
     *
     *
     * 惰性集合类型：序列
     */

}
