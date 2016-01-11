package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.screen.Upgrade
import pw.ske.panspermia.event.Events

object UpgradeOnDeathS : EntitySystem() {
    var switch = false
    var timer = 0f

    init {
        Events.Death.register { deathE ->
            if (deathE.entity == Play.player) {
                switch = true
            }
            false
        }
    }

    override fun update(deltaTime: Float) {
        if (switch) {
            Play.playerDead = true
            timer += deltaTime / Play.globalSpeed

            val v1 = MathUtils.clamp(MathUtils.lerp(1f, 0.1f, timer / 2f), 0.1f, 1f)
            val v2 = MathUtils.clamp(MathUtils.lerp(1f, 0f, (timer - 1) / 2f), 0f, 1f)

            Play.globalSpeed = v1
            Play.globalSaturation = v2
            Play.globalValue = v2

            if (timer > 4f) {
                timer = 0f
                switch = false

                Panspermia.setScreen(Upgrade)
            }
        }
    }
}