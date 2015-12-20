package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Filter
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import pw.ske.panspermia.component.*

object EntityCreator {
    val PLAYER_CAT = 0b10
    val PLAYER_MASK = 0b1
    val PLAYER_SENSOR_MASK = 0b101

    val PROJECTILE_CAT = 0b100
    val PROJECTILE_MASK = 0b011;
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
        val filter = Filter()
        filter.categoryBits = PLAYER_CAT.toShort()
        filter.maskBits = PLAYER_MASK.toShort()
        fix.filterData = filter

        shape.radius = 0.15f
        shape.position = Vector2(0f, 0.3f)
        val sensor = body.createFixture(shape, 1f)
        val filter2 = Filter()
        filter2.categoryBits = PLAYER_CAT.toShort()
        filter2.maskBits = PLAYER_SENSOR_MASK.toShort()
        sensor.filterData = filter2

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(PlayerMovementC(5f, 10f))
        entity.add(AttackOnClickC())
        entity.add(AttackMiniSpermC(20f, Vector2(0f, 0.75f)))
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
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        return entity
    }

    fun createMiniSperm(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody

        val anim = Animation(0.03f, *TextureRegion.split(Texture("sperm.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.LOOP
        val sprite = AnimatedSprite(anim)
        sprite.setSize(0.4f, 0.4f)
        sprite.setOriginCenter()
        sprite.play()

        val shape = CircleShape()
        shape.radius = 0.2f

        val fix = body.createFixture(shape, 1f)
        val filter = Filter()
        filter.categoryBits = PROJECTILE_CAT.toShort()
        filter.maskBits = PROJECTILE_MASK.toShort()
        fix.filterData = filter

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(DestroyOnTouchC())
        return entity
    }

    fun createProjectile(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody

        val sprite = Sprite(Texture("bullet.png"))
        sprite.setSize(0.25f, 0.25f)
        sprite.setOriginCenter()

        val shape = CircleShape()
        shape.radius = 0.125f

        val fix = body.createFixture(shape, 1f)
        val filter = Filter()
        filter.categoryBits = PROJECTILE_CAT.toShort()
        filter.maskBits = PROJECTILE_MASK.toShort()
        fix.filterData = filter

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(DestroyOnTouchC())
        return entity
    }

    fun createCannon(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody

        val anim = Animation(0.15f, *TextureRegion.split(Texture("plop.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.NORMAL

        val sprite = AnimatedSprite(anim)
        sprite.time = Math.random().toFloat()
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(AttackPeriodicallyC(2f, 0.15f))
        entity.add(PlayAnimationOnPreAttackC())
        entity.add(AttackShootProjectileC(10f, Vector2(0f, 0.5f)))
        return entity
    }
}