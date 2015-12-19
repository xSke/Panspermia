package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.ShipFlyC
import pw.ske.panspermia.component.SpriteC

object EntityCreator {
    fun createPlayer(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody
        body.angularDamping = 10f

        val sprite = Sprite(Texture("ship.png"))
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()

        val shape = PolygonShape()
        shape.setAsBox(0.5f, 0.5f)

        val fix = body.createFixture(shape, 1f)

        val entity = Entity()
        entity.add(BodyC(body))
        entity.add(ShipFlyC(10f, 10f))
        entity.add(SpriteC(sprite))
        return entity
    }
}