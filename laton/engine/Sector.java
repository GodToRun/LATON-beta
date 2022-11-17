package laton.engine;

import java.util.ArrayList;

import vecutils.Vector3;

public class Sector {
	public ArrayList<Vector3> vecs = new ArrayList<Vector3>();
	public int offset = -9999;
	public Vector3 min = new Vector3(9999, 9999, 9999), max = new Vector3(-9999, -9999, -9999);
	public String name;
	public AABB refAABB;
}
