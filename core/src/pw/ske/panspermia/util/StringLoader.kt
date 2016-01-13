package pw.ske.panspermia.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array

class StringLoader(resolver: com.badlogic.gdx.assets.loaders.FileHandleResolver): AsynchronousAssetLoader<String, StringLoader.StringParameter>(resolver) {
    var str: String? = null

    override fun loadAsync(manager: AssetManager?, fileName: String?, file: FileHandle?, parameter: StringParameter?) {
        str = file!!.readString()
    }

    override fun loadSync(manager: AssetManager?, fileName: String?, file: FileHandle?, parameter: StringParameter?): String? {
        val str = file!!.readString()
        this.str = null
        return str
    }

    override fun getDependencies(fileName: String?, file: FileHandle?, parameter: StringParameter?): Array<AssetDescriptor<Any>>? {
        return null
    }

    class StringParameter: AssetLoaderParameters<String>()
}