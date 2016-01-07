package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.screen.Play

object PhysicsS: EntitySystem(-999) {
    override fun update(deltaTime: Float) {
        Play.world.step(deltaTime, 5, 5)
    }
}