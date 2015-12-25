package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.SoundOnDamageC
import pw.ske.panspermia.event.Events

object SoundOnDamageS: EntitySystem() {
    init {
        Events.Damage.add { signal, damageE ->
            val sod = damageE.entity.getComponent(SoundOnDamageC::class.java)
            if (sod != null) {
                sod.sound.play()
            }
        }
    }
}