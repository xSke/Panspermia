package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.DropGoldC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object DropGoldS: EntitySystem() {
    init {
        Events.Death.add { signal, deathE ->
            val dg = deathE.entity.getComponent(DropGoldC::class.java)
            if (dg != null) {
                (0..dg.gold-1).forEach {
                    val gold = EntityCreator.createGold()
                    gold.position = deathE.entity.position

                    Play.engine.addEntity(gold)
                }
            }
        }
    }
}