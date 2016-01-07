package pw.ske.panspermia.util

import com.badlogic.gdx.graphics.Color

data class Palette(
        // Default hue is 210, blue-azure-ish, hue shift field is for a delta
        val backgroundColor: Color,
        val wallColor: Color,
        val hueShift: Float
) {
    companion object {
        fun generate(): Palette {
            val hueShift = Math.random().toFloat() * 360
            val fixedHue = (210f + hueShift) % 360

            val backgroundColor = Color()
            Color.rgb888ToColor(backgroundColor, java.awt.Color.HSBtoRGB(fixedHue / 360f, 83f / 100f, 93f / 100f))
            backgroundColor.a = 1f

            val wallColor = Color()
            Color.rgb888ToColor(wallColor, java.awt.Color.HSBtoRGB(fixedHue / 360f, 92f / 100f, 87f / 100f))
            wallColor.a = 1f

            return Palette(backgroundColor, wallColor, hueShift)
        }
    }
}