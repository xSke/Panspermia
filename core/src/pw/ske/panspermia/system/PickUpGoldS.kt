package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.GoldC
import pw.ske.panspermia.event.Events

object PickUpGoldS: EntitySystem() {
    val sound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"))

    init {
        Events.EntityTouchFixture.add { signal, entityTouchFixtureE ->
            val g = entityTouchFixtureE.entity.getComponent(GoldC::class.java)

            if (g != null) {
                if (entityTouchFixtureE.fixture.body.userData == Play.player) {
                    sound.play()
                    Play.engine.removeEntity(entityTouchFixtureE.entity)

                    GameState.dna++
                }
            }
        }
    }
}