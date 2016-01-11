package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.event.Events

object DeathS: EntitySystem() {
    init {
        Events.Death.register(10) { deathE ->
            Play.engine.removeEntity(deathE.entity)
            false
        }
    }
}