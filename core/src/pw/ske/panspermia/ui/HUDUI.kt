package pw.ske.panspermia.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.HealthC

object HUDUI : Stage(ScreenViewport()) {
    var counter = 0f
    val anim = Animation(0.04f, *TextureRegion.split(Texture("sperm.png"), 16, 16)[0]).apply {
        playMode = Animation.PlayMode.LOOP
    }

    val healthSperm = Image(anim.getKeyFrame(0f))
    val healthText = Label("x0", Skin)

    val dna = Label("DNA: 0", Skin)

    val table = Table().apply {
        top()
        pad(20f)
        add(healthSperm).size(48f, 48f)
        add(healthText)
        add(dna).padLeft(50f)

        setFillParent(true)
    }

    init {
        addActor(table)
    }

    override fun act(delta: Float) {
        super.act(delta)

        healthText.setText("x" + MathUtils.floor(Play.player.getComponent(HealthC::class.java).health))

        counter += delta

        (healthSperm.drawable as TextureRegionDrawable).region = anim.getKeyFrame(counter)

        dna.setText("DNA: " + GameState.dna.toString())
    }
}