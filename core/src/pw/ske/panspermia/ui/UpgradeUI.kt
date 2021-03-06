package pw.ske.panspermia.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.screen.Generating
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.stat.Stat

object UpgradeUI : Stage(ScreenViewport()) {
    val dnacount = Label("DNA: 0", Skin)

    val buttons = hashMapOf<Stat<out Any>, TextButton>()

    val blip = Gdx.audio.newSound(Gdx.files.internal("audio/blip.wav"))

    val go = TextButton("Go", Skin, "blue").apply {
        addListener(object : ClickListener(Input.Buttons.LEFT) {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                blip.play()

                // Kotlin doesn't like protected fields, don't autofix this
                Panspermia.setScreen(Play)
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

    fun reconstruct() {
        buttons.clear()
        table.clear()

        GameState.stats.forEachIndexed { i, it ->
            val label = Label(it.name + " lv." + it.level, Skin, "small")
            label.setAlignment(Align.center)

            val button = TextButton("Upgrade (${it.nextLevelPrice} DNA)", Skin, if (GameState.dna >= it.nextLevelPrice) "blue" else "red").apply {
                addListener(object : ClickListener(Input.Buttons.LEFT) {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                        blip.play()

                        if (GameState.dna >= it.nextLevelPrice) {
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

            val group = VerticalGroup()
            group.addActor(label)
            group.space(8f)
            group.addActor(button)
            group.fill()

            table.add(group).width(300f).center().space(25f)
            if (i == 0) {
                table.add(dnacount).top().space(25f).row()
            } else if (i % 2 == 0) {
                table.row()
            }

            buttons.put(it, button)
        }

        table.add(go).colspan(2).expandY().bottom().right().width(100f).space(25f)
    }

    init {
        reconstruct()
    }

    override fun act(delta: Float) {
        super.act(delta)

        dnacount.setText("DNA: " + GameState.dna)

        buttons.forEach { stat, textButton ->
            if (stat.level != stat.maxLevel) {
                textButton.style = Skin.get(if (GameState.dna >= stat.nextLevelPrice) "blue" else "red", TextButton.TextButtonStyle::class.java)
            }
        }
    }
}