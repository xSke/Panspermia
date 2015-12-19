package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import pw.ske.panspermia.Play

object MapRendererS: EntitySystem(999) {
    override fun update(deltaTime: Float) {
        Play.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        Play.shapeRenderer.color = Color.BLACK
        Play.map.map.forEachIndexed { x, xr ->
            xr.forEachIndexed { y, v ->
                if (v) {
                    Play.shapeRenderer.rect(x.toFloat(), y.toFloat(), 1f, 1f)
                }
            }
        }

        Play.shapeRenderer.end()
    }
}