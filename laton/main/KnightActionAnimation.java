package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;
import vecutils.Vector3;

public class KnightActionAnimation extends Animation {
	GameObject obj;
	OBJLoader mesh;
	public KnightActionAnimation(GameObject obj, OBJLoader mesh) {
		this.obj = obj;
		this.mesh = mesh;
		keyframes = 8;
	}
	@Override
	public void onAnimation(int keyframe) {
		Sector hand = mesh.findSector("hand");
		float hy = -2.2f;
		float val1 = 1f;
		float val2 = 0.3f;
		if (keyframe < 2) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z -= val1;
				}
				if (vec.y < hy) {
					vec.z -= val2;
				}
			}
		}
		else if (keyframe > 1 && keyframe < 6) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z += val1;
				}
				if (vec.y < hy) {
					vec.z += val2;
				}
			}
		}
		else if (keyframe > 5) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z -= val1;
				}
				if (vec.y < hy) {
					vec.z -= val2;
				}
			}
		}
		obj.create();
	}

}
