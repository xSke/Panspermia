package pw.ske.panspermia.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

object Skin : Skin() {
    init {
        val font = BitmapFont(Gdx.files.internal("font.fnt"))
        add("default", font)

        val fontsmall = BitmapFont(Gdx.files.internal("font.fnt"))
        fontsmall.data.setScale(0.666666666f)
        add("small", fontsmall)

        val blueup = NinePatch(Texture("blueup.9.png"), 14, 14, 14, 14)
        val bluedown = NinePatch(Texture("bluedown.9.png"), 14, 14, 14, 14)
        val bdu = NinePatchDrawable(blueup)
        val bdd = NinePatchDrawable(bluedown)

        val redup = NinePatch(Texture("redup.9.png"), 14, 14, 14, 14)
        val reddown = NinePatch(Texture("reddown.9.png"), 14, 14, 14, 14)
        val rdu = NinePatchDrawable(redup)
        val rdd = NinePatchDrawable(reddown)

        val greyup = NinePatch(Texture("greyup.9.png"), 14, 14, 14, 14)
        val greydown = NinePatch(Texture("greydown.9.png"), 14, 14, 14, 14)
        val gdu = NinePatchDrawable(greyup)
        val gdd = NinePatchDrawable(greydown)

        val bbs = TextButton.TextButtonStyle(bdu, bdd, bdu, fontsmall)
        bbs.unpressedOffsetY = 3f
        bbs.checkedOffsetY = 3f
        bbs.pressedOffsetY = 0f

        val rbs = TextButton.TextButtonStyle(rdu, rdd, rdu, fontsmall)
        rbs.unpressedOffsetY = 3f
        rbs.checkedOffsetY = 3f
        rbs.pressedOffsetY = 0f

        val gbs = TextButton.TextButtonStyle(gdu, gdd, gdu, fontsmall)
        gbs.unpressedOffsetY = 3f
        gbs.checkedOffsetY = 3f
        gbs.pressedOffsetY = 0f

        add("blue", bbs)
        add("red", rbs)
        add("grey", gbs)

        add("blue", bdu)

        //add("block", bd)

        val lbl = Label.LabelStyle(font, Color.WHITE)
        add("default", lbl)

        val lbls = Label.LabelStyle(fontsmall, Color.WHITE)
        add("small", lbls)
    }
}