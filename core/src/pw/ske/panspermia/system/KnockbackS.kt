package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.body
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object KnockbackS: EntitySystem() {
    init {
        Events.Damage.register {
            val vec = it.source!!.body.linearVelocity.nor().scl(1f)
            it.entity.body.applyLinearImpulse(vec, it.entity.position, true)
            false
        }
    }
}