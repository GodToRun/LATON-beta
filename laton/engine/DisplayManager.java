package laton.engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
public class DisplayManager {
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 400;
	public static void createDisplay(String title) {
		try {
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
			Display.setTitle(title);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static boolean isDisplayShouldClose() {
		return Display.isCloseRequested();
	}
	public static void updateDisplay() {
		Display.update();
		Display.sync(60);
	}
}
