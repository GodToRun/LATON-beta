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
		float hy = -2f;
		float val1 = 0.6f;
		float val2 = 1.2f;
		if (keyframe == 0) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z += val1;
				}
				if (vec.y > hy) {
					vec.z += val2;
				}
			}
		}
		else if (keyframe == 1) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z += val1;
				}
				if (vec.y > hy) {
					vec.z += val2;
				}
			}
		}
		else if (keyframe == 2) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z += val1;
				}
				if (vec.y > hy) {
					vec.z += val2;
				}
			}
		}
		else if (keyframe == 3) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z += val1;
				}
				if (vec.y > hy) {
					vec.z += val2;
				}
			}
		}
		else if (keyframe == 4) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z -= val1;
				}
				if (vec.y > hy) {
					vec.z -= val2;
				}
			}
		}
		else if (keyframe > 4) {
			for (Vector3 vec : hand.vecs) {
				if (vec.y > -1.3f) {
					vec.z -= val1;
				}
				if (vec.y > hy) {
					vec.z -= val2;
				}
			}
		}
		obj.create();
	}

}
