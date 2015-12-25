package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.NumericalSpringing
import pw.ske.panspermia.Play
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object CameraControllerS : EntitySystem(1) {
    var velocity = Vector2()

    var screenShakeStrength = 0f
    var screenShakeTime = 0f

    var lastScreenShake = Vector2()

    init {
        Events.ScreenShake.add { signal, screenShakeE ->
            screenShakeStrength = screenShakeE.strength
            screenShakeTime = screenShakeE.time
        }
    }

    override fun update(deltaTime: Float) {
        val pos = Vector2(Play.camera.position.x, Play.camera.position.y)
        NumericalSpringing.springVector(pos.sub(lastScreenShake), velocity, Play.player.position, 1f, 8f, deltaTime)
        Play.camera.position.set(pos, 0f)

        if (screenShakeTime > 0f) {
            screenShakeTime -= deltaTime
            lastScreenShake.set(Math.random().toFloat() * screenShakeStrength * 2 - screenShakeStrength, Math.random().toFloat() * screenShakeStrength * 2 - screenShakeStrength)
            Play.camera.position.add(lastScreenShake.x, lastScreenShake.y, 0f)
        } else {
            lastScreenShake.setZero()
        }

        Play.camera.update()

        Play.shapeRenderer.projectionMatrix = Play.camera.combined
        Play.batch.projectionMatrix = Play.camera.combined
    }
}