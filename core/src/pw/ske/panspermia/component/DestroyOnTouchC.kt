package pw.ske.panspermia.component

import com.badlogic.ashley.core.Component
import pw.ske.panspermia.util.Category

data class DestroyOnTouchC(val filter: Set<Category> = setOf(Category.Any)) : Component