package laton.engine;
import vecutils.*;
public class AABB {
	public Vector3 min, max;
	public AABB(Vector3 min, Vector3 max) {
		this.min = min;
		this.max = max;
	}
	public void move(Vector3 v) {
		float x = v.x;
		float y = v.y;
		float z = v.z;
		min.x += x;
		min.y += y;
		min.z += z;
		
		max.x += x;
		max.y += y;
		max.z += z;
	}
	public boolean intersects(AABB other) {
		if (this.max.x < other.min.x) {
            return false;
        }

        if (this.max.y < other.min.y) {
            return false;
        }

        if (this.min.x > other.max.x) {
            return false;
        }

        if (this.min.y > other.max.y) {
            return false;
        }
        
        if (this.max.z < other.min.z) {
            return false;
        }
        
        if (this.min.z > other.max.z) {
            return false;
        }
        
		return true;
	}
}
