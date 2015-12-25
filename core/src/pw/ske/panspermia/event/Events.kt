package pw.ske.panspermia.event

import com.badlogic.ashley.signals.Signal

object Events {
    val PreAttack = Signal<PreAttackE>()
    val Attack = Signal<AttackE>()
    val EntityTouchFixture = Signal<EntityTouchFixtureE>()
    val Damage = Signal<DamageE>()
    val Death = Signal<DeathE>()
    val ScreenShake = Signal<ScreenShakeE>()
}