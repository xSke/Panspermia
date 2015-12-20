package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.HealthC

object KillOnHealthZeroS: IteratingSystem(Family.all(HealthC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity.getComponent(HealthC::class.java).health <= 0) {
            Play.engine.removeEntity(entity)
        }
    }
}