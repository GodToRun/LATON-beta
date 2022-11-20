package laton.main;

import laton.engine.Camera;
import laton.engine.Mesh;
import laton.engine.Textures;

public class Enforcer extends Grunt {

	public Enforcer(Game laton, Camera cam, Mesh mesh) {
		super(laton, cam, mesh);
		this.hp = 80;
		texture = Textures.loadTexture("ltex/enforcer.png", 9728);
	}

}
