package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.Play
import pw.ske.panspermia.Upgrade
import pw.ske.panspermia.event.Events

object UpgradeOnDeathS: EntitySystem() {
    var switch = false

    init {
        Events.Death.add { signal, deathE ->
            if (deathE.entity == Play.player) {
                switch = true
            }
        }
    }

    override fun update(deltaTime: Float) {
        if (switch) {
            Timer.instance().scheduleTask(object: Timer.Task() {
                override fun run() {
                    Panspermia.setScreen(Upgrade)
                }
            }, 3f)
            switch = false
        }
    }
}