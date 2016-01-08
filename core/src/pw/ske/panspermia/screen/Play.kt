package pw.ske.panspermia.screen

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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Timer
import pw.ske.panspermia.EntityCreator
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.gen.LevelGenerator
import pw.ske.panspermia.gen.Map
import pw.ske.panspermia.position
import pw.ske.panspermia.system.*
import pw.ske.panspermia.ui.HUDUI
import pw.ske.panspermia.util.ContactFilter
import pw.ske.panspermia.util.ContactListener
import pw.ske.panspermia.util.Palette

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
        addSystem(DestroyOnTouchS)
        addSystem(DropGoldS)
        addSystem(HomingOnPlayerS)
        addSystem(KillOnHealthZeroS)
        addSystem(KnockbackS)
        addSystem(MapRendererS)
        addSystem(PhysicsS)
        addSystem(PickUpGoldS)
        addSystem(PlayAnimationOnPreAttackS)
        addSystem(PlayerMovementS)
        addSystem(PreAttackS)
        addSystem(RandomSpawnCellS)
        addSystem(ScreenShakeOnAttackS)
        addSystem(ScreenShakeOnDamageS)
        addSystem(ScreenShakeOnDeathS)
        addSystem(ShieldRendererS)
        addSystem(ShieldS)
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

    lateinit var palette: Palette

    override fun show() {
        palette = Palette.generate()

        player = EntityCreator.createPlayer().apply {
            position = Vector2(map.start.x.toFloat(), map.start.y.toFloat())
            engine.addEntity(this)
        }

        Gdx.input.inputProcessor = null
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(palette.backgroundColor.r, palette.backgroundColor.g, palette.backgroundColor.b, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.update(delta)

        HUDUI.act(delta)
        HUDUI.draw()

        //Box2DDebugRenderer().render(world, camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 64f, height / 64f)

        HUDUI.viewport.update(width, height, true)
    }
}