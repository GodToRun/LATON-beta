package laton.main;

import org.lwjgl.opengl.GL11;

import laton.engine.Camera;
import laton.engine.GameObject;
import laton.engine.Mesh;
import laton.engine.OBJLoader;
import laton.engine.Textures;
import vecutils.Vector3;
import vecutils.Vector4;

public class Grunt extends Monster {
	Game laton;
	int tick = 0;
	int damTick = 0;
	GameObject target;
	boolean toTarget = false;
	public Grunt(Game laton, Camera cam, Mesh mesh) {
		super(cam, mesh);
		this.laton = laton;
		texture = Textures.loadTexture("ltex/lknight.png", 9728);
		GruntAnimation ani = new GruntAnimation(this, (OBJLoader)this.mesh);
		this.animation = ani;
		this.rotation = new Vector4(0, 1, 0, 0);
		target = cam;
	}
	@Override
	public void update() {
		super.update();
		if (!died) {
			Vector3 dis = getPosition().distance(target.getPosition());
			if (dis.x < 10f && dis.y < 10f && dis.z < 10f) {
				damTick++;
				if (damTick > 100) {
					damTick = 0;
					if (Math.random() < 0.4)
						laton.playerHP -= 24;
				}
			}
			if (dis.x < 30f && dis.y < 6f && dis.z < 30f) {
				toTarget = true;
				if (dis.x > 10f && dis.z > 10f)
					moveToPlayer(target, 0.1f);
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
	@Override
	public void onDeath() {
		super.onDeath();
		if (hp < -30) { // Gibbed
			Gib gib = new Gib(getPosition().copy());
			animation = null;
			setActive(false);
		}
		else {
			AmmoBox ammo = new AmmoBox(laton, new OBJLoader("lmodels/backpack.obj"));
			ammo.setPosition(getPosition().copy());
			ammo.getPosition().y -= 4f;
			ammo.create();
			laton.pickables.add(ammo);
			animation = new GruntDeathAnimation(this, (OBJLoader)this.mesh);
		}
	}

}
