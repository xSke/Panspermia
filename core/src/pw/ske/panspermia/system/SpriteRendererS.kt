package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import pw.ske.panspermia.screen.Play
import pw.ske.panspermia.body
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.SpriteC
import pw.ske.panspermia.position

object SpriteRendererS : IteratingSystem(Family.all(BodyC::class.java, SpriteC::class.java).get(), 1000) {
    override fun update(deltaTime: Float) {
        Play.batch.begin()
        super.update(deltaTime)
        Play.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val sc = entity.getComponent(SpriteC::class.java)
        val sprite = sc.sprite

        sprite.setPosition(entity.position.x - sprite.width / 2, entity.position.y - sprite.height / 2)
        sprite.rotation = entity.body.angle * MathUtils.radiansToDegrees

        // If the lightness is 1f it starts fucking up so idk whats goin on here
        Play.hueShiftShader.setUniformf("vHSV", Vector3(Play.palette.hueShift, 1f, 1f))

        if (sc.hueShift) {
            if (Play.batch.shader != Play.hueShiftShader) {
                Play.batch.shader = Play.hueShiftShader
            }
        } else {
            if (Play.batch.shader == Play.hueShiftShader) {
                Play.batch.shader = null
            }
        }

        sprite.draw(Play.batch)
    }
}