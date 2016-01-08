package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.PlayerMovementC
import pw.ske.panspermia.position
import pw.ske.panspermia.screen.Play

object PlayerMovementS : IteratingSystem(Family.all(BodyC::class.java, PlayerMovementC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pm = entity.getComponent(PlayerMovementC::class.java)

        val uprj = Play.camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
        val pos = Vector2(uprj.x, uprj.y)

        var speed = pm.speed

        val lmt = 1
        val mouseDelta = pos.sub(entity.position)
        if (mouseDelta.len2() < lmt * lmt) {
            speed = MathUtils.lerp(0f, speed, mouseDelta.len2() / lmt)
        }

        val vel = mouseDelta.nor().scl(speed)
        entity.body.linearVelocity = vel
        entity.body.setTransform(entity.body.position, vel.angleRad() - MathUtils.PI / 2)
    }
}