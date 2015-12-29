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
        Events.Attack.add { attackE ->
            val ams = attackE.entity.getComponent(AttackMiniSpermC::class.java)

            if (ams != null) {
                var angle = -(ams.split - 1) * 5f
                (0..ams.split-1).forEach {
                    val sperm = EntityCreator.createMiniSperm()
                    sperm.body.linearVelocity = Vector2(0f, ams.speed).rotateRad(attackE.entity.body.angle).rotate(angle).rotate(Math.random().toFloat() * 2f - 1f)
                    sperm.body.setTransform(attackE.entity.body.getWorldVector(ams.offset).add(attackE.entity.position), attackE.entity.body.angle)

                    Play.engine.addEntity(sperm)

                    angle += 10
                }
            }
            false
        }
    }
}