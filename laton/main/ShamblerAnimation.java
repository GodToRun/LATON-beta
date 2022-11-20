package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;

public class ShamblerAnimation extends Animation {
	public OBJLoader objMesh;
	Sector body;
	GameObject obj;
	public ShamblerAnimation(GameObject obj, OBJLoader objMesh) {
		keyframes = 8;
		this.obj = obj;
		this.objMesh = objMesh;
		body = objMesh.findSector("Body");
		for (int i = 0; i < body.vecs.size(); i++) {
			if (body.vecs.get(i).y < -7.5f) {
				if (body.vecs.get(i).z > 0)
					body.vecs.get(i).x -= 2f;
				else
					body.vecs.get(i).x += 2f;
			}
		}
	}
	@Override
	public void onAnimation(int keyframe) {
		if (body == null) {
			return;
		}
		for (int i = 0; i < body.vecs.size(); i++) {
			if (body.vecs.get(i).y < -7.5f) {
				if (keyframe < keyframes/2) {
					if (body.vecs.get(i).z > 0)
						body.vecs.get(i).x += 1f;
					else
						body.vecs.get(i).x -= 1f;
				}
				else {
					if (body.vecs.get(i).z > 0)
						body.vecs.get(i).x -= 1f;
					else
						body.vecs.get(i).x += 1f;
				}
			}
		}
		obj.create();
	}
}
