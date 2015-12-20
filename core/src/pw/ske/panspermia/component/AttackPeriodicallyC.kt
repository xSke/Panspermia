package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component

data class AttackPeriodicallyC(val interval: Float, val preAttackDelay: Float, var counter: Float = 0f): Component