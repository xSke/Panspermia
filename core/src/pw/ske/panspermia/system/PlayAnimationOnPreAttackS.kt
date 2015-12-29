package pw.ske.panspermia.system

import com.badlogic.ashley.core.EntitySystem
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import pw.ske.panspermia.component.SpriteC
import pw.ske.panspermia.event.Events

object PlayAnimationOnPreAttackS: EntitySystem() {
    init {
        Events.PreAttack.add { preAttackE ->
            (preAttackE.entity.getComponent(SpriteC::class.java).sprite as AnimatedSprite).play()
            (preAttackE.entity.getComponent(SpriteC::class.java).sprite as AnimatedSprite).time = 0f
            false
        }
    }
}