package pw.ske.panspermia.ui

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pw.ske.panspermia.GameState
import pw.ske.panspermia.component.HealthC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.util.Assets

object HUDUI : Stage(ScreenViewport()) {
    var counter = 0f
    val anim = Animation(0.04f, *TextureRegion.split(Assets.manager.get("sprites/sperm.png"), 16, 16)[0]).apply {
        playMode = Animation.PlayMode.LOOP
    }

    val healthSperm = Image(anim.getKeyFrame(0f))
    val healthText = Label("x0", Skin)

    val dna = Label("DNA: 0", Skin)

    val title = Label("", Skin).apply {
        setFontScale(2f)

        Events.Title.register {
            setText(it.text)
            clearActions()

            color.a = 0f

            val a = AlphaAction()
            a.alpha = 1f
            a.duration = 1f

            val b = DelayAction(3f)

            val c = AlphaAction()
            c.alpha = 0f
            c.duration = 1f
            addAction(SequenceAction(a, b, c))
            false
        }
    }

    val table = Table().apply {
        top()
        pad(20f)
        add(Widget()).expandX()
        add(healthSperm).size(48f, 48f)
        add(healthText)
        add(dna).padLeft(50f)
        add(Widget()).expandX().row()

        add(title).padTop(100f).expandX().center().colspan(5)
        //debug()

        setFillParent(true)
    }

    init {
        addActor(table)
    }

    override fun act(delta: Float) {
        super.act(delta)

        val hp = MathUtils.floor(Play.player.getComponent(HealthC::class.java).health)
        healthText.setText(if (hp > 0) {
            counter += delta
            "x" + hp
        } else {
            "rip"
        })


        (healthSperm.drawable as TextureRegionDrawable).region = anim.getKeyFrame(counter)

        dna.setText("DNA: " + GameState.dna.toString())
    }
}