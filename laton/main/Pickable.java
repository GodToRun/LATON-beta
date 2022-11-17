package laton.main;

import laton.engine.GameObject;
import laton.engine.Mesh;
import vecutils.Vector4;

public abstract class Pickable extends GameObject {
	float rotateSpeed;
	public Pickable(Mesh mesh, float rotateSpeed) {
		super(mesh);
		this.rotation = new Vector4(0, 1, 0, 0);
		this.rotateSpeed = rotateSpeed;
	}
	public abstract void onPick();
	@Override
	public void update() {
		super.update();
		this.rotation = this.rotation.add(new Vector4(0, 0, 0, rotateSpeed));
		this.rotation.t %= 360;
	}

}
