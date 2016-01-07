package pw.ske.panspermia.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import pw.ske.panspermia.ui.UpgradeUI

object Upgrade: ScreenAdapter() {
    init {
        show()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(41 / 255f, 140 / 255f, 238 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        UpgradeUI.act(delta)
        UpgradeUI.draw()
    }

    override fun resize(width: Int, height: Int) {
        UpgradeUI.viewport.update(width, height, true)
    }

    override fun show() {
        Gdx.input.inputProcessor = UpgradeUI
    }
}