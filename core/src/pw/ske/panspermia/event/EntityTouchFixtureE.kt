package pw.ske.panspermia.event

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Fixture

data class EntityTouchFixtureE(val entity: Entity, val fixture: Fixture)