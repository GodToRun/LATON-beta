package laton.engine;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import vecutils.Vector4;
public class GLManager {
	public void create() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
        GL11.glClearDepth(1.0); 
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL); 

        GL11.glMatrixMode(GL11.GL_PROJECTION); 
        GL11.glLoadIdentity();

        
        GLU.gluPerspective(
          45.0f,
          (float)DisplayManager.SCREEN_WIDTH/(float)DisplayManager.SCREEN_HEIGHT,
          0.1f,
          1000.0f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}
}
