package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.component.DropGoldC
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.position

object DropGoldS: EntitySystem() {
    init {
        Events.Death.register(-1) { deathE ->
            val dg = deathE.entity.getComponent(DropGoldC::class.java)
            if (dg != null) {
                (0..dg.gold-1).forEach {
                    val gold = EntityCreator.createDNA()
                    //println(deathE.entity.position)
                    gold.position = deathE.entity.position

                    Play.engine.addEntity(gold)
                }
            }
            false
        }
    }
}