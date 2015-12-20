package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.component.AttackPeriodicallyC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.PreAttackE

object AttackPeriodicallyS: IteratingSystem(Family.all(AttackPeriodicallyC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val ap = entity.getComponent(AttackPeriodicallyC::class.java)

        ap.counter += deltaTime

        if (ap.counter >= ap.interval) {
            ap.counter -= ap.interval

            Events.PreAttack.dispatch(PreAttackE(entity, ap.preAttackDelay))
        }
    }
}