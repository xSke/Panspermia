package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.PolygonShape
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.PlayerMovementC
import pw.ske.panspermia.component.SpriteC

object EntityCreator {
    fun createPlayer(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody
        body.angularDamping = 10f

        val anim = Animation(0.04f, *TextureRegion.split(Texture("sperm.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.LOOP
        val sprite = AnimatedSprite(anim)
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()
        sprite.play()

        val shape = CircleShape()
        shape.radius = 0.49f

        val fix = body.createFixture(shape, 1f)

        val entity = Entity()
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(PlayerMovementC(5f, 10f))
        return entity
    }
}