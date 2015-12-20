package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.DestroyOnTouchC
import pw.ske.panspermia.event.Events

object DestroyOnTouchS: EntitySystem() {
    init {
        Events.EntityTouchFixture.add { signal, entityTouchFixtureE ->
            if (entityTouchFixtureE.entity.getComponent(DestroyOnTouchC::class.java) != null) {
                Play.engine.removeEntity(entityTouchFixtureE.entity)
            }
        }
    }
}