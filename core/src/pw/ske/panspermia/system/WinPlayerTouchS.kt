package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.component.WinPlayerTouchC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.screen.Generating
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.ui.UpgradeUI

object WinPlayerTouchS: EntitySystem() {
    init {
        Events.EntityTouchFixture.register {
            if (it.entity == Play.player) {
                val e = it.fixture.body.userData
                if (e is Entity) {
                    val c = e.getComponent(WinPlayerTouchC::class.java)
                    if (c != null) {
                        if (c.fix == it.fixture) {
                            Generating.state = Generating.State.INIT
                            GameState.stats.forEach {
                                it.level = it.startLevel
                            }
                            UpgradeUI.reconstruct()
                            Panspermia.setScreen(Generating)
                        }
                    }
                }
            }
            false
        }
    }
}