package pw.ske.panspermia.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin

object Skin : Skin() {
    init {
        val font = BitmapFont(Gdx.files.internal("font.fnt"))
        add("default", font)

        val lbl = Label.LabelStyle(font, Color.WHITE)
        add("default", lbl)
    }
}