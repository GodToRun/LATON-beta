package laton.main;

import laton.engine.Mesh;
import laton.engine.Textures;
import vecutils.Vector3;

public class AmmoBox extends Pickable {
	Game laton;
	public AmmoBox(Game laton, Mesh mesh) {
		super(mesh, 1f);
		setScale(new Vector3(0.5f, 0.5f, 0.5f));
		this.texture = Textures.loadTexture("ltex/backpack.png", 9728);
		this.laton = laton;
	}
	@Override
	public void onPick() {
		this.laton.playerAMMO += 5;
		setActive(false);
	}

}
