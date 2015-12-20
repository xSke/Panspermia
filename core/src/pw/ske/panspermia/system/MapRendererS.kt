package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import pw.ske.panspermia.Play

object MapRendererS : EntitySystem(999) {
    val texts = TextureRegion.split(Texture("walls.png"), 16, 16)

    override fun update(deltaTime: Float) {
        Play.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

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
                    val se = n(1, -1);

                    val inc = Color(0xEE2929FF.toInt())
                    val outc = Color(0xDE1111FF.toInt())

                    var vtx1 = inc
                    var vtx2 = inc
                    var vtx3 = inc
                    var vtx4 = inc

                    if (!n) {
                        vtx4 = outc
                        vtx3 = outc
                    }

                    if (!s) {
                        vtx1 = outc
                        vtx2 = outc
                    }

                    if (!w) {
                        vtx1 = outc
                        vtx4 = outc
                    }

                    if (!e) {
                        vtx2 = outc
                        vtx3 = outc
                    }

                    if (!ne) {
                        vtx3 = outc
                    }

                    if (!nw) {
                        vtx4 = outc
                    }

                    if (!se) {
                        vtx2 = outc
                    }

                    if (!sw) {
                        vtx1 = outc
                    }

                    Play.shapeRenderer.rect(x.toFloat(), y.toFloat(), 1f, 1f, vtx1, vtx2, vtx3, vtx4)
                }
            }
        }

        Play.shapeRenderer.end()
    }
}