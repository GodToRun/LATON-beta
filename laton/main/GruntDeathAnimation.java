package laton.main;

import java.util.ArrayList;

import laton.engine.Animation;
import laton.engine.GameObject;
import laton.engine.OBJLoader;
import laton.engine.Sector;
import vecutils.Vector3;

public class GruntDeathAnimation extends Animation {
	public OBJLoader objMesh;
	Sector leg1;
	Sector leg2;
	GameObject obj;
	boolean ended = false;
	public GruntDeathAnimation(GameObject obj, OBJLoader objMesh) {
		keyframes = 6;
		this.obj = obj;
		this.objMesh = objMesh;
		leg1 = objMesh.findSector("leg1_cube");
		leg2 = objMesh.findSector("leg2_cube.003");
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
		if (leg1 == null || ended) {
			return;
		}
		ArrayList<Vector3> head = expand(-1.9f, 9999);
		ArrayList<Vector3> body = expand(-4f, -1.9f);
		ArrayList<Vector3> leg = expand(-6f, -4f);
		if (keyframe == 0) {
			for (Vector3 vec : head) {
				vec.x += 0.2f;
				vec.y -= 0.05f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.1f;
				vec.y -= 0.05f;
			}
			obj.create();
		}
		else if (keyframe == 1) {
			for (Vector3 vec : head) {
				vec.x += 0.25f;
				vec.y -= 0.1f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.15f;
				vec.y -= 0.1f;
			}
			for (Vector3 vec : leg) {
				vec.x += 0.1f;
				vec.y -= 0.06f;
			}
			obj.create();
		}
		else if (keyframe == 2) {
			for (Vector3 vec : head) {
				vec.x += 0.25f;
				vec.y -= 0.1f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.15f;
				vec.y -= 0.1f;
			}
			for (Vector3 vec : leg) {
				vec.x += 0.1f;
				vec.y -= 0.08f;
			}
			obj.create();
		}
		else if (keyframe == 3) {
			for (Vector3 vec : head) {
				vec.x += 0.25f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.15f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : leg) {
				vec.x += 0.1f;
				vec.y -= 0.1f;
			}
			obj.create();
		}
		else if (keyframe == 4) {
			for (Vector3 vec : head) {
				vec.x += 0.25f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.15f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : leg) {
				vec.x += 0.2f;
				vec.y -= 0.2f;
			}
			obj.create();
		}
		else if (keyframe == 5) {
			for (Vector3 vec : head) {
				vec.x += 0.2f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : body) {
				vec.x += 0.1f;
				vec.y -= 0.15f;
			}
			for (Vector3 vec : leg) {
				vec.x += 0.1f;
				vec.y -= 0.3f;
			}
			obj.create();
		}
		
		if (keyframe+1 >= keyframes) {
			ended = true;
		}
	}

}