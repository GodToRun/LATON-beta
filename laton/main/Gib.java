package laton.main;

import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Texture;
import laton.engine.Textures;
import vecutils.Vector3;

public class Gib {
	GameObject[] gibs = new GameObject[7];
	public Gib(Vector3 pos) {
		Texture gibTex = Textures.loadTexture("ltex/gib.png", 9728);
		OBJLoader loader = new OBJLoader("lmodels/gib.obj");
		for (int i = 0; i < gibs.length; i++) {
			gibs[i] = new GameObject(loader);
			gibs[i].setPosition(pos);
			gibs[i].create();
			gibs[i].texture = gibTex;
			gibs[i].velocity.y = 0.7f;
			gibs[i].velocity.x = 0.5f - ((float)Math.random() * 1.2f);
			gibs[i].velocity.z = 0.5f - ((float)Math.random() * 1.2f);
			gibs[i].gravity = -0.025f;
			gibs[i].inGround = false;
		}
	}
}
