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
import com.badlogic.gdx.physics.box2d.World
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.gen.LevelGenerator
import pw.ske.panspermia.system.*

object Play : ScreenAdapter() {
    val engine = Engine().apply {
        addSystem(AttackMiniSpermS)
        addSystem(AttackOnClickS)
        addSystem(AttackPeriodicallyS)
        addSystem(AttackShootProjectileS)
        addSystem(DamageS)
        addSystem(DestroyOnTouchS)
        addSystem(KillOnHealthZeroS)
        addSystem(MapRendererS)
        addSystem(PhysicsS)
        addSystem(PlayAnimationOnPreAttackS)
        addSystem(PlayerCameraS)
        addSystem(PlayerMovementS)
        addSystem(PreAttackS)
        addSystem(SpriteRendererS)

        addEntityListener(Family.all(BodyC::class.java).get(), object: EntityListener {
            override fun entityAdded(entity: Entity) {
            }

            override fun entityRemoved(entity: Entity) {
                world.destroyBody(entity.body)
            }
        })
    }
    val world = World(Vector2(), true).apply {
        setContactListener(ContactListener)
    }
    val batch = SpriteBatch()
    val shapeRenderer = ShapeRenderer()

    val camera = OrthographicCamera()

    val map = LevelGenerator.genMap(100, 100).apply {
        placeWorld()
        placeEntities()
    }

    val player = EntityCreator.createPlayer().apply {
        position = Vector2(map.start.x.toFloat(), map.start.y.toFloat())
        engine.addEntity(this)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(255 / 255f, 45 / 255f, 45 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.update(delta)

        //Box2DDebugRenderer().render(world, camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 64f, height / 64f)
    }
}