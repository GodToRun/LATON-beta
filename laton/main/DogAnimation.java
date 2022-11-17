package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;

public class DogAnimation extends Animation {
	public OBJLoader objMesh;
	Sector leg1;
	Sector leg2;
	Sector leg3;
	Sector leg4;
	GameObject obj;
	public DogAnimation(GameObject obj, OBJLoader objMesh) {
		keyframes = 4;
		this.obj = obj;
		this.objMesh = objMesh;
		leg1 = objMesh.findSector("leg1");
		leg2 = objMesh.findSector("leg2");
		leg3 = objMesh.findSector("leg3");
		leg4 = objMesh.findSector("leg4_fleg4");
	}
	@Override
	public void onAnimation(int keyframe) {
		if (leg1 == null) {
			return;
		}
		float val = 0.6f;
		if (keyframe == 0 || keyframe == 1) {
			for (int i = 0; i < leg1.vecs.size(); i++) {
				if (leg1.vecs.get(i).y < -2f) {
					leg1.vecs.get(i).x += val;
					leg3.vecs.get(i).x -= val;
					
					leg2.vecs.get(i).x -= val;
					leg4.vecs.get(i).x += val;
				}
			}
		}
		else if (keyframe == 2 || keyframe == 3) {
			for (int i = 0; i < leg1.vecs.size(); i++) {
				if (leg1.vecs.get(i).y < -2f) {
					leg1.vecs.get(i).x -= val;
					leg3.vecs.get(i).x += val;
					
					leg2.vecs.get(i).x += val;
					leg4.vecs.get(i).x -= val;
				}
			}
		}
		obj.create();
	}

}
