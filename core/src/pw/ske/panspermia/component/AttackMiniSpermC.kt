package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

data class AttackMiniSpermC(val speed: Float, val offset: Vector2, val split: Int): Component