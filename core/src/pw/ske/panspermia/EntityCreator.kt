package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Filter
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import pw.ske.panspermia.component.*
import pw.ske.panspermia.util.*

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

        shape.radius = 0.15f
        shape.position = Vector2(0f, 0.3f)
        val sensor = body.createFixture(shape, 1f)

        val hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"))
        val shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"))
        val deathSound = Gdx.audio.newSound(Gdx.files.internal("kill.wav"))

        val entity = Entity()
        body.userData = entity
        entity.add(FilterC(Category.Player))
        entity.add(BodyC(body))
        entity.add(SpriteC(sprite))
        entity.add(PlayerMovementC(5f, 10f))
        entity.add(AttackOnClickC(1 / GameState.fireRate.value))
        entity.add(AttackMiniSpermC(GameState.projectileSpeed.value, Vector2(0f, 0.75f), GameState.projectileCount.value))
        entity.add(HealthC(GameState.health.value))
        entity.add(ScreenShakeOnDamageC(0.4f, 0.13f))
        entity.add(SoundOnDamageC(hurtSound))
        entity.add(SoundOnAttackC(shootSound))
        entity.add(SoundOnDeathC(deathSound))
        entity.add(ScreenShakeOnDeathC(1.5f, 2f, true))

        if (GameState.shield.value > 0) {
            entity.add(ShieldC(GameState.shield.value, 3f))
        }
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
        entity.add(FilterC(Category.Scenery))
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

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.PlayerProjectile))
        entity.add(SpriteC(sprite))
        entity.add(DestroyOnTouchC(setOf(Category.Wall)))
        entity.add(DamageOnTouchC(1f, true))
        return entity
    }

    fun createProjectile(homing: Boolean): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody

        val sprite = Sprite(Texture("bullet.png"))
        sprite.setSize(0.25f, 0.25f)
        sprite.setOriginCenter()

        val shape = CircleShape()
        shape.radius = 0.125f

        val fix = body.createFixture(shape, 1f)
        fix.isSensor = true

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.EnemyProjectile))
        entity.add(SpriteC(sprite))
        entity.add(DestroyOnTouchC(setOf(Category.Wall)))
        entity.add(HealthC(1f))
        entity.add(DamageOnTouchC(1f, true))
        entity.add(DropGoldC(1))

        if (homing) {
            entity.add(HomingOnPlayerC(20f, 100f))
        }
        return entity
    }

    fun createAndAddBoss(pos: GridPoint2) {
        val bossCell = createBossCell()
        bossCell.position = Vector2(pos.x.toFloat(), pos.y.toFloat())
        Play.engine.addEntity(bossCell)

        val i = 32

        var last: Entity? = null
        var first: Entity? = null
        (0..i - 1).forEach {
            val seg = createBossSegment()

            seg.body.setTransform(
                    bossCell.position.add(Vector2(0f, 5f).rotate(it * (360f / i))),
                    it * (360f / i) * MathUtils.degreesToRadians
            )
            Play.engine.addEntity(seg)

            val cannon = createBossSmallCannon()
            cannon.body.setTransform(
                    seg.position.add(Vector2(0f, 0.5f).rotate(it * (360f / i))),
                    it * (360f / i) * MathUtils.degreesToRadians
            )
            Play.engine.addEntity(cannon)

            val cannonJoint = WeldJointDef()
            cannonJoint.initialize(seg.body, cannon.body, seg.position.add(Vector2(0f, 0.25f).rotate(it * (360f / i))))
            Play.world.createJoint(cannonJoint)

            if (first == null) first = seg;

            if (last != null) {
                val jd = RevoluteJointDef()
                jd.initialize(last!!.body, seg.body, seg.body.position.add(Vector2(0.5f, 0f).rotate(it * (360f / i))))
                jd.lowerAngle = jd.referenceAngle
                jd.upperAngle = jd.referenceAngle
                jd.enableLimit = true

                Play.world.createJoint(jd)
            }

            last = seg
        }

        val jd = RevoluteJointDef()
        jd.initialize(last!!.body, first!!.body, first!!.position.add(Vector2(0.5f, 0f)))
        jd.enableLimit = true
        jd.lowerAngle = -jd.referenceAngle
        jd.upperAngle = -jd.referenceAngle
        Play.world.createJoint(jd)
    }

    fun createBossCell(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody
        body.linearDamping = 1000f

        val sprite = Sprite(Texture("bosscell.png"))
        sprite.setSize(3f, 3f)
        sprite.setOriginCenter()

        val shape = PolygonShape()
        shape.setAsBox(1.5f, 1.5f)

        val fix = body.createFixture(shape, 1f)

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.BossCell))
        entity.add(SpriteC(sprite))
        return entity
    }

    fun createBossSegment(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody
        body.linearDamping = 10f

        val sprite = Sprite(Texture("bosssegment.png"))
        sprite.setSize(1f, 0.5f)
        sprite.setOriginCenter()

        val shape = PolygonShape()
        shape.setAsBox(0.5f, 0.25f)

        val fix = body.createFixture(shape, 1f)

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.BossShield))
        entity.add(SpriteC(sprite))
        entity.add(HealthC(500f))
        return entity
    }

    fun createBossSmallCannon(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody
        body.linearDamping = 10f

        val anim = Animation(0.15f, *TextureRegion.split(Texture("smallcannon.png"), 8, 8)[0])
        anim.playMode = Animation.PlayMode.NORMAL

        val sprite = AnimatedSprite(anim)
        sprite.setSize(0.5f, 0.5f)
        sprite.setOriginCenter()

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.BossShield))
        entity.add(SpriteC(sprite))
        entity.add(AttackPeriodicallyC(1f, 0.15f, Math.random().toFloat() * 1f))
        entity.add(PlayAnimationOnPreAttackC())
        entity.add(AttackShootProjectileC(10f, listOf(Vector2(0f, 0.5f)), false, true))
        return entity
    }

    fun createCannon(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.StaticBody

        val anim = Animation(0.15f, *TextureRegion.split(Texture("plop.png"), 16, 16)[0])
        anim.playMode = Animation.PlayMode.NORMAL

        val sprite = AnimatedSprite(anim)
        sprite.setSize(1f, 1f)
        sprite.setOriginCenter()

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.Scenery))
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
        val offset = Math.random().toFloat() * 1f

        val deathSound = Gdx.audio.newSound(Gdx.files.internal("kill.wav"))

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.Cell))
        entity.add(SpriteC(sprite))
        entity.add(AttackPeriodicallyC(1f, 0.15f, offset))
        entity.add(PlayAnimationOnPreAttackC())
        entity.add(AttackShootProjectileC(10f, listOf(Vector2(0f, 1f), Vector2(0f, -1f), Vector2(1f, 0f), Vector2(-1f, 0f)), false))
        entity.add(DashTowardsPlayerC(10f, 1f, offset))
        entity.add(HealthC(20f))
        entity.add(BulletDeathC(15f, 100))
        entity.add(SoundOnDeathC(deathSound))
        entity.add(ScreenShakeOnDeathC(0.8f, 0.2f, false))
        entity.add(DropGoldC(50))
        return entity
    }

    fun createDNA(): Entity {
        val body = Play.world.createBody(BodyDef())
        body.type = BodyDef.BodyType.DynamicBody

        val sprite = Sprite(Texture("gold.png"))
        sprite.setSize(0.125f, 0.125f)
        sprite.setOriginCenter()

        val shape = CircleShape()
        shape.radius = 0.0625f

        val fix = body.createFixture(shape, 1f)
        //fix.isSensor = true

        val entity = Entity()
        body.userData = entity
        entity.add(BodyC(body))
        entity.add(FilterC(Category.DNA))
        entity.add(SpriteC(sprite))
        entity.add(GoldC())
        entity.add(HomingOnPlayerC(10f, 100f))
        return entity
    }
}