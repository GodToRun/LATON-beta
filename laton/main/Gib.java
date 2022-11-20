package laton.main;

import laton.engine.AABB;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Texture;
import laton.engine.Textures;
import vecutils.Vector3;

public class Gib {
	GibObj[] gibs = new GibObj[5];
	Game laton;
	public Gib(Game laton,Vector3 pos) {
		this.laton = laton;
		Texture gibTex = Textures.loadTexture("ltex/gib.png", 9728);
		OBJLoader loader = new OBJLoader("lmodels/gib.obj");
		for (int i = 0; i < gibs.length; i++) {
			gibs[i] = new GibObj(loader);
			gibs[i].setPosition(pos);
			gibs[i].create();
			gibs[i].texture = gibTex;
			gibs[i].velocity.y = 0.2f + ((float)Math.random() * 0.5f);
			gibs[i].velocity.x = 0.5f - ((float)Math.random() * 1.2f);
			gibs[i].velocity.z = 0.5f - ((float)Math.random() * 1.2f);
			gibs[i].gravity = -0.025f;
			gibs[i].sizeAABB = new Vector3(1, 1, 1);
			gibs[i].compareAABB = laton.mapAABB;
			gibs[i].inGround = false;
		}
	}
}
