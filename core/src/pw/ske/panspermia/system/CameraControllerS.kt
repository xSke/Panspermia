package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.util.NumericalSpringing
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object CameraControllerS : EntitySystem(1) {
    var velocity = Vector2()

    var screenShakeStrength = 0f
    var screenShakeTime = 0f
    var lastMaxScreenShakeTime = 0f
    var fade = false

    var lastScreenShake = Vector2()

    init {
        Events.ScreenShake.add { screenShakeE ->
            screenShakeStrength = screenShakeE.strength
            screenShakeTime = screenShakeE.time
            lastMaxScreenShakeTime = screenShakeE.time
            fade = screenShakeE.fade
            false
        }
    }

    override fun update(deltaTime: Float) {
        if (!Play.playerDead) {
            val pos = Vector2(Play.camera.position.x, Play.camera.position.y)
            NumericalSpringing.springVector(pos.sub(lastScreenShake), velocity, Play.player.position, 1f, 8f, deltaTime)
            Play.camera.position.set(pos, 0f)

            if (screenShakeTime > 0f) {
                val strength = if (fade) {
                    MathUtils.lerp(0f, screenShakeStrength, screenShakeTime / lastMaxScreenShakeTime)
                } else {
                    screenShakeStrength
                }
                screenShakeTime -= deltaTime
                lastScreenShake.set(Math.random().toFloat() * strength * 2 - strength, Math.random().toFloat() * strength * 2 - strength)
                Play.camera.position.add(lastScreenShake.x, lastScreenShake.y, 0f)
            } else {
                lastScreenShake.setZero()
            }
        }

        Play.camera.update()

        Play.shapeRenderer.projectionMatrix = Play.camera.combined
        Play.batch.projectionMatrix = Play.camera.combined
    }
}