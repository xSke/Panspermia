package pw.ske.panspermia.gen

import com.badlogic.gdx.math.GridPoint2

object LevelGenerator {
    fun genMap(w: Int, h: Int): Map {
        var map = Array(w, { Array(h, { false }) })
        (0..w - 1).forEach { x ->
            (0..h - 1).forEach { y ->
                map[x][y] = Math.random() < 0.46
            }
        }

        fun neighbors(x: Int, y: Int): Int {
            var neighbors = 0
            (-1..1).forEach { xx ->
                (-1..1).forEach { yy ->
                    if (xx != 0 || yy != 0) {
                        val xxx = xx + x
                        val yyy = yy + y

                        if (xxx >= 0 && xxx < w && yyy >= 0 && yyy < h) {
                            if (map[xxx][yyy]) {
                                neighbors++
                            }
                        }
                    }
                }
            }
            return neighbors
        }

        fun fillCorner() {
            (0..w - 1).forEach { x ->
                map[x][0] = true
                map[x][h - 1] = true
            }


            (0..h - 1).forEach { y ->
                map[0][y] = true
                map[w - 1][y] = true
            }
        }

        (0..6).forEach {
            val newmap = Array(w, { Array(h, { false }) })

            fillCorner()
            (0..w - 1).forEach { x ->
                (0..h - 1).forEach { y ->
                    val ns = neighbors(x, y)
                    newmap[x][y] = if (map[x][y]) {
                        ns >= 4
                    } else {
                        ns >= 5
                    }
                }
            }

            map = newmap
            fillCorner()
        }

        val visited = map.clone().map { it.clone() }
        val startPointsWithMost = hashMapOf<GridPoint2, Int>()
        (0..w - 1).forEach { x ->
            (0..h - 1).forEach { y ->
                if (!map[x][y]) {
                    if (!visited[x][y]) {
                        var count = 1
                        fun flood(xx: Int, yy: Int) {
                            visited[xx][yy] = true
                            count++

                            if (xx < w-1 && !visited[xx + 1][yy]) flood(xx + 1, yy)
                            if (xx > 0 && !visited[xx - 1][yy]) flood(xx - 1, yy)
                            if (yy < h-1 && !visited[xx][yy + 1]) flood(xx, yy + 1)
                            if (yy < 0 && !visited[xx][yy - 1]) flood(xx, yy - 1)
                        }
                        flood(x, y)

                        startPointsWithMost[GridPoint2(x, y)] = count
                    }
                }
            }
        }

        println(startPointsWithMost)

        val visited2 = map.clone().map { it.clone() }
        fun flood(xx: Int, yy: Int) {
            visited2[xx][yy] = true
            map[xx][yy] = true

            if (xx < w-1 && !visited[xx + 1][yy]) flood(xx + 1, yy)
            if (xx > 0 && !visited[xx - 1][yy]) flood(xx - 1, yy)
            if (yy < h-1 && !visited[xx][yy + 1]) flood(xx, yy + 1)
            if (yy < 0 && !visited[xx][yy - 1]) flood(xx, yy - 1)
        }

        val pt = startPointsWithMost.maxBy { it.value }
        startPointsWithMost.filterNot { it.key == pt!!.key }.forEach {
            flood(it.key.x, it.key.y)
        }

        val startPos = GridPoint2()
        while (map[startPos.x][startPos.y]) {
            startPos.set((Math.random() * w).toInt(), (Math.random() * h).toInt())
        }

        val endPos = (0..20).map {
            val pos = GridPoint2()
            while (map[pos.x][pos.y]) {
                pos.set((Math.random() * w).toInt(), (Math.random() * h).toInt())
            }
            pos
        }.maxBy {
            Math.abs(it.x - startPos.x) + Math.abs(it.y - startPos.y)
        }

        return Map(map, startPos, endPos!!, w, h)
    }
}