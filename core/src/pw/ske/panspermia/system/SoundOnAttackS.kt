package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.SoundOnAttackC
import pw.ske.panspermia.component.SoundOnDamageC
import pw.ske.panspermia.event.Events

object SoundOnAttackS: EntitySystem() {
    init {
        Events.Attack.add { signal, attackE ->
            val soa = attackE.entity.getComponent(SoundOnAttackC::class.java)
            if (soa != null) {
                soa.sound.play()
            }
        }
    }
}