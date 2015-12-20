package pw.ske.panspermia.gen

import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.body

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

    fun placeEntities() {
        placeWeed()
    }

    fun neighbors8(x: Int, y: Int): Int {
        var neighbors = 0
        (-1..1).forEach { xx ->
            (-1..1).forEach { yy ->
                if (xx != 0 || yy != 0) {
                    val xxx = xx + x
                    val yyy = yy + y

                    if (xxx >= 0 && xxx < width && yyy >= 0 && yyy < width) {
                        if (map[xxx][yyy]) {
                            neighbors++
                        }
                    }
                }
            }
        }
        return neighbors
    }

    fun filled(xx: Int, yy: Int): Boolean {
        if (xx >= 0 && xx < width && yy >= 0 && yy < width) {
            return map[xx][yy]
        } else {
            return false
        }
    }

    fun neighbors4(x: Int, y: Int): Int {
        var neighbors = 0

        if (filled(x - 1, y)) neighbors += 1
        if (filled(x + 1, y)) neighbors += 1
        if (filled(x, y - 1)) neighbors += 1
        if (filled(x, y + 1)) neighbors += 1

        return neighbors
    }

    fun placeWeed() {
        (0..50).map {
            val pos = GridPoint2()
            while (map[pos.x][pos.y] || neighbors4(pos.x, pos.y) != 1) {
                pos.set((Math.random() * width).toInt(), (Math.random() * width).toInt())
            }
            pos
        }.forEach {
            val direction = when {
                filled(it.x - 1, it.y) -> -90
                filled(it.x + 1, it.y) -> 90
                filled(it.x, it.y - 1) -> 0
                filled(it.x, it.y + 1) -> 180
                else -> 0
            }

            val weed = EntityCreator.createWeed()
            weed.body.setTransform(it.x + 0.5f, it.y + 0.5f, direction * MathUtils.degreesToRadians)

            Play.engine.addEntity(weed)
            println(it)
        }
    }
}