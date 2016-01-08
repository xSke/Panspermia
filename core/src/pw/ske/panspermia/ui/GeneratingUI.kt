package pw.ske.panspermia.ui

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport

object GeneratingUI : Stage(ScreenViewport()) {
    val title = Label("Generating...", Skin)
    val status = Label("", Skin, "small")

    val table = Table().apply {
        top()
        pad(20f)
        add(title).expandX().center().padBottom(10f).row()
        add(status).expandX().center()

        setFillParent(true)
    }

    init {
        addActor(table)
    }
}