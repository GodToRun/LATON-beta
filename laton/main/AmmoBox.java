package laton.main;

import laton.engine.Mesh;
import laton.engine.Textures;
import vecutils.Vector3;

public class AmmoBox extends Pickable {
	Game laton;
	public AmmoBox(Game laton, Mesh mesh, float rotate, String tex) {
		super(mesh, rotate);
		setScale(new Vector3(0.6f, 0.6f, 0.6f));
		this.texture = Textures.loadTexture(tex, 9728);
		this.laton = laton;
	}
	@Override
	public void onPick() {
		this.laton.playerAMMO += 5;
		setActive(false);
	}

}
