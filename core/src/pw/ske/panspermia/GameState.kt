package pw.ske.panspermia

import pw.ske.panspermia.stat.Stat

object GameState {
    var dna = 0

    val fireRate = Stat("Fire Rate", 1, 5, listOf(0, 100, 200, 500, 1000), listOf(20, 30, 40, 50, 60))
    val projectileSpeed = Stat("Projectile Speed", 1, 5, listOf(0, 100, 200, 500, 1000), listOf(15f, 20f, 25f, 30f, 35f))

    val stats = listOf(fireRate, projectileSpeed)
}