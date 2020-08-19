package com.blend.basic.NyetHack

/**
 * 可见性修饰符：
 * 1.public(默认情况下)
 * 2.private（类）
 * 3.protected（类或者子类）
 * 4.internal（同一模块，这里我理解的是同一个module）
 *
 *
 * 针对每一个属性,属性属于类级别的，Kotlin会产生一个field,一个getter，一个setter.
 * 使用.语法，会调用属性的getter方法，使用=赋值运算符，会调用属性的setter方法
 *
 * 在Kotlin中，临时变量——包括仅仅用一次的参数——通常都会以下划线开头的名字命名,对于使用默认getter和setter方法的属性，
 *
 * 在主构造函数中，Kotlin允许你不使用临时变量赋值，而是直接用一个定义同时指定参数和类属性
 *
 * Player类初始化顺序：
 * 1.主构造函数里声明的属性
 * 2.类级别的属性赋值 和 init初始化块中的属性赋值和函数调用（取决于定义顺序）
 * 3.次构造函数里的属性赋值和函数调用
 *
 * 为什么次构造函数最后被初始化呢？
 * 是因为根据反编译的java代码，肯定先通过this()调用主构造函数，下面才执行次构造函数的代码，
 * 反编译后的java代码，主构造函数和类级别的属性，init初始化块中的属性，都是在this()构造函数中赋值的。
 */
class Player(
    _name: String,
    override var healthPoints: Int = 100,
    val isBlessed: Boolean,
    private val isImmortal: Boolean
) : Fightable {

    var name: String = _name
        get() = "${field.capitalize()} of $hometown"  //自定义getter
        private set(value) {    //只想暴露属性，不想暴露它的setter方法
            field = value.trim()
        }

    override val diceCount: Int = 3

    override val diceSides: Int = 6

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }

    /**
     * 属性必须初始化
     *
     * 但是并不适用于函数这样作用域较小的变量
     */
    private val hometown: String = "Chain"

    /**
     * 调用数据类
     */
    var currentPosition = Coordinate(0, 0)

    /**
     *延迟初始化，这个非常有用，但是要谨慎，需要确保在引用之前能初始化变量。
     */
    lateinit var alignment: String


    /**
     * 惰性初始化：暂时不初始化某个变量，直到首次使用它。在Kotlin中使用一种代理的机制来实现的，负责约定属性该如何初始化。
     */
    val lazyHomeTown = lazy {}

    /**
     * 次构造函数：只是多了一个构造类实例的方式，主构造函数的要求仍要满足。次构造函数要么直接调用主构造函数，要么通过
     * 另外一个次构造函数调用主构造函数，但次构造函数不能像主构造函数那样定义属性。
     * 类属性必须定义在主构造函数中，或者定义在类层级。
     */
    constructor(name: String) : this(
        name,
        isBlessed = true,
        isImmortal = false
    ) {
        if (name.toLowerCase() == "kar") {
            healthPoints = 40
        }
    }

    /**
     *初始化块，设置变量和值，执行有效的检查
     */
    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero" }
        require(name.isNotBlank()) { "Player nust have a name" }
    }

    fun auraColor(): String {
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        return if (auraVisible) "GREEN" else "NONE"
    }

    fun formatHealthStatus() =
        when (healthPoints) {
            100 -> "is in excellent condition!"
            in 90..99 -> "has a few scratches."
            in 75..89 -> if (isBlessed) {
                "has some minor wounds, but is healing quite quickly!"
            } else {
                "has some minor wounds."
            }
            in 15..74 -> "looks pretty hurt."
            else -> "is in awful condition!"
        }


    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball Springs into existence.($numFireballs)")
}