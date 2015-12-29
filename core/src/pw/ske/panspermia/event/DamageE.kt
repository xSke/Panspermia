package pw.ske.panspermia.event

import com.badlogic.ashley.core.Entity

data class DamageE(val entity: Entity, val damage: Float, val source: Entity?)