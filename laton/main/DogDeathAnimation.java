package laton.main;

import java.util.ArrayList;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;
import vecutils.Vector3;

public class DogDeathAnimation extends Animation {
	public OBJLoader objMesh;
	Sector leg1;
	Sector leg2;
	GameObject obj;
	boolean ended = false;
	public DogDeathAnimation(GameObject obj, OBJLoader objMesh) {
		keyframes = 5;
		this.obj = obj;
		this.objMesh = objMesh;
	}
	ArrayList<Vector3> expand(float min, float max) {
		ArrayList<Vector3> list = new ArrayList<Vector3>();
		for (Vector3 vec : objMesh.vertices) {
			if (vec.y > min && vec.y < max) {
				list.add(vec);
			}
		}
		return list;
	}
	@Override
	public void onAnimation(int keyframe) {
		if (ended) {
			return;
		}
		ArrayList<Vector3> body = expand(-1f, 9999f);
		ArrayList<Vector3> leg = expand(-2f, -1f);
		if (true) {
			for (Vector3 vec : body) {
				vec.z += 0.1f;
				vec.y -= 0.1f;
			}
			for (Vector3 vec : leg) {
				vec.z += 0.1f;
				vec.y -= 0.1f;
			}
			
		}
		obj.create();
		
		if (keyframe+1 >= keyframes) {
			ended = true;
		}
	}

}