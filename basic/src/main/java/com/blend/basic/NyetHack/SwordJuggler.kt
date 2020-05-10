package com.blend.basic.NyetHack

/**
 * Kotlin中的所有异常都是未检查异常
 */
fun main(args: Array<String>) {
    var swordJuggling: Int? = null  //代表可空
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordJuggling = 2
        //先决条件函数
        require(swordJuggling >= 3) { "Juggle at least 3 swords to be exciting." }
    }

    //若为null，抛出异常
    proficiencyCheck(swordJuggling)


    try {
        proficiencyCheck(swordJuggling)

        swordJuggling = swordJuggling?.plus(1)
        println("You juggle $swordJuggling swords!")

        swordJuggling = swordJuggling!!.plus(1)
        println("You juggle $swordJuggling swords!")
    } catch (e: Exception) {
        println(e)
    }
}

/*
    使用先决条件函数代替throw UnskilledSwordJugglerException
 */
fun proficiencyCheck(swordsJuggling: Int?) {
//    swordsJuggling ?: throw UnskilledSwordJugglerException()
    checkNotNull(swordsJuggling, { "player cannot juggle swords" })
}

class UnskilledSwordJugglerException() : IllegalStateException("player cannot juggle swords")