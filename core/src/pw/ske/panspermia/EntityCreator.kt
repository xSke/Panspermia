package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
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
    val PLAYER_MASK = 0b101001
    val PLAYER_SENSOR_MASK = 0b1101

    val SPERM_PROJECTILE_CAT = 0b100
    val SPERM_PROJECTILE_MASK = 0b11011
    val BULLET_PROJECTILE_CAT = 0b1000
    val BULLET_PROJECTILE_MASK = 0b111

    val CELL_CAT = 0b10000
    val CELL_MASK = 0b00101

    val GOLD_CAT = 0b100000
    val GOLD_MASK = 0b11

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

        val hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"))
        val shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"))
        val deathSound = Gdx.audio.newSound(Gdx.files.internal("kill.wav"))

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(PlayerMovementC(5f, 10f))
        entity.add(AttackOnClickC())
        entity.add(AttackMiniSpermC(20f, Vector2(0f, 0.75f)))
        entity.add(HealthC(10f))
        entity.add(ScreenShakeOnDamageC(0.4f, 0.13f))
        entity.add(SoundOnDamageC(hurtSound))
        entity.add(SoundOnAttackC(shootSound))
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
        fix.isSensor = true
        val filter = Filter()
        filter.categoryBits = SPERM_PROJECTILE_CAT.toShort()
        filter.maskBits = SPERM_PROJECTILE_MASK.toShort()
        fix.filterData = filter

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(KillOnTouchC())
        entity.add(DamageOnTouchC(1f))
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
        fix.isSensor = true
        val filter = Filter()
        filter.categoryBits = BULLET_PROJECTILE_CAT.toShort()
        filter.maskBits = BULLET_PROJECTILE_MASK.toShort()
        fix.filterData = filter

        val deathSound = Gdx.audio.newSound(Gdx.files.internal("kill.wav"))

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(KillOnTouchC(true))
        entity.add(HealthC(1f))
        entity.add(DamageOnTouchC(1f))
        //entity.add(SoundOnDeathC(deathSound))
        entity.add(DropGoldC(1))
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
        entity.add(AttackPeriodicallyC(1f, 0.15f, Math.random().toFloat() * 1f))
        entity.add(PlayAnimationOnPreAttackC())
        entity.add(AttackShootProjectileC(10f, listOf(Vector2(0f, 0.5f)), true))
        return entity
    }

    fun createCell(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody
        body.angularVelocity = Math.random().toFloat() * 2 - 1
        body.linearDamping = 1f

        val anim = Animation(0.15f, *TextureRegion.split(Texture("cell.png"), 32, 32)[0])
        anim.playMode = Animation.PlayMode.NORMAL

        val sprite = AnimatedSprite(anim)
        sprite.time = Math.random().toFloat()
        sprite.setSize(2f, 2f)
        sprite.setOriginCenter()

        val shape = CircleShape()
        shape.radius = 1f

        val fix = body.createFixture(shape, 1f)
        val filter = Filter()
        filter.categoryBits = CELL_CAT.toShort()
        filter.maskBits = CELL_MASK.toShort()
        fix.filterData = filter

        val offset = Math.random().toFloat() * 1f

        val deathSound = Gdx.audio.newSound(Gdx.files.internal("kill.wav"))

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(AttackPeriodicallyC(1f, 0.15f, offset))
        entity.add(PlayAnimationOnPreAttackC())
        entity.add(AttackShootProjectileC(10f, listOf(Vector2(0f, 1f), Vector2(0f, -1f), Vector2(1f, 0f), Vector2(-1f, 0f)), false))
        entity.add(DashTowardsPlayerC(10f, 1f, offset))
        entity.add(HealthC(20f))
        entity.add(BulletDeathC(15f, 100))
        entity.add(SoundOnDeathC(deathSound))
        entity.add(ScreenShakeOnDamageC(0.6f, 0.07f))
        entity.add(DropGoldC(50))
        return entity
    }

    fun createGold(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody

        val sprite = Sprite(Texture("gold.png"))
        sprite.setSize(0.125f, 0.125f)
        sprite.setOriginCenter()

        val shape = CircleShape()
        shape.radius = 0.0625f

        val fix = body.createFixture(shape, 1f)
        fix.isSensor = true

        val filter = Filter()
        filter.categoryBits = GOLD_CAT.toShort()
        filter.maskBits = GOLD_MASK.toShort()
        fix.filterData = filter

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(GoldC())
        entity.add(HomingOnPlayerC(10f, 100f))
        return entity
    }
}