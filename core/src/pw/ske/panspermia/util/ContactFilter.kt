package pw.ske.panspermia.util

import com.badlogic.gdx.physics.box2d.ContactFilter
import com.badlogic.gdx.physics.box2d.Fixture

object ContactFilter: ContactFilter {
    override fun shouldCollide(fixtureA: Fixture, fixtureB: Fixture): Boolean {
        val a = Category.getCategory(fixtureA)
        val b = Category.getCategory(fixtureB)

        if (!(Category.collideMap[Pair(a, Category.Any)]?: true)) return false
        if (!(Category.collideMap[Pair(b, Category.Any)]?: true)) return false

        return Category.collideMap[Pair(a, b)] ?: true
    }
}