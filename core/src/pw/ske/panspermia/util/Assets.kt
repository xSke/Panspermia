package pw.ske.panspermia.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont

object Assets {
    val manager = AssetManager().apply {
        val files = listOf(
                "audio/blip.wav",
                "audio/coin.wav",
                "audio/hurt.wav",
                "audio/kill.wav",
                "audio/shoot.wav",
                "font/font.fnt",
                "shaders/default.frag",
                "shaders/default.vert",
                "shaders/hsv_shift.frag",
                "sprites/blazeit.png",
                "sprites/blur.png",
                "sprites/bosscell.png",
                "sprites/bosssegment.png",
                "sprites/bullet.png",
                "sprites/cell.png",
                "sprites/gold.png",
                "sprites/plop.png",
                "sprites/shield.png",
                "sprites/smallcannon.png",
                "sprites/sperm.png",
                "sprites/vortex.png",
                "ui/bluedown.9.png",
                "ui/blueup.9.png",
                "ui/greydown.9.png",
                "ui/greyup.9.png",
                "ui/logo.png",
                "ui/reddown.9.png",
                "ui/redup.9.png"
        )

        setLoader(String::class.java, StringLoader(fileHandleResolver))

        files.forEach {
            load(it, if (it.endsWith(".wav")) {
                Sound::class.java
            } else if (it.endsWith(".png")) {
                Texture::class.java
            } else if (it.endsWith(".fnt")) {
                BitmapFont::class.java
            } else if (it.endsWith(".frag") || it.endsWith(".vert")) {
                String::class.java
            } else {
                throw RuntimeException("Unknown file type")
            })
        }
    }
}