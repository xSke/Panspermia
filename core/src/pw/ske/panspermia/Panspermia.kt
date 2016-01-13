package pw.ske.panspermia

import com.badlogic.gdx.Game
import pw.ske.panspermia.screen.Loading
import pw.ske.panspermia.screen.MainMenu
import pw.ske.panspermia.screen.Play

object Panspermia : Game() {
    override fun create() {
        screen = Loading
    }
}
