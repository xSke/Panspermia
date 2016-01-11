package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import pw.ske.panspermia.body
import pw.ske.panspermia.component.AttractPlayerC
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.position
import pw.ske.panspermia.screen.Play

object AttractPlayerS: IteratingSystem(Family.all(BodyC::class.java, AttractPlayerC::class.java).get(), 10) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val vec = entity.position.sub(Play.player.position)

        val ap = entity.getComponent(AttractPlayerC::class.java)

        if (vec.len2() < ap.distance * ap.distance) {
            val force = (ap.distance * ap.distance - vec.len2()) / (ap.distance * ap.distance)
            vec.nor().scl(force).scl(ap.force).scl(deltaTime)

            Play.player.body.applyLinearImpulse(vec, Play.player.position, true)
        }
    }
}