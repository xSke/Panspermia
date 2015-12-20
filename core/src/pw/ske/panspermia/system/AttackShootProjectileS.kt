package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.AttackShootProjectileC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object AttackShootProjectileS: EntitySystem() {
    init {
        Events.Attack.add { signal, attackE ->
            val asp = attackE.entity.getComponent(AttackShootProjectileC::class.java)
            if (asp != null) {
                val pos = attackE.entity.position.add(attackE.entity.body.getWorldVector(asp.offset))
                val vel = Play.player.position.sub(pos)

                val angle = vel.nor().angle(attackE.entity.body.getWorldVector(Vector2(0f, 1f)))

                val proj = EntityCreator.createProjectile()
                proj.position = pos
                proj.body.linearVelocity = vel.scl(asp.speed)

                Play.engine.addEntity(proj)
            }
        }
    }
}