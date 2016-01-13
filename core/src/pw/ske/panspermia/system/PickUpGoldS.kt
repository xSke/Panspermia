package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import pw.ske.panspermia.GameState
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.component.GoldC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.util.Assets

object PickUpGoldS: EntitySystem() {
    val sound = Assets.manager.get("audio/coin.wav", Sound::class.java)

    init {
        Events.EntityTouchFixture.register { entityTouchFixtureE ->
            val g = entityTouchFixtureE.entity.getComponent(GoldC::class.java)

            if (g != null) {
                if (entityTouchFixtureE.fixture.body.userData == Play.player) {
                    sound.play()
                    Play.engine.removeEntity(entityTouchFixtureE.entity)

                    GameState.dna++
                }
            }
            false
        }
    }
}