package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component

data class KillOnTouchC(val destroyIfNonPlayer: Boolean = false) : Component