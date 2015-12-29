package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component

data class ShieldC(val shieldHits: Int, val shieldRechargeTime: Float, var hitCounter: Int = 0, var rechargeCounter: Float = 0f, var enabled: Boolean = false): Component