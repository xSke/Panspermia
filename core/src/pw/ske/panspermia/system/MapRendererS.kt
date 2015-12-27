package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import pw.ske.panspermia.Play

object MapRendererS : EntitySystem(999) {
    val blur = Texture("blur.png")

    override fun update(deltaTime: Float) {
        Play.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        Play.shapeRenderer.color = Color(0x298CEEFF.toInt())
        (0..Play.map.width-1).forEach { x ->
            (0..Play.map.height-1).forEach { y ->
                if (Play.map.map[x][y]) {
                    Play.shapeRenderer.rect(x.toFloat(), y.toFloat(), 1f, 1f)
                }
            }
        }

        Play.batch.begin()
        (0..Play.map.width-1).forEach { x ->
            (0..Play.map.height-1).forEach { y ->
                if (Play.map.map[x][y]) {
                    Play.batch.draw(blur, x.toFloat() - 0.5f, y.toFloat() - 0.5f, 2f, 2f)
                }
            }
        }
        Play.batch.end()

        Play.shapeRenderer.color = Color(0x1178DEFF.toInt())
        (0..Play.map.width-1).forEach { x ->
            (0..Play.map.height-1).forEach { y ->
                if (Play.map.map[x][y]) {
                    Play.shapeRenderer.rect(x.toFloat(), y.toFloat(), 1f, 1f)
                }
            }
        }

        Play.shapeRenderer.end()
    }
}