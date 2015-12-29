package pw.ske.panspermia.util

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object NumericalSpringing {
    // Source: http://allenchou.net/2015/04/game-math-precise-control-over-numeric-springing/

    data class SpringResult(var value: Float, var velocity: Float)

    private val springResult = SpringResult(0f, 0f)

    fun spring(value: Float, velocity: Float, target: Float, dampingRatio: Float, angularFrequency: Float, deltaTime: Float): SpringResult {
        var angularFrequency = angularFrequency
        angularFrequency *= MathUtils.PI2

        val f = 1.0f + 2.0f * deltaTime * dampingRatio * angularFrequency
        val oo = angularFrequency * angularFrequency
        val hoo = deltaTime * oo
        val hhoo = deltaTime * hoo
        val detInv = 1.0f / (f + hhoo)
        val detX = f * value + deltaTime * velocity + hhoo * target
        val detV = velocity + hoo * (target - value)
        springResult.value = detX * detInv
        springResult.velocity = detV * detInv
        return springResult
    }

    // Keep in mind positionOut and velocityOut is both read from AND set to.
    fun springVector(positionOut: Vector2, velocityOut: Vector2, target: Vector2, dampingRatio: Float, angularFrequency: Float, deltaTime: Float) {
        var result = spring(positionOut.x, velocityOut.x, target.x, dampingRatio, angularFrequency, deltaTime)
        val xValue = result.value
        val xVelocity = result.velocity

        result = spring(positionOut.y, velocityOut.y, target.y, dampingRatio, angularFrequency, deltaTime)
        val yValue = result.value
        val yVelocity = result.velocity

        positionOut.set(xValue, yValue)
        velocityOut.set(xVelocity, yVelocity)
    }
}