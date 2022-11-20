package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;
import vecutils.Vector3;

public class NailgunAnimation extends GunAnimation {
	GameObject shotgun;
	int tick = 0;
	int current_keyframe = 0;
	OBJLoader mesh;
	Sector n1, n2;
	public NailgunAnimation(GameObject shotgun, OBJLoader mesh) {
		ended = true;
		keyframes = 2;
		this.shotgun = shotgun;
		this.mesh = mesh;
		n1 = mesh.findSector("n1");
		n2 = mesh.findSector("n2");
	}
	@Override
	public void init() {
		//shotgun.getPosition().z += 0.7f;
	}
	@Override
	public void update() {
		if (!ended) {
			tick++;
			if (tick > 8) {
				onAnimation(current_keyframe);
				current_keyframe++;
				tick = 0;
				if (current_keyframe >= keyframes) {
					current_keyframe = 0;
					ended = true;
					return;
				}
			}
		}
		else {
			current_keyframe = 0;
			tick = 0;
		}
	}
	@Override
	public void onAnimation(int keyframe) {
		float zval = 0.15f;
		float yval = 0.015f;
		if (ended) return;
		if (keyframe == 0) {
			for (Vector3 v : n1.vecs) {
				v.z += zval;
				v.y += yval;
			}
			for (Vector3 v : n2.vecs) {
				v.z -= zval;
				v.y -= yval;
			}
		}
		else if (keyframe == 1) {
			for (Vector3 v : n1.vecs) {
				v.z -= zval;
				v.y -= yval;
			}
			for (Vector3 v : n2.vecs) {
				v.z += zval;
				v.y += yval;
			}
		}
		shotgun.create();
	}

}
