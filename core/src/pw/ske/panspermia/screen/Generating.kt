package pw.ske.panspermia.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import pw.ske.panspermia.GameState
import pw.ske.panspermia.Panspermia
import pw.ske.panspermia.event.Events
import pw.ske.panspermia.event.TitleE
import pw.ske.panspermia.gen.LevelGenerator
import pw.ske.panspermia.gen.Map
import pw.ske.panspermia.ui.GeneratingUI
import pw.ske.panspermia.ui.HUDUI
import pw.ske.panspermia.util.ContactFilter
import pw.ske.panspermia.util.ContactListener
import pw.ske.panspermia.util.Palette

object Generating : ScreenAdapter() {
    enum class State {
        INIT,
        CREATE_WORLD,
        TILES,
        WALLS,
        BOSS,
        COLLISION,
        DONE
    }

    init {
        show()
    }

    var state = State.INIT
    var counter = 0

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(41 / 255f, 140 / 255f, 238 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        GeneratingUI.act(delta)
        GeneratingUI.draw()

        when (state) {
            State.INIT -> {
                GameState.level += 1
                Play.palette = Palette.generate()

                // Load the HUDUI class so the event handler will register itself
                HUDUI.hashCode()

                Events.Title.dispatch(TitleE(" - Level ${GameState.level} -"))

                state = State.CREATE_WORLD

                GeneratingUI.status.setText("Creating world...")
            }
            State.CREATE_WORLD -> {
                Play.engine.removeAllEntities()

                Play.world = World(Vector2(), true).apply {
                    setContactListener(ContactListener)
                    setContactFilter(ContactFilter)
                }

                state = State.TILES
                GeneratingUI.status.setText("Generating tiles...")
            }
            State.TILES -> {
                val map = LevelGenerator.genMap(200, 200)
                Play.map = map

                state = State.WALLS
                GeneratingUI.status.setText("Placing wall objects...")
            }
            State.WALLS -> {
                Play.map.placeWallObjects(400)

                state = State.BOSS
                GeneratingUI.status.setText("Placing boss...")
            }
            State.BOSS -> {
                Play.map.placeBoss()

                state = State.COLLISION
                GeneratingUI.status.setText("Placing collision mesh...")
            }
            State.COLLISION -> {
                Play.map.placeWorld()

                state = State.DONE
            }
            State.DONE -> {
                Panspermia.setScreen(Play)
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        GeneratingUI.viewport.update(width, height, true)
    }

}