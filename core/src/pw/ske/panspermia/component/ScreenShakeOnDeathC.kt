package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component

data class ScreenShakeOnDeathC(val strength: Float, val time: Float, var fade: Boolean): Component