package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.AttackMiniSpermC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object AttackMiniSpermS: EntitySystem() {
    init {
        Events.Attack.add { signal, attackE ->
            val ams = attackE.entity.getComponent(AttackMiniSpermC::class.java)

            if (ams != null) {
                val sperm = EntityCreator.createProjectile()
                sperm.position = attackE.entity.position
                sperm.body.linearVelocity = Vector2(0f, ams.speed).rotateRad(attackE.entity.body.angle)

                Play.engine.addEntity(sperm)
            }
        }
    }
}