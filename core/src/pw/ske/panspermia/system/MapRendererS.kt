package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import pw.ske.panspermia.Play

object MapRendererS : EntitySystem(999) {
    val texts = TextureRegion.split(Texture("walls.png"), 16, 16)

    override fun update(deltaTime: Float) {
        Play.batch.begin()

        //Play.batch.color = Color(0x00FF00FF.toInt())
        //Play.shapeRenderer.color = Color(0x1AB03DFF.toInt())
        Play.map.map.forEachIndexed { x, xr ->
            xr.forEachIndexed { y, v ->
                fun n(dx: Int, dy: Int): Boolean {
                    val xx = dx + x
                    val yy = dy + y
                    if (xx < 0 || yy < 0 || xx >= Play.map.width || yy >= Play.map.height) {
                        return false
                    } else {
                        return Play.map.map[xx][yy]
                    }
                }

                if (n(0, 0)) {
                    val w = n(-1, 0)
                    val e = n(1, 0)
                    val n = n(0, 1)
                    val s = n(0, -1)

                    val nw = n(-1, 1)
                    val ne = n(1, 1)
                    val sw = n(-1, -1)
                    val se = n(1, -1)

                    Play.batch.draw(texts[0][0], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)

                    if (!n) Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                    if (!s) Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                    if (!w) Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                    if (!e) Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)

                    if (!ne) Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                    if (!sw) Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                    if (!nw) Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                    if (!se) Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)

                    /*when {
                        !w && e && n && s -> Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                        w && !e && n && s -> Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)
                        w && e && !n && s -> Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                        w && e && n && !s -> Play.batch.draw(texts[0][1], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                        !w && e && !n && s -> Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                        w && !e && !n && s -> Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                        !w && e && n && !s -> Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                        w && !e && n && !s -> Play.batch.draw(texts[0][2], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)
                        w && !e && !n && !s -> Play.batch.draw(texts[0][4], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)
                        !w && e && !n && !s -> Play.batch.draw(texts[0][4], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                        !w && !e && n && !s -> Play.batch.draw(texts[0][4], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                        !w && !e && !n && s -> Play.batch.draw(texts[0][4], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                        !ne -> Play.batch.draw(texts[0][3], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
                        !nw -> Play.batch.draw(texts[0][3], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 90f)
                        !se -> Play.batch.draw(texts[0][3], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, -90f)
                        !sw -> Play.batch.draw(texts[0][3], x.toFloat(), y.toFloat(), 0.5f, 0.5f, 1f, 1f, 1f, 1f, 180f)
                        else -> Play.batch.draw(texts[0][0], x.toFloat(), y.toFloat(), 1f, 1f)
                    }*/
                }
            }
        }

        Play.batch.end()
    }
}