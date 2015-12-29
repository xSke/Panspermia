package pw.ske.panspermia

import pw.ske.panspermia.stat.Stat

object GameState {
    var dna = 0

    val fireRate = Stat("Fire Rate", 1, 5, listOf(0, 100, 200, 500, 1000), listOf(0f, 20f, 30f, 40f, 50f, 60f))
    val projectileSpeed = Stat("Projectile Speed", 1, 5, listOf(0, 100, 200, 500, 1000), listOf(0f, 15f, 20f, 25f, 30f, 35f))
    val health = Stat("Health", 1, 5, listOf(0, 100, 200, 500, 1000), listOf(0f, 10f, 15f, 20f, 25f, 30f))
    val projectileCount = Stat("Projectile Count", 1, 3, listOf(0, 500, 2000), listOf(0, 1, 3, 5))
    val shield = Stat("Shield", 0, 3, listOf(1000, 2000, 5000), listOf(0, 1, 3, 5))

    val stats = listOf(fireRate, projectileSpeed, health, projectileCount, shield)
}