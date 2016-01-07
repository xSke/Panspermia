package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite

data class SpriteC(val sprite: Sprite, val hueShift: Boolean = true): Component