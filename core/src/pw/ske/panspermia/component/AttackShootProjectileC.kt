package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

data class AttackShootProjectileC(val speed: Float, val offset: List<Vector2>, val towardsPlayer: Boolean, val homing: Boolean = false): Component