package pw.ske.panspermia.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pw.ske.panspermia.Panspermia;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Panspermia");
        config.setWindowedMode(1024, 768);
        new Lwjgl3Application(Panspermia.INSTANCE, config);
    }
}
