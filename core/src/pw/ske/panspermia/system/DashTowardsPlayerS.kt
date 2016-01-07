package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.DashTowardsPlayerC
import pw.ske.panspermia.position

object DashTowardsPlayerS: IteratingSystem(Family.all(BodyC::class.java, DashTowardsPlayerC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val dtp = entity.getComponent(DashTowardsPlayerC::class.java)

        dtp.counter += deltaTime
        if (dtp.counter >= dtp.interval) {
            dtp.counter -= dtp.interval

            val vel = Play.player.position.sub(entity.position).nor().scl(dtp.speed)
            entity.body.applyLinearImpulse(vel, entity.body.position, true)
        }
    }
}