package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.KillOnTouchC
import pw.ske.panspermia.event.DeathE
import pw.ske.panspermia.event.Events

object KillOnTouchS : EntitySystem() {
    val toKill = arrayListOf<Entity>()

    init {
        Events.EntityTouchFixture.add { signal, entityTouchFixtureE ->
            val kot = entityTouchFixtureE.entity.getComponent(KillOnTouchC::class.java)
            if (kot != null) {
                if (kot.destroyIfNonPlayer && entityTouchFixtureE.fixture.body.userData !is Entity) {
                    Play.engine.removeEntity(entityTouchFixtureE.entity)
                } else {
                    toKill.add(entityTouchFixtureE.entity)
                }
            }
        }
    }

    override fun update(deltaTime: Float) {
        toKill.forEach {
            Events.Death.dispatch(DeathE(it))
        }
        toKill.clear()
    }
}