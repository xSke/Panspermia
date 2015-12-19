package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import pw.ske.panspermia.component.BodyC

val Entity.body: Body get() {
    return this.getComponent(BodyC::class.java).body
}

var Entity.position: Vector2 get() {
    return this.body.position
} set(value) {
    this.body.setTransform(value, this.body.angle)
}