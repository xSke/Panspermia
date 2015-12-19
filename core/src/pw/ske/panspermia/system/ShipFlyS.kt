package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.ShipFlyC
import pw.ske.panspermia.position

object ShipFlyS: IteratingSystem(Family.all(BodyC::class.java, ShipFlyC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val fly = entity.getComponent(ShipFlyC::class.java)

        val input = Vector2()
        if (Gdx.input.isKeyPressed(Input.Keys.W)) input.add(0f, 1f)
        if (Gdx.input.isKeyPressed(Input.Keys.S)) input.add(0f, -1f)

        if (Gdx.input.isKeyPressed(Input.Keys.A)) input.add(1f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.D)) input.add(-1f, 0f)

        entity.body.applyLinearImpulse(Vector2(0f, input.y).rotateRad(entity.body.angle).scl(deltaTime).scl(fly.speed), entity.position, true)

        entity.body.applyAngularImpulse(input.x * deltaTime * fly.rotateSpeed, true)
    }
}