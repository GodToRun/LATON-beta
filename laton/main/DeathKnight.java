package laton.main;

import org.lwjgl.opengl.GL11;

import laton.engine.Camera;
import laton.engine.Mesh;
import laton.engine.OBJLoader;
import laton.engine.Textures;
import vecutils.Vector3;
import vecutils.Vector4;

public class DeathKnight extends Knight {
	public DeathKnight(Game laton, Camera cam, Mesh mesh) {
		super(laton, cam, mesh);
		texture = Textures.loadTexture("ltex/deathknight.png", 9728);
		this.hp = 250;
	}
}
