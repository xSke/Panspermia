package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.DestroyOnTouchC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.util.Category

object DestroyOnTouchS : EntitySystem() {
    init {
        Events.EntityTouchFixture.add { entityTouchFixtureE ->
            val kot = entityTouchFixtureE.entity.getComponent(DestroyOnTouchC::class.java)
            if (kot != null) {
                if (Category.matches(Category.getCategory(entityTouchFixtureE.fixture), kot.filter)) {
                    Play.engine.removeEntity(entityTouchFixtureE.entity)
                }
            }
            false
        }
    }
}