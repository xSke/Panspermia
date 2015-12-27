package pw.ske.panspermia.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pw.ske.panspermia.Panspermia;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.backgroundFPS = -1;
        config.width = 800;
        config.height = 600;
        new LwjglApplication(Panspermia.INSTANCE, config);
    }
}
