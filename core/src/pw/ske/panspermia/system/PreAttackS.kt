package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.AttackE

object PreAttackS : EntitySystem() {
    init {
        Events.PreAttack.add { e ->
            Timer.instance().scheduleTask(object : Timer.Task() {
                override fun run() {
                    Events.Attack.dispatch(AttackE(e.entity))
                }
            }, e.delay)
            false
        }
    }
}