package laton.engine;


import org.lwjgl.opengl.GL11;

import vecutils.Vector2;
import vecutils.Vector3;
import vecutils.Vector4;

public class Quad extends Mesh {
	public Quad() {
		super(GL11.GL_QUADS);
		vertices = new Vector3[4];
		vertices[0] = new Vector3(1f, 1f, 1f);
		vertices[1] = new Vector3(-1f, 1f, 1f);
		vertices[2] = new Vector3(-1f, -1f, 1f);
		vertices[3] = new Vector3(1f, -1f, 1f);
		colors = new Vector4[1];
		colors[0] = new Vector4(1f, 1f, 1f, 1f);
		uvs = new Vector2[4];
		uvs[0] = new Vector2(0, 0f);
		uvs[1] = new Vector2(1f, 0f);
		uvs[2] = new Vector2(1f, 1f);
		uvs[3] = new Vector2(0f, 1f);
	}
}