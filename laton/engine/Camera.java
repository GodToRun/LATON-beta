package laton.engine;

import org.lwjgl.opengl.GL11;

import vecutils.*;

public class Camera extends GameObject {
	public Vector3 forward = Vector3.ONE();
	public Vector3 right = Vector3.ONE();
	public Camera() {
		super(null);
	}
	
	public void lookThrough()
	{
	//roatate the pitch around the position.x aposition.xis
		GL11.glRotatef(rotation.x, 1.0f, 0.0f, 0.0f);
	//roatate the yaw around the Y aposition.xis
		GL11.glRotatef(rotation.y, 0.0f, 1.0f, 0.0f);
	}
	public void transferThrough() {
		GL11.glTranslatef(-position.x, -position.y, -position.z);
	}
	
	public void yaw(float amount)
	{
		//increment the yaw by the amount param
		rotation.y += amount;
	}

	//increment the camera's current yaw rotation
	public void pitch(float amount)
	{
		if (rotation.x > 90) {
			rotation.x = 90;
		}
		if (rotation.x < -90) {
			rotation.x = -90;
		}
		//increment the pitch by the amount param
		rotation.x += amount;
	}
	
	//strafes the camera left relitive to its current rotation (yaw)
	public void strafeLeft(float distance)
	{
		position.x -= distance * (float)Math.sin(Math.toRadians(rotation.y-90));
		position.z += distance * (float)Math.cos(Math.toRadians(rotation.y-90));
	}

	//strafes the camera right relitive to its current rotation (yaw)
	public void strafeRight(float distance)
	{
		position.x -= distance * (float)Math.sin(Math.toRadians(rotation.y+90));
		position.z += distance * (float)Math.cos(Math.toRadians(rotation.y+90));
	}
	@Override
	public void update() {
		super.update();
		forward = new Vector3(-(float)Math.sin(Math.toRadians(rotation.y)), 0, (float)Math.cos(Math.toRadians(rotation.y)));
		right = new Vector3(-(float)Math.sin(Math.toRadians(rotation.y+90)), 0, (float)Math.cos(Math.toRadians(rotation.y+90)));
		
	}
		
}