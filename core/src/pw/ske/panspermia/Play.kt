package pw.ske.panspermia

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import pw.ske.panspermia.system.ShipFlyS
import pw.ske.panspermia.system.SpriteRendererS

object Play: ScreenAdapter() {
    val engine = Engine().apply {
        addSystem(ShipFlyS)
        addSystem(SpriteRendererS)
    }
    val world = World(Vector2(), true)
    val batch = SpriteBatch()

    val camera = OrthographicCamera()

    val player = EntityCreator.createPlayer().apply {
        engine.addEntity(this)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined

        world.step(delta, 5, 5)
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 64f, height / 64f)
    }
}