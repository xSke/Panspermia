package pw.ske.panspermia.system

import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.Vector2
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.position

object RandomSpawnCellS: IntervalSystem(1.5f) {
    override fun updateInterval() {
        val cell = if (Math.random() < 0.7) {
            EntityCreator.createCell()
        } else {
            EntityCreator.createVortex()

        }

        val pos = GridPoint2()
        while (true) {
            pos.set((Math.random() * Play.map.width).toInt(), (Math.random() * Play.map.height).toInt())

            val dist = Math.pow(pos.x - Play.player.position.x.toDouble(), 2.0) + Math.pow(pos.x - Play.player.position.x.toDouble(), 2.0)
            if (dist < 20) continue
            if (!Play.map.map[pos.x][pos.y]) break
        }
        cell.position = Vector2(pos.x.toFloat(), pos.y.toFloat())

        Play.engine.addEntity(cell)
    }
}