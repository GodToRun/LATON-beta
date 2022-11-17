package laton.main;

import laton.engine.AABB;
import laton.engine.Camera;
import laton.engine.ForceMode;
import laton.engine.GameObject;
import laton.engine.Mesh;
import vecutils.Vector3;
import vecutils.Vector4;

public class Rocket extends GameObject {
	Vector3 forward;
	float speed;
	Game laton;
	AABB rocketAABB = new AABB(Vector3.ZERO(), Vector3.ZERO());
	Camera cam;
	int damage;
	boolean isExplode = false;
	public Rocket(Game laton, Camera cam, Mesh mesh, float speed, int damage, boolean isExplode) {
		super(mesh);
		this.cam = cam;
		this.damage = damage;
		this.laton = laton;
		this.speed = speed;
		this.rotation = new Vector4(0, 1, 0, -cam.getRotation().y);
		forceMode = ForceMode.Immediate;
		forward = cam.forward.copy();
		velocity = velocity.sub(forward.mul(speed));
		this.isExplode = isExplode;
	}
	void explode() {
		ExplosionObject obj = new ExplosionObject();
		obj.setPosition(getPosition());
		obj.create();
		if (isExplode) {
			Vector3 l = cam.getPosition();
			Vector3 r = getPosition();
			float xi = (l.x - r.x);
			float yi = (l.y - r.y);
			float zi = (l.z - r.z);
			Vector3 dis = new Vector3(xi, yi, zi).normalized();
			if (dis.x != 0)
				dis.x /= Math.abs(xi);
			if (dis.y != 0)
				dis.y /= Math.abs(yi);
			if (dis.z != 0)
				dis.z /= Math.abs(zi);
			cam.velocity = cam.velocity.add(dis.mul(1));
		}
	}
	@Override
	public void update() {
		super.update();
		if (!active()) return;
		rocketAABB.min = getPosition();
		rocketAABB.max = getPosition().add(new Vector3(0.5f, 0.5f, 0.5f));
		aabbUpdate(rocketAABB, laton.mapAABB);
		if (isSideCollisioned) {
			explode();
			setActive(false);
			return;
		}
		for (Monster mon : laton.monsters) {
			if (!mon.active() || mon.died) continue;
			Vector3 dis = getPosition().distance(mon.getPosition());
			if (dis.x < 4f && dis.y < 6f && dis.z < 4f) {
				explode();
				mon.damage(damage);
				setActive(false);
				return;
			}
		}
	}

}
