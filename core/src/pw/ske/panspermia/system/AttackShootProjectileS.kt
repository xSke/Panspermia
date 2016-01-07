package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.AttackShootProjectileC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object AttackShootProjectileS: EntitySystem() {
    init {
        Events.Attack.add { attackE ->
            val asp = attackE.entity.getComponent(AttackShootProjectileC::class.java)
            if (asp != null) {
                asp.offset.forEach { offset ->
                    val pos = attackE.entity.position.add(attackE.entity.body.getWorldVector(offset))
                    val vel = if (asp.towardsPlayer) {
                        Play.player.position.sub(pos).nor()
                    } else {
                        attackE.entity.body.getWorldVector(offset).nor()
                    }

                    val proj = EntityCreator.createProjectile(asp.homing)
                    proj.position = pos
                    proj.body.linearVelocity = vel.scl(asp.speed)

                    Play.engine.addEntity(proj)
                }
            }
            false
        }
    }
}