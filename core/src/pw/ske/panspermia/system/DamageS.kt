package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.HealthC
import pw.ske.panspermia.event.Events

object DamageS: EntitySystem() {
    init {
        Events.Damage.add { signal, damageE ->
            val d = damageE.entity.getComponent(HealthC::class.java)
            if (d != null) {
                d.health -= damageE.damage
            }
        }
    }
}