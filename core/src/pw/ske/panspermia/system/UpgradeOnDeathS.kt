package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
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
            Panspermia.setScreen(Upgrade)
            switch = false
        }
    }
}