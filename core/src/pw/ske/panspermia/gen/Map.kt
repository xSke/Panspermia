package pw.ske.panspermia.gen

import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import pw.ske.panspermia.Play

data class Map(val map: Array<Array<Boolean>>, val start: GridPoint2, val end: GridPoint2, val width: Int, val height: Int) {
    fun placeWorld() {
        val body = Play.world.createBody(BodyDef())
        val shape = EdgeShape()

        map.forEachIndexed { x, xr ->
            xr.forEachIndexed { y, v ->
                if (v) {
                    fun c(xx: Int, yy: Int, x1: Int, y1: Int, x2: Int, y2: Int) {
                        if (xx >= 0 && xx < width && yy >= 0 && yy < height && !map[xx][yy]) {
                            shape.set(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())

                            val fix = body.createFixture(shape, 0f)
                        }
                    }

                    c(x + 1, y, x + 1, y + 1, x + 1, y)
                    c(x - 1, y, x, y + 1, x, y)
                    c(x, y + 1, x, y + 1, x + 1, y + 1)
                    c(x, y - 1, x, y, x + 1, y)
                }
            }
        }
    }
}