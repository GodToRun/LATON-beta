package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;

public class GruntAnimation extends Animation {
	public OBJLoader objMesh;
	Sector leg1;
	Sector leg2;
	GameObject obj;
	public GruntAnimation(GameObject obj, OBJLoader objMesh) {
		keyframes = 6;
		this.obj = obj;
		this.objMesh = objMesh;
		leg1 = objMesh.findSector("leg1_cube");
		leg2 = objMesh.findSector("leg2_cube.003");
	}
	@Override
	public void onAnimation(int keyframe) {
		if (leg1 == null) {
			return;
		}
		for (int i = 0; i < leg1.vecs.size(); i++) {
			if (leg1.vecs.get(i).y < -6f) {
				if (keyframe < keyframes/2)
					leg1.vecs.get(i).x += 1f;
				else
					leg1.vecs.get(i).x -= 1f;
			}
			if (leg2.vecs.get(i).y < -6f) {
				if (keyframe < keyframes/2)
					leg2.vecs.get(i).x -= 1f;
				else
					leg2.vecs.get(i).x += 1f;
			}
		}
		obj.create();
	}

}
