package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.PolygonShape
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import pw.ske.panspermia.component.*

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

    fun createWeed(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody

        val anim = Animation(0.5f, *TextureRegion.split(Texture("blazeit.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.LOOP

        val sprite = AnimatedSprite(anim)
        sprite.time = Math.random().toFloat()
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()

        val entity = Entity()
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        return entity
    }

    fun createCannon(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody

        val anim = Animation(0.1f, *TextureRegion.split(Texture("plop.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.NORMAL

        val sprite = AnimatedSprite(anim)
        sprite.time = Math.random().toFloat()
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()

        val entity = Entity()
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(AttackPeriodicallyC(2f, 0.2f))
        entity.add(PlayAnimationOnPreAttackC())
        return entity
    }
}