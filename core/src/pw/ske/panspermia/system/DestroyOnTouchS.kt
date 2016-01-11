package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.component.DestroyOnTouchC
import pw.ske.panspermia.event.DeathE
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.util.Category

object DestroyOnTouchS : EntitySystem() {
    val toKill = arrayListOf<Entity>()

    init {
        Events.EntityTouchFixture.register { entityTouchFixtureE ->
            val kot = entityTouchFixtureE.entity.getComponent(DestroyOnTouchC::class.java)
            if (kot != null) {
                if (Category.matches(Category.getCategory(entityTouchFixtureE.fixture), kot.destroyFilter)) {
                    Play.engine.removeEntity(entityTouchFixtureE.entity)
                } else {
                    toKill.add(entityTouchFixtureE.entity)
                }
            }
            false
        }
    }

    override fun update(deltaTime: Float) {
        toKill.forEach {
            Events.Death.dispatch(DeathE(it))
        }
        toKill.clear()
    }
}