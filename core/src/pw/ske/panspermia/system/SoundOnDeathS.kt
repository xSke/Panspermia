package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.SoundOnDamageC
import pw.ske.panspermia.component.SoundOnDeathC
import pw.ske.panspermia.event.Events

object SoundOnDeathS: EntitySystem() {
    init {
        Events.Death.add { signal, deathE ->
            val sod = deathE.entity.getComponent(SoundOnDeathC::class.java)
            if (sod != null) {
                sod.sound.play()
            }
        }
    }
}