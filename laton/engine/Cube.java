package laton.engine;

import org.lwjgl.opengl.GL11;

import vecutils.Vector2;
import vecutils.Vector3;
import vecutils.Vector4;

public class Cube extends Mesh {
	public Cube() {
		super(GL11.GL_QUADS);
		vertices = new Vector3[24];
		uvs = new Vector3[24];
		//Top
		vertices[0] = new Vector3(1f, 1f, -1f);
		vertices[1] = new Vector3(-1f, 1f, -1f);
		vertices[2] = new Vector3(-1f, 1f, 1f);
		vertices[3] = new Vector3(1f, 1f, 1f);
		
		//Bottom
		vertices[4] = new Vector3(1f, -1f, 1f);
		vertices[5] = new Vector3(-1f, -1f, 1f);
		vertices[6] = new Vector3(-1f, -1f, -1f);
		vertices[7] = new Vector3(1f, -1f, -1f);
		
		//Front
		vertices[8] = new Vector3(1f, 1f, 1f);
		vertices[9] = new Vector3(-1f, 1f, 1f);
		vertices[10] = new Vector3(-1f, -1f, 1f);
		vertices[11] = new Vector3(1f, -1f, 1f);
		
		//Back
		vertices[12] = new Vector3(1f, -1f, -1f);
		vertices[13] = new Vector3(-1f, -1f, -1f);
		vertices[14] = new Vector3(-1f, 1f, -1f);
		vertices[15] = new Vector3(1f, 1f, -1f);
		
		//Left
		vertices[16] = new Vector3(-1f, 1f, 1f);
		vertices[17] = new Vector3(-1f, 1f, -1f);
		vertices[18] = new Vector3(-1f, -1f, -1f);
		vertices[19] = new Vector3(-1f, -1f, 1f);
				
		//Right
		vertices[20] = new Vector3(1f, 1f, -1f);
		vertices[21] = new Vector3(1f, 1f, 1f);
		vertices[22] = new Vector3(1f, -1f, 1f);
		vertices[23] = new Vector3(1f, -1f, -1f);
		
		colors = new Vector4[1];
		colors[0] = new Vector4(1, 1, 1, 1);
		
		// UV Mapping
		
		//Top
		uvs = new Vector2[24];
		uvs[0] = new Vector2(0, 0f);
		uvs[1] = new Vector2(1f, 0f);
		uvs[2] = new Vector2(1f, 1f);
		uvs[3] = new Vector2(0f, 1f);
		
		//Bottom
		uvs[4] = new Vector2(0, 0f);
		uvs[5] = new Vector2(1f, 0f);
		uvs[6] = new Vector2(1f, 1f);
		uvs[7] = new Vector2(0f, 1f);
		
		//Front
		uvs[8] = new Vector2(0, 0f);
		uvs[9] = new Vector2(1f, 0f);
		uvs[10] = new Vector2(1f, 1f);
		uvs[11] = new Vector2(0f, 1f);
		
		//Back
		uvs[12] = new Vector2(1f, 1f);
		uvs[13] = new Vector2(0f, 1f);
		uvs[14] = new Vector2(0f, 0f);
		uvs[15] = new Vector2(1f, 0f);
		
		//Left
		uvs[16] = new Vector2(0, 0f);
		uvs[17] = new Vector2(1f, 0f);
		uvs[18] = new Vector2(1f, 1f);
		uvs[19] = new Vector2(0f, 1f);
		
		//Right
		uvs[20] = new Vector2(0, 0f);
		uvs[21] = new Vector2(1f, 0f);
		uvs[22] = new Vector2(1f, 1f);
		uvs[23] = new Vector2(0f, 1f);
	}
}