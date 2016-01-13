package pw.ske.panspermia.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.ui.LoadingUI
import pw.ske.panspermia.util.Assets

object Loading : ScreenAdapter() {
    init {
        show()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(41 / 255f, 140 / 255f, 238 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (Assets.manager.update()) {
            Panspermia.setScreen(MainMenu)
        }

        LoadingUI.status.setText("${Math.round(Assets.manager.progress * 100)}%")
        LoadingUI.act(delta)
        LoadingUI.draw()
    }

    override fun resize(width: Int, height: Int) {
        LoadingUI.viewport.update(width, height, true)
    }

    override fun show() {
        Gdx.input.inputProcessor = LoadingUI
    }
}