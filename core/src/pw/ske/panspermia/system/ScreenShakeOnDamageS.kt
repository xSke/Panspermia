package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.ScreenShakeOnDamageC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.ScreenShakeE

object ScreenShakeOnDamageS: EntitySystem() {
    init {
        Events.Damage.add { signal, damageE ->
            val ssod = damageE.entity.getComponent(ScreenShakeOnDamageC::class.java)
            if (ssod != null) {
                Events.ScreenShake.dispatch(ScreenShakeE(ssod.strength, ssod.time))
            }
        }
    }
}