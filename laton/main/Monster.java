package laton.main;

import laton.engine.Camera;
import laton.engine.GameObject;
import laton.engine.Mesh;
import vecutils.Vector3;
import vecutils.Vector4;

public class Monster extends GameObject {
	public Camera cam;
	public boolean died = false;
	public int hp = 40;
	int rotTick = 0;
	public boolean isInWater = false;
	public boolean haveAI = true;
	public Monster(Camera cam, Mesh mesh) {
		super(mesh);
		this.cam = cam;
		this.rotation = new Vector4(0, 1, 0, 0);
	}
	@Override
	public void update() {
		super.update();
		if (!died && haveAI)
			onAI();
	}
	public void moveToPlayer(GameObject target, float speed) {
		Vector3 dir = target.getPosition().sub(getPosition()).normalized();
		dir.y = 0;
		move(dir.mul(speed));
	}
	public void lookPlayer() {
		Vector3 dir = cam.getPosition().sub(getPosition()).normalized();
		dir.y = 0;
		float len = -(float)Math.toDegrees(Math.asin(dir.x)) + (float)Math.toDegrees(Math.acos(dir.z));
		this.rotation.t = len + 150;
	}
	public void onAI() {
		rotTick++;
		if (rotTick > 100) {
			if (Math.random() < 0.5 || rotation.t < 42) {
				rotation.t += 42;
				rotation.t %= 360;
			}
			else{
				rotation.t -= 42;
			}
			rotTick = 0;
		}
	}
	public void damage(int hp) {
		this.hp -= hp;
		if (this.hp <= 0) {
			onDeath();
		}
	}
	public void onDeath() {
		died = true;
		aniTick = 0;
	}
}
