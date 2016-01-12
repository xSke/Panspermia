package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.GameState
import pw.ske.panspermia.position
import pw.ske.panspermia.screen.Play

object EggFinderS : EntitySystem(1002) {
    override fun update(deltaTime: Float) {
        if (GameState.eggFinder.value) {
            Play.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

            val pos1 = Vector2(0f, 0.9f)
            val pos2 = Vector2(-0.1f, 0.7f)
            val pos3 = Vector2(0.1f, 0.7f)

            val eggDelta = Play.egg.position.sub(Play.player.position).angle() - 90f

            pos1.rotate(eggDelta)
            pos2.rotate(eggDelta)
            pos3.rotate(eggDelta)

            Play.shapeRenderer.color = Color.WHITE

            Play.shapeRenderer.triangle(
                    Play.player.position.x + pos1.x,
                    Play.player.position.y + pos1.y,
                    Play.player.position.x + pos2.x,
                    Play.player.position.y + pos2.y,
                    Play.player.position.x + pos3.x,
                    Play.player.position.y + pos3.y
            )

            Play.shapeRenderer.end()
        }
    }
}