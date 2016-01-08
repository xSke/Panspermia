package pw.ske.panspermia.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Generating

object MainMenuUI : Stage(ScreenViewport()) {
    val logo = Image(Texture("ui/logo.png"))
    val button = TextButton("Play", Skin, "blue").apply {
        addListener(object : ClickListener(Input.Buttons.LEFT) {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Panspermia.setScreen(Generating)
            }
        })
    }
    val story = Label("You are a human on a mission:\nSpread your seed in outer space!\nInvade the alien vaginas, find the egg, and inseminate it!"
            , Skin, "small").apply {
        setAlignment(Align.center)
    }

    val controls = Label("Controls:\n- Mouse to aim\n- Left click to shoot\n- To recharge shield, stop shooting", Skin, "small").apply {
        setAlignment(Align.center)
    }

    val table = Table().apply {
        top()
        pad(50f)
        add(logo).width(logo.width * 6).height(logo.height * 6).expandX().center().padBottom(50f).row()
        add(story).expandX().center().padBottom(50f).row()
        add(controls).expandX().center().padBottom(50f).row()
        add(button).width(150f).expandX().center()
        //debug()

        setFillParent(true)
    }

    init {
        addActor(table)
    }
}