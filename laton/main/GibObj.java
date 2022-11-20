package laton.main;

import laton.engine.GameObject;
import laton.engine.Mesh;
import vecutils.Vector4;

public class GibObj extends GameObject {

	public GibObj(Mesh mesh) {
		super(mesh);
		this.rotation = new Vector4(0, 0, 1, 0);
		bounce = 0.2f + (float)Math.random() / 1.2f;
	}
	public void update() {
		super.update();
		if (!isSideCollisioned && !inGround) {
			this.rotation.t += 12;
		}
	}
}
