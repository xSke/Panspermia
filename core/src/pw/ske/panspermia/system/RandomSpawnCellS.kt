package pw.ske.panspermia.system

import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.position

object RandomSpawnCellS: IntervalSystem(4f) {
    override fun updateInterval() {
        val cell = EntityCreator.createCell()

        val pos = GridPoint2()
        while (Play.map.map[pos.x][pos.y]) {
            pos.set((Math.random() * Play.map.width).toInt(), (Math.random() * Play.map.height).toInt())
        }
        cell.position = Vector2(pos.x.toFloat(), pos.y.toFloat())

        Play.engine.addEntity(cell)
    }
}