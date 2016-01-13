package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Filter
import pw.ske.panspermia.component.BodyC

val Entity.body: Body get() {
    return this.getComponent(BodyC::class.java).body
}

var Entity.position: Vector2 get() {
    return this.body.position
} set(value) {
    this.body.setTransform(value, this.body.angle)
}

fun contactFilter(filterA: Filter, filterB: Filter): Boolean {
    if (filterA.groupIndex == filterB.groupIndex && filterA.groupIndex.toInt() != 0) {
        return filterA.groupIndex > 0
    }

    val collide = (filterA.maskBits.toInt() and filterB.categoryBits.toInt()) != 0 && (filterA.categoryBits.toInt() and filterB.maskBits.toInt()) != 0
    return collide
}