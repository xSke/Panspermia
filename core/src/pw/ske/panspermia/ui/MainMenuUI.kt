package pw.ske.panspermia.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Generating

object MainMenuUI : Stage(ScreenViewport()) {
    val button = TextButton("Play", Skin, "blue").apply {
        addListener(object : ClickListener(Input.Buttons.LEFT) {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Panspermia.setScreen(Generating)
            }
        })
    }

    val table = Table().apply {
        top()
        pad(20f)
        add(button).width(150f)

        setFillParent(true)
    }

    init {
        addActor(table)
    }
}