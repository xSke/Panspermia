package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.component.DamageOnTouchC
import pw.ske.panspermia.component.HealthC
import pw.ske.panspermia.event.DamageE
import pw.ske.panspermia.event.Events

object DamageOnTouchS: EntitySystem() {
    var toKill = arrayListOf<Entity>()

    init {
        Events.EntityTouchFixture.register { entityTouchFixtureE ->
            val dot = entityTouchFixtureE.entity.getComponent(DamageOnTouchC::class.java)
            if (dot != null) {
                if (entityTouchFixtureE.fixture.body.userData is Entity) {
                    val other = entityTouchFixtureE.fixture.body.userData as Entity
                    if (other.getComponent(HealthC::class.java) != null) {
                        Events.Damage.dispatch(DamageE(other, dot.damage, entityTouchFixtureE.entity))

                        if (dot.killThis) {
                            toKill.add(entityTouchFixtureE.entity)
                        }
                    }
                }
            }
            false
        }
    }

    override fun update(deltaTime: Float) {
        toKill.forEach {
            Play.engine.removeEntity(it)
        }
        toKill.clear()
    }
}