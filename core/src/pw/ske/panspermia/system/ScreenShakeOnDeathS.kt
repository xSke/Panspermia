package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.ScreenShakeOnDeathC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.ScreenShakeE

object ScreenShakeOnDeathS: EntitySystem() {
    init {
        Events.Death.add { signal, deathE ->
            val ssod = deathE.entity.getComponent(ScreenShakeOnDeathC::class.java)
            if (ssod != null) {
                Events.ScreenShake.dispatch(ScreenShakeE(ssod.strength, ssod.time, ssod.fade))
            }
        }
    }
}