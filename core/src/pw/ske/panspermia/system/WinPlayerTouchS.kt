package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.component.WinPlayerTouchC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.screen.Play

object WinPlayerTouchS: EntitySystem() {
    init {
        Events.EntityTouchFixture.register {
            if (it.entity == Play.player) {
                val e = it.fixture.body.userData
                if (e is Entity) {
                    val c = e.getComponent(WinPlayerTouchC::class.java)
                    if (c != null) {
                        if (c.fix == it.fixture) {
                            println("WINNNNNN")
                        }
                    }
                }
            }
            false
        }
    }
}