package pw.ske.panspermia.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Generating
import pw.ske.panspermia.screen.Play

object UpgradeUI : Stage(ScreenViewport()) {
    var speedLevel = 1

    val dnacount = Label("DNA: 0", Skin)


    val go = TextButton("Go", Skin, "blue").apply {
        addListener(object: ClickListener(Input.Buttons.LEFT) {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                // Kotlin doesn't like protected fields, don't autofix this
                Panspermia.setScreen(Generating)
            }
        })
    }

    val table = Table().apply {
        background = Skin.get("blue", NinePatchDrawable::class.java)
        pad(20f)
        //debug()
    }

    val outertable = Table().apply {
        add(table).prefWidth(640f).prefHeight(480f)
        setFillParent(true)
        UpgradeUI.addActor(this)
    }

    init {
        GameState.stats.forEachIndexed { i, it ->
            val label = Label(it.name + " lv." + it.level, Skin, "small")

            val button = TextButton("Upgrade (${it.nextLevelPrice} DNA)", Skin, if (GameState.dna >= it.nextLevelPrice) "blue" else "red").apply {
                addListener(object: ClickListener(Input.Buttons.LEFT) {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                        if (GameState.dna >= it.nextLevelPrice || true) {
                            GameState.dna -= it.nextLevelPrice
                            it.level++

                            label.setText(it.name + " lv." + it.level)

                            if (it.level == it.maxLevel) {
                                setText("[MAX]")
                                isDisabled = true
                                touchable = Touchable.disabled
                                style = Skin.get("grey", TextButton.TextButtonStyle::class.java)
                            } else {
                                setText("Upgrade (" + it.nextLevelPrice + " DNA)")
                                style = Skin.get(if (GameState.dna >= it.nextLevelPrice) "blue" else "red", TextButton.TextButtonStyle::class.java)
                            }

                        }
                    }
                })
            }

            table.add(label).center().padBottom(5f)
            if (i == 0) {
                table.add(dnacount).expandX().right().padBottom(10f).row()
            } else {
                table.row()
            }
            table.add(button).width(300f).center().padBottom(25f).row()
        }

        table.add(go).colspan(2).expandY().bottom().right().width(100f)
    }

    override fun act(delta: Float) {
        super.act(delta)

        dnacount.setText("DNA: " + GameState.dna)
    }
}