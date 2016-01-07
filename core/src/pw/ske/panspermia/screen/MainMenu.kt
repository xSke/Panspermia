package pw.ske.panspermia.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import pw.ske.panspermia.ui.MainMenuUI
import pw.ske.panspermia.ui.UpgradeUI

object MainMenu: ScreenAdapter() {
    init {
        show()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(41 / 255f, 140 / 255f, 238 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        MainMenuUI.act(delta)
        MainMenuUI.draw()
    }

    override fun resize(width: Int, height: Int) {
        MainMenuUI.viewport.update(width, height, true)
    }

    override fun show() {
        Gdx.input.inputProcessor = MainMenuUI
    }
}