package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BulletDeathC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object BulletDeathS : EntitySystem() {
    init {
        Events.Death.add { deathE ->
            val bd = deathE.entity.getComponent(BulletDeathC::class.java)
            if (bd != null) {
                (0..bd.amount-1).forEach {
                    val proj = EntityCreator.createProjectile()
                    proj.position = deathE.entity.position
                    proj.body.linearVelocity = Vector2(0f, 1f).rotate(Math.random().toFloat() * 360).nor().scl(Math.random().toFloat() * bd.speed)

                    Play.engine.addEntity(proj)
                }
            }
            false
        }
    }
}