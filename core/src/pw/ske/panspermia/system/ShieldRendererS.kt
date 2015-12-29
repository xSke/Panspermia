package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import pw.ske.panspermia.Play
import pw.ske.panspermia.component.BodyC
import pw.ske.panspermia.component.ShieldC
import pw.ske.panspermia.position

object ShieldRendererS: IteratingSystem(Family.all(BodyC::class.java, ShieldC::class.java).get(), 1001) {
    val shield = Texture("shield.png")
    override fun update(deltaTime: Float) {
        Play.batch.begin()
        super.update(deltaTime)
        Play.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val s = entity.getComponent(ShieldC::class.java)

        if (s.enabled) {
            Play.batch.draw(shield, entity.position.x - 0.75f, entity.position.y - 0.75f, 1.5f, 1.5f)
        } else {
            Play.batch.color = Color(1f, 1f, 1f, MathUtils.lerp(0f, 0.3f, s.rechargeCounter / s.shieldRechargeTime))
            Play.batch.draw(shield, entity.position.x - 0.75f, entity.position.y - 0.75f, 1.5f, 1.5f)
            Play.batch.color = Color.WHITE
        }
    }
}