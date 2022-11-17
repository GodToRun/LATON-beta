package laton.main;

import laton.engine.GameObject;
import laton.engine.Mesh;
import laton.engine.Quad;
import laton.engine.Textures;

public class ExplosionObject extends GameObject {
	
	public ExplosionObject() {
		super(new Quad());
		mesh.isBillboard = true;
		texture = Textures.loadTexture("ltex/exp1.png", 9728);
		animation = new ExplosionAnimation(this);
	}

}
