package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.ScreenShakeOnAttackC
import pw.ske.panspermia.component.ScreenShakeOnDeathC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.ScreenShakeE

object ScreenShakeOnAttackS : EntitySystem() {
    init {
        Events.Attack.add { attackE ->
            val ssoa = attackE.entity.getComponent(ScreenShakeOnAttackC::class.java)
            if (ssoa != null) {
                Events.ScreenShake.dispatch(ScreenShakeE(ssoa.strength, ssoa.time, false))
            }
            false
        }
    }
}