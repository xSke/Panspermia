package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.ShieldC
import pw.ske.panspermia.event.Events

object ShieldS : IteratingSystem(Family.all(BodyC::class.java, ShieldC::class.java).get()) {
    init {
        Events.Damage.add(-1) {
            val s = it.entity.getComponent(ShieldC::class.java)

            if (s != null) {
                if (s.enabled) {
                    s.hitCounter += 1

                    if (it.source != null) {
                        Play.engine.removeEntity(it.source)
                    }

                    if (s.hitCounter == s.shieldHits - 1) {
                        s.hitCounter = 0
                        s.enabled = false
                        s.rechargeCounter = 0f
                    }
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        Events.Attack.add {
            val s = it.entity.getComponent(ShieldC::class.java)

            if (s != null) {
                s.rechargeCounter = 0f
            }
            false
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val s = entity.getComponent(ShieldC::class.java)

        if (!s.enabled) {
            s.rechargeCounter += deltaTime
            if (s.rechargeCounter > s.shieldRechargeTime) {
                s.enabled = true
                s.hitCounter = 0
            }
        }
    }
}