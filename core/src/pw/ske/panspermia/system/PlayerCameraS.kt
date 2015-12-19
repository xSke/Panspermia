package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.NumericalSpringing
import pw.ske.panspermia.Play
import pw.ske.panspermia.position

object PlayerCameraS: EntitySystem(1) {
    var velocity = Vector2()

    override fun update(deltaTime: Float) {
        val pos = Vector2(Play.camera.position.x, Play.camera.position.y)
        NumericalSpringing.springVector(pos, velocity, Play.player.position, 1f, 8f, deltaTime)
        Play.camera.position.set(pos, 0f)
        Play.camera.update()

        Play.shapeRenderer.projectionMatrix = Play.camera.combined
        Play.batch.projectionMatrix = Play.camera.combined
    }
}