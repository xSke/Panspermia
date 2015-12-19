package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import pw.ske.panspermia.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.SpriteC
import pw.ske.panspermia.position

object SpriteRendererS: IteratingSystem(Family.all(BodyC::class.java, SpriteC::class.java).get(), 1000) {
    override fun update(deltaTime: Float) {
        Play.batch.begin()
        super.update(deltaTime)
        Play.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val sprite = entity.getComponent(SpriteC::class.java).sprite

        sprite.setPosition(entity.position.x - sprite.width / 2, entity.position.y - sprite.height / 2)
        sprite.rotation = entity.body.angle * MathUtils.radiansToDegrees

        sprite.draw(Play.batch)
    }
}