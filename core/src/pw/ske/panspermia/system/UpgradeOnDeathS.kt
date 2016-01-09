package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.screen.Upgrade
import pw.ske.panspermia.event.Events

object UpgradeOnDeathS: EntitySystem() {
    var switch = false
    var timer = 0f

    init {
        Events.Death.add {  deathE ->
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

            if (timer > 1) {
                val v = MathUtils.clamp(MathUtils.lerp(1f, 0f, (timer - 1) / 2f), 0f, 1f)
                Play.globalSaturation = v
                Play.globalValue = v
                Play.globalSpeed = v * 0.75f + 0.25f

                if (timer > 4f) {
                    timer = 0f
                    switch = false

                    Panspermia.setScreen(Upgrade)
                }
            }
        }
    }
}