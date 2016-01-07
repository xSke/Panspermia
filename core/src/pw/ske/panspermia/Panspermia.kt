package pw.ske.panspermia

import com.badlogic.gdx.Game
import pw.ske.panspermia.screen.MainMenu

object Panspermia : Game() {
    override fun create() {
        screen = MainMenu
    }
}
