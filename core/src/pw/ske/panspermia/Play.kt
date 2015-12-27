package pw.ske.panspermia

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.gen.LevelGenerator
import pw.ske.panspermia.gen.Map
import pw.ske.panspermia.system.*
import pw.ske.panspermia.ui.HUDUI

object Play : ScreenAdapter() {
    val engine = Engine().apply {
        addSystem(AttackMiniSpermS)
        addSystem(AttackOnClickS)
        addSystem(AttackPeriodicallyS)
        addSystem(AttackShootProjectileS)
        addSystem(BulletDeathS)
        addSystem(CameraControllerS)
        addSystem(DamageOnTouchS)
        addSystem(DamageS)
        addSystem(DashTowardsPlayerS)
        addSystem(DeathS)
        addSystem(DropGoldS)
        addSystem(HomingOnPlayerS)
        addSystem(KillOnHealthZeroS)
        addSystem(KillOnTouchS)
        addSystem(MapRendererS)
        addSystem(PhysicsS)
        addSystem(PickUpGoldS)
        addSystem(PlayAnimationOnPreAttackS)
        addSystem(PlayerMovementS)
        addSystem(PreAttackS)
        addSystem(RandomSpawnCellS)
        addSystem(ScreenShakeOnDamageS)
        addSystem(SoundOnAttackS)
        addSystem(SoundOnDamageS)
        addSystem(SoundOnDeathS)
        addSystem(SpriteRendererS)
        addSystem(UpgradeOnDeathS)

        addEntityListener(Family.all(BodyC::class.java).get(), object : EntityListener {
            override fun entityAdded(entity: Entity) {
            }

            override fun entityRemoved(entity: Entity) {
                world.destroyBody(entity.body)
            }
        })
    }
    lateinit var world: World

    val batch = SpriteBatch()
    val shapeRenderer = ShapeRenderer()

    val camera = OrthographicCamera()

    lateinit var map: Map

    lateinit var player: Entity

    init {
        show()
    }

    override fun show() {
        engine.removeAllEntities()

        world = World(Vector2(), true).apply {
            setContactListener(ContactListener)
        }

        map = LevelGenerator.genMap(100, 100).apply {
            placeWorld()
            placeEntities(100)
        }

        player = EntityCreator.createPlayer().apply {
            position = Vector2(map.start.x.toFloat(), map.start.y.toFloat())
            engine.addEntity(this)
        }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(41 / 255f, 140 / 255f, 238 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.update(delta)

        HUDUI.act(delta)
        HUDUI.draw()
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 8f, height / 8f)

        HUDUI.viewport.update(width, height, true)
    }
}