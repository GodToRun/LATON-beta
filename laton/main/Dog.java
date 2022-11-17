package laton.main;

import laton.engine.Camera;
import laton.engine.GameObject;
import laton.engine.Mesh;
import laton.engine.OBJLoader;
import laton.engine.Textures;
import vecutils.Vector3;

public class Dog extends Monster {
	GameObject target;
	boolean toTarget = false;
	int tick = 0;
	int damTick = 0;
	Game laton;
	public Dog(Game laton, Camera cam, Mesh mesh) {
		super(cam, mesh);
		this.laton = laton;
		hp = 30;
		target = cam;
		texture = Textures.loadTexture("ltex/ldog.png", 9728);
		DogAnimation ani = new DogAnimation(this, (OBJLoader)this.mesh);
		this.animation = ani;
	}
	@Override
	public void onDeath() {
		super.onDeath();
		if (hp < -20) { // Gibbed
			Gib gib = new Gib(getPosition().copy());
			animation = null;
			setActive(false);
		}
		else {
			animation = new DogDeathAnimation(this, (OBJLoader)this.mesh);
		}
	}
	@Override
	public void update() {
		super.update();
		if (!died) {
			Vector3 dis = getPosition().distance(target.getPosition());
			if (dis.x < 6f && dis.y < 6f && dis.z < 6f) {
				damTick++;
				if (damTick > 60) {
					damTick = 0;
					laton.playerHP -= 8;
				}
			}
			if (dis.x < 30f && dis.y < 6f && dis.z < 30f) {
				toTarget = true;
				moveToPlayer(target, 0.15f);
			}
			else {
				toTarget = false;
			}
		}
		
	}
	@Override
	public void onAI() {
		if (!toTarget) super.onAI();
		else {
			tick++;
			if (tick > 18) {
				lookPlayer();
				tick = 0;
			}
		}
	}

}
