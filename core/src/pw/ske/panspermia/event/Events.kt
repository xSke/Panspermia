package pw.ske.panspermia.event

import pw.ske.panspermia.util.Signal

object Events {
    val PreAttack = Signal<PreAttackE>()
    val Attack = Signal<AttackE>()
    val EntityTouchFixture = Signal<EntityTouchFixtureE>()
    val Damage = Signal<DamageE>()
    val Death = Signal<DeathE>()
    val ScreenShake = Signal<ScreenShakeE>()
}