package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.PlayerMovementC
import pw.ske.panspermia.position

object PlayerMovementS : IteratingSystem(Family.all(BodyC::class.java, PlayerMovementC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pm = entity.getComponent(PlayerMovementC::class.java)

        val uprj = Play.camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
        val pos = Vector2(uprj.x, uprj.y)

        val vel = pos.sub(entity.position).nor().scl(pm.speed)
        entity.body.linearVelocity = vel
        entity.body.setTransform(entity.body.position, vel.angleRad() - MathUtils.PI / 2)

        /*val input = Vector2()
        if (Gdx.input.isKeyPressed(Input.Keys.W)) input.add(0f, 1f)
        if (Gdx.input.isKeyPressed(Input.Keys.S)) input.add(0f, -1f)

        if (Gdx.input.isKeyPressed(Input.Keys.A)) input.add(1f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.D)) input.add(-1f, 0f)

        entity.body.applyLinearImpulse(Vector2(0f, input.y).rotateRad(entity.body.angle).scl(deltaTime).scl(fly.speed), entity.position, true)

        entity.body.applyAngularImpulse(input.x * deltaTime * fly.rotateSpeed, true)*/
    }
}