package laton.main;

import laton.engine.Mesh;
import laton.engine.Textures;

public class GunPickable extends Pickable {
	Gun gun;
	Game laton;
	public GunPickable(Game laton, Gun gun, Mesh mesh, String texture) {
		super(mesh, 1f);
		this.texture = Textures.loadTexture(texture, 9728);
		this.laton = laton;
		this.gun = gun;
	}

	@Override
	public void onPick() {
		laton.gun = gun;
		laton.updateGun();
		setActive(false);
	}

}
