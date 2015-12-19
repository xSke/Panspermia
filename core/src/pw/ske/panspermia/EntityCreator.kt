package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
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

    fun createPlanet(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody
        body.setTransform(0f, 0f, Math.random().toFloat() * MathUtils.PI2)

        val sprite = Sprite(Texture("planet.png"))
        sprite.setSize(2f, 2f)
        sprite.setOriginCenter()
        sprite.color = Color(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat(), 1f)

        val shape = CircleShape()
        shape.radius = 1f

        val fix = body.createFixture(shape, 1f)

        val entity = Entity()
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        return entity
    }
}