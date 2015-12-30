package pw.ske.panspermia.util

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Fixture
import pw.ske.panspermia.component.FilterC
import java.util.*

data class Pair(val a: Category, val b: Category) {
    // Intentionally weak hashCode, so the order doesn't matter
    override fun hashCode(): Int {
        return a.hashCode() * b.hashCode()
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Pair) return false;
        return hashCode() == other.hashCode();
    }
}

data class Category(val name: String) {
    companion object {
        val collideMap: HashMap<Pair, Boolean> = hashMapOf()

        val Any = Category("Any")

        val Wall = Category("Wall")
        val Player = Category("Player")
        val PlayerProjectile = Category("PlayerProjectile")
        val EnemyProjectile = Category("EnemyProjectile")
        val Cell = Category("Cell")
        val BossCell = Category("BossCell")
        val BossShield = Category("BossShield")
        val DNA = Category("DNA")
        val Scenery = Category("Scenery")

        init {
            collideMap[Pair(EnemyProjectile, EnemyProjectile)] = false
            collideMap[Pair(PlayerProjectile, PlayerProjectile)] = false
            collideMap[Pair(Player, Cell)] = false
            collideMap[Pair(Player, PlayerProjectile)] = false
            collideMap[Pair(Cell, EnemyProjectile)] = false
            collideMap[Pair(Scenery, EnemyProjectile)] = false
        }

        fun getCategory(fixture: Fixture): Category {
            return if (fixture.userData is Category) {
                fixture.userData as Category
            } else {
                val e = fixture.body.userData

                if (e is Entity) {
                    e.getComponent(FilterC::class.java)?.category!!
                } else {
                    return Any
                }
            }
        }

        fun matches(category: Category, filter: Set<Category>): Boolean {
            if (Any in filter) return true;
            return category in filter;
        }
    }
}