package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.component.KillIfDiesC
import pw.ske.panspermia.event.DeathE
import pw.ske.panspermia.event.Events

object KillIfDiesS: IteratingSystem(Family.all(KillIfDiesC::class.java).get()) {
    val deaths = arrayListOf<Entity>()
    init {
        Events.Death.register {
            deaths.add(it.entity)
            false
        }
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val kid = entity.getComponent(KillIfDiesC::class.java)

        if (kid.entity in deaths) {
            Events.Death.dispatch(DeathE(entity))
        }
    }
}