package pw.ske.panspermia

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import pw.ske.panspermia.event.EntityTouchFixtureE
import pw.ske.panspermia.event.Events

object ContactListener: ContactListener {
    override fun endContact(contact: Contact?) {
    }

    override fun beginContact(contact: Contact) {
        if (contact.fixtureA.body.userData is Entity) {
            Events.EntityTouchFixture.dispatch(EntityTouchFixtureE(contact.fixtureA.body.userData as Entity, contact.fixtureB))
        }

        if (contact.fixtureB.body.userData is Entity) {
            Events.EntityTouchFixture.dispatch(EntityTouchFixtureE(contact.fixtureB.body.userData as Entity, contact.fixtureA))
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}