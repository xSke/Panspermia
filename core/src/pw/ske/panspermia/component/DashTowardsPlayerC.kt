package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component

data class DashTowardsPlayerC(val speed: Float, val interval: Float, var counter: Float = 0f): Component