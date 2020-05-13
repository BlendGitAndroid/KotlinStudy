package com.blend.basic.NyetHack


fun main(args: Array<String>) {

    val player = Player("blend", 65, isBlessed = true, isImmortal = false)
//    val player = Player("blend", isBlessed = true, isImmortal = false)
//    val player = Player("blend")
    player.castFireball()

    // Player status
    printPlayerStatus(player)

}

private fun printPlayerStatus(player: Player) {
    println(
        "(Aura: ${player.auraColor()}) " +
                "(Blessed: ${if (player.isBlessed) "YES" else "NO"})"
    )
    println("${player.name} ${player.formatHealthStatus()}")
}