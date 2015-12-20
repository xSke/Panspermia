package pw.ske.panspermia.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import pw.ske.panspermia.component.AttackOnClickC
import pw.ske.panspermia.event.AttackE
import pw.ske.panspermia.event.Events

object AttackOnClickS: IteratingSystem(Family.all(AttackOnClickC::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Events.Attack.dispatch(AttackE(entity))
        }
    }
}