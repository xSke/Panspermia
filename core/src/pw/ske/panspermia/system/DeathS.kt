package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.event.Events

object DeathS: EntitySystem() {
    init {
        Events.Death.add(1) { deathE ->
            Play.engine.removeEntity(deathE.entity)
            false
        }
    }
}