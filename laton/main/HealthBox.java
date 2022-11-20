package laton.main;

import laton.engine.Mesh;
import laton.engine.Textures;

public class HealthBox extends Pickable {
	Game laton;
	public HealthBox(Game laton, Mesh mesh) {
		super(mesh, 0);
		this.texture = Textures.loadTexture("ltex/healthbox.png", 9728);
		this.laton = laton;
	}
	@Override
	public void onPick() {
		this.laton.playerHP += 20;
		setActive(false);
	}

}
