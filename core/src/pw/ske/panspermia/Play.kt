package pw.ske.panspermia

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import pw.ske.panspermia.gen.LevelGenerator
import pw.ske.panspermia.system.MapRendererS
import pw.ske.panspermia.system.PlayerCameraS
import pw.ske.panspermia.system.PlayerMovementS
import pw.ske.panspermia.system.SpriteRendererS

object Play : ScreenAdapter() {
    val engine = Engine().apply {
        addSystem(MapRendererS)
        addSystem(PlayerCameraS)
        addSystem(PlayerMovementS)
        addSystem(SpriteRendererS)
    }
    val world = World(Vector2(), true)
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

    init {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(255 / 255f, 45 / 255f, 45 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(delta, 5, 5)
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 64f, height / 64f)
    }
}