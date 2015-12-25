package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.HomingOnPlayerC
import pw.ske.panspermia.position

object HomingOnPlayerS: IteratingSystem(Family.all(BodyC::class.java, HomingOnPlayerC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hop = entity.getComponent(HomingOnPlayerC::class.java)

        val vel = Play.player.position.sub(entity.position)
        entity.body.linearVelocity = entity.body.linearVelocity.add(vel.nor().scl(hop.acceleration)).limit(hop.speed)
    }
}