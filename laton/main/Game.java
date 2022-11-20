package laton.main;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import laton.engine.AABB;
import laton.engine.Animation;
import laton.engine.Camera;
import laton.engine.Cube;
import laton.engine.DisplayManager;
import laton.engine.GLManager;
import laton.engine.GameObject;
import laton.engine.IEvent;
import laton.engine.Mesh;
import laton.engine.OBJLoader;
import laton.engine.Quad;
import laton.engine.Renderer;
import laton.engine.Sector;
import laton.engine.Textures;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import vecutils.Vector3;
import vecutils.Vector4;

public class Game {
	public GLManager glman;
	public Renderer renderer;
	public Camera cam;
	public AABB camAABB;
	public ArrayList<AABB> mapAABB = new ArrayList<AABB>();
	public float speed = 0.2f;
	public int level = 1;
	GameObject lit;
	public String[][] codes = {
		new String[] { "set event1_o mvc2_yard mvt 0 -0.11 0 90",
				"set eventd_o1 mvcd_door1 mvt 0 0 0.16 80",
				"set eventf_o2 mvcf_floor1 mvt 0 0.15 0 82",
				"set event0_o3 mvc_MV1 mvt 0 0.12 0 80",
				"set eventex null ext"},
		new String[] { "set event1 mvc1 mvt 0 0.14 0 30",
				"set event2 mvc2 mvt 0 0.14 0 60",
				"set event3 mvc3 mvt 0 -0.14 0 80",
				"set event4 mvc4 mvt 0 0.14 0 190", 
				"set event5 mvc5 mvt 0 0 -0.14 80",
				"set event6 mvc6 mvt 0 0 -0.18 120",
				"set event7 mvc7 mvt 0 0 -0.18 120",
				"set event8 mvc8 mvt -0.18 0 0 120",
				"set event9 mvc9 mvt -0.18 0 0 120",
				"set ext null ext" },
		new String[] { "set event1 mv1 mvt 0 0.12 0 50",
				"set ev2 mv2 mvt 0 0.18 0 180",
				"set ext null ext"
				},
		new String[] { },
		new String[] { }
	};
	AABB waterAABB;
	GameObject obj, water, shotgun;
	GunAnimation gunAni;
	OBJLoader shotgunMesh, rocketLauncherMesh, nailgunMesh, nailMesh, rocketMesh, doubleShotgunMesh, thunderboltMesh;
	public Gun gun = Gun.Shotgun;
	boolean died = false;
	boolean camIsInWater = false;
	int playerHP = 100, playerAMMO = 50;
	ArrayList<IEvent> events = new ArrayList<IEvent>();
	public ArrayList<Monster> monsters = new ArrayList<Monster>();
	public ArrayList<Pickable> pickables = new ArrayList<Pickable>();
	void updateGun() {
		if (gun == Gun.Shotgun || gun == Gun.DoubleShotgun) {
			if (gun == Gun.Shotgun)
				shotgun.mesh = shotgunMesh;
			else if (gun == Gun.DoubleShotgun)
				shotgun.mesh = doubleShotgunMesh;
			gunAni = new ShotgunAnimation(shotgun);
			shotgun.texture = Textures.loadTexture("ltex/shotgun.png", 9728);
		}
		else if (gun == Gun.RocketLauncher) {
			shotgun.mesh = rocketLauncherMesh;
			gunAni = new ShotgunAnimation(shotgun);
			shotgun.texture = Textures.loadTexture("ltex/rocketl.png", 9728);
		}
		else if (gun == Gun.Nailgun) {
			shotgun.mesh = nailgunMesh;
			gunAni = new NailgunAnimation(shotgun, (OBJLoader)shotgun.mesh);
			shotgun.texture = Textures.loadTexture("ltex/nailgun.png", 9728);
		}
		else if (gun == Gun.Thunderbolt) {
			shotgun.mesh = thunderboltMesh;
			gunAni = null;
			shotgun.texture = Textures.loadTexture("ltex/thunderbolt.png", 9728);
		}
		shotgun.create();
	}
	OBJLoader loadLevel(String modelName, String[] latonShell) {
		if (level > 3) {
			System.out.println("You defeated the monster that invaded the earth and finally killed two vicious shamblers.\r\n"
					+ "Now, just as you're about to go back to your hometown, you realize that this dimensional gate is connected to hell. Now the sight before you is a den of red skies and creepy demons. If you go back like this, you will not be able to turn the earth around, so you enter the den of demons with wild steps.\r\n"
					+ "Will you save the Earth like this? Or will it be crushed by demons?\r\n"
					+ "That's all for LATON. Thank you for the play!\r\n"
					+ "Developed by ToRun");
			System.exit(0);
		}
		if (obj != null)
			obj.setActive(false);
		obj = new GameObject(new OBJLoader(modelName));
		obj.texture = Textures.loadTexture("lmodels/e1m1/tech10_1.png", 9728);
		obj.create();
		for (Monster m : monsters) {
			m.setActive(false);
		}
		for (Pickable m : pickables) {
			m.setActive(false);
		}
		monsters.clear();
		pickables.clear();
		mapAABB.clear();
		OBJLoader loader = (OBJLoader)obj.mesh;
		for (Sector sec : loader.model.sectors) {
			if (sec.name.startsWith("grunt")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 4.65f;
				gruntAt(pos);
			}
			else if (sec.name.startsWith("enforcer")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 4.8f;
				enforcerAt(pos);
			}
			else if (sec.name.startsWith("knight")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 5f;
				knightAt(pos);
			}
			else if (sec.name.startsWith("deathknight")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 5f;
				deathKnightAt(pos);
			}
			else if (sec.name.startsWith("container")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 0.7f;
				containerAt(pos);
			}
			else if (sec.name.startsWith("dog")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1f;
				dogAt(pos);
			}
			else if (sec.name.startsWith("healthb")) {
				Vector3 pos = sec.vecs.get(0).copy();
				if (level != 1)
					pos.y += 1.2f;
				else
					pos.y += 0.35f;
				healthBoxAt(pos);
			}
			else if (sec.name.startsWith("ammob")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.2f;
				ammoBoxAt(pos);
			}
			else if (sec.name.startsWith("shambler")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 7f;
				shamblerAt(pos);
			}
			else if (sec.name.startsWith("nailgun")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.3f;
				gunAt(new OBJLoader("lmodels/nailgun.obj"), Gun.Nailgun, pos, "ltex/nailgun.png");
			}
			else if (sec.name.startsWith("rocketl")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.3f;
				gunAt(new OBJLoader("lmodels/rocketl.obj"), Gun.RocketLauncher, pos, "ltex/rocketl.png");
			}
			else if (sec.name.startsWith("doubleshotgun")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.3f;
				gunAt(new OBJLoader("lmodels/double_shotgun.obj"), Gun.DoubleShotgun, pos, "ltex/shotgun.png");
			}
			else if (sec.name.startsWith("thunderbolt")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.3f;
				gunAt(new OBJLoader("lmodels/thunderbolt.obj"), Gun.Thunderbolt, pos, "ltex/thunderbolt.png");
			}
			else {
				sec.refAABB.min = sec.min;
				sec.refAABB.max = sec.max;
				mapAABB.add(sec.refAABB);
			}
		}
		cam.setPosition(new Vector3(6f, 6, 6f));
		LSInterpreter interpreter = new LSInterpreter(this, obj, loader, latonShell);
		ArrayList<IEvent> expanded = interpreter.expandEvents();
		for (IEvent event : expanded) {
			events.add(event);
		}
		return loader;
	}
	public void create() {
		//createLAD();
		createDisplay();
		createGL();
		createCamera();
		createRenderer();
		createInput();
		
		
		loadLevel("lmodels/m" + level + ".obj", codes[level-1]);
		shotgunMesh = new OBJLoader("lmodels/shotgun.obj");
		doubleShotgunMesh = new OBJLoader("lmodels/double_shotgun.obj");
		rocketLauncherMesh = new OBJLoader("lmodels/rocketl.obj");
		nailgunMesh = new OBJLoader("lmodels/nailgun.obj");
		thunderboltMesh = new OBJLoader("lmodels/thunderbolt.obj");
		shotgunMesh.renderInSpace = false;
		doubleShotgunMesh.renderInSpace = false;
		rocketLauncherMesh.renderInSpace = false;
		nailgunMesh.renderInSpace = false;
		thunderboltMesh.renderInSpace = false;
		shotgun = new GameObject(shotgunMesh);
		shotgun.setPosition(new Vector3(0, -0.4f, -1.6f));
		nailMesh = new OBJLoader("lmodels/nail.obj");
		rocketMesh = new OBJLoader("lmodels/rocket.obj");
		lit = new GameObject(new OBJLoader("lmodels/lightning.obj"));
		lit.setRotation(new Vector4(0, 1, 0, 90));
		lit.setPosition(new Vector3(0, 0, -28));
		lit.mesh.renderInSpace = false;
		lit.create();
		lit.setActive(false);
		lit.texture = Textures.loadTexture("ltex/lightning.png", 9728);
		updateGun();
		
		water = new GameObject(new Quad());
		water.texture = Textures.loadTexture("ltex/water.png", 9728);
		water.setRotation(new Vector4(1, 0, 0, 90));
		water.setScale(new Vector3(600, 600, 1));
		water.setPosition(new Vector3(6, 0, 6));
		water.create();
		
		waterAABB = new AABB(new Vector3(-600, -600, -600), new Vector3(600, 0, 600));
		
		/*IEvent mv1 = new MovementEvent(obj, loader.findSector("mvc_MV1"), new Vector3(0, 0.12f, 0), 80);
		mv1.origin = new Vector3(15, 1f, 60);
		events.add(mv1);*/
		
		while(!DisplayManager.isDisplayShouldClose()) {
			for (int i = 0; i < GameObject.objects.size(); i++) {
				GameObject.objects.get(i).update();
			}
			if (!died) {
				while (Keyboard.next()) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
						for (IEvent event : events) {
							Vector3 dis = event.origin.distance(cam.getPosition());
							if (dis.x < 6f && dis.y < 6f && dis.z < 6f) {
								event.onActive();
								break;
							}
						}
					}
				}
				for (IEvent event : events) {
					event.update();
				}
				for (Pickable pickable : pickables) {
					Vector3 ami = pickable.getPosition().copy();
					Vector3 d = cam.getPosition().distance(ami);
					float l = 1.5f;
					if (d.x < l && d.y < l && d.z < l) {
						pickable.onPick();
					}
				}
				updateController();
			}
			update();
			renderer.render(cam);
			DisplayManager.updateDisplay();
		}
	}
	void gruntAt(Vector3 position) {
		Grunt mon1 = new Grunt(this, cam, new OBJLoader("lmodels/grunt.obj"));
		mon1.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void enforcerAt(Vector3 position) {
		Enforcer mon1 = new Enforcer(this, cam, new OBJLoader("lmodels/grunt.obj"));
		mon1.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void deathKnightAt(Vector3 position) {
		DeathKnight mon1 = new DeathKnight(this, cam, new OBJLoader("lmodels/deathknight.obj"));
		mon1.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void shamblerAt(Vector3 position) {
		Shambler mon1 = new Shambler(this, cam, new OBJLoader("lmodels/shambler.obj"));
		mon1.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void knightAt(Vector3 position) {
		Knight mon1 = new Knight(this, cam, new OBJLoader("lmodels/knight.obj"));
		mon1.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void dogAt(Vector3 position) {
		Dog mon1 = new Dog(this, cam, new OBJLoader("lmodels/dog.obj"));
		mon1.setScale(new Vector3(0.5f, 0.5f, 0.5f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	void healthBoxAt(Vector3 position) {
		HealthBox box = new HealthBox(this, new Cube());
		box.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		box.create();
		box.setPosition(position);
		pickables.add(box);
	}
	void ammoBoxAt(Vector3 position) {
		AmmoBox box = new AmmoBox(this, new Cube(), 0f, "ltex/ammo.png");
		box.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		box.create();
		box.setPosition(position);
		pickables.add(box);
	}
	void gunAt(Mesh gunMesh, Gun gun, Vector3 position, String tex) {
		GunPickable box = new GunPickable(this, gun, gunMesh, tex);
		//box.setScale(new Vector3(0.7f, 0.7f, 0.7f));
		box.setPosition(position);
		box.create();
		pickables.add(box);
	}
	void containerAt(Vector3 position) {
		Container mon1 = new Container(this, cam, new Cube());
		mon1.setScale(new Vector3(0.7f, 1.4f, 0.7f));
		mon1.create();
		mon1.setPosition(position);
		monsters.add(mon1);
	}
	public void createInput() {
		Mouse.setGrabbed(true);
	}
	float si;
	void headBobbing() {
		float val = (float)Math.sin(si) / 4;
		renderer.offset = new Vector3(0, -2 + val, 0);
		if (-val > -0.18f) {
			shotgun.offset = new Vector3(0, 0, val / 1.2f);
		}
		si += 0.17f;
	}
	void gunShot() {
		if (playerAMMO > 0) {
			if (gun == Gun.Thunderbolt) {
				lit.getRotation().z = Math.abs((float)Math.sin(System.currentTimeMillis() / 10));
				if (lit.getRotation().z > 1) lit.getRotation().z = 1;
				lit.setActive(true);
				if (Math.random() > 0.05) {
					playerAMMO++;
				}
			}
			if (gunAni != null) {
				if (!gunAni.ended) {
					return;
				}
				gunAni.init();
				gunAni.ended = false;
			}
			playerAMMO--;
			if (gun == Gun.Shotgun || gun == Gun.DoubleShotgun || gun == Gun.Thunderbolt) {
				for (int i = 0; i < 40; i++) {
					Vector3 fwd = cam.getPosition().add(cam.forward.mul(i * -2));
					for (Monster mon : monsters) {
						if (!mon.died) {
							Vector3 dis = fwd.distance(mon.getPosition());
							if (dis.x < 3f && dis.y < 6f && dis.z < 3f) {
								if (gun == Gun.Shotgun)
									mon.damage(20);
								else if (gun == Gun.DoubleShotgun) {
									playerAMMO--;
									mon.damage(56);
								}
								else if (gun == Gun.Thunderbolt) {
									mon.damage(3);
								}
								return;
							}
						}
					}
					
				}
			}
			else if (gun == Gun.Nailgun) {
				Rocket nail = new Rocket(this, cam, nailMesh, 2f, 12, false);
				nail.setScale(new Vector3(0.1f, 0.1f, 0.1f));
				nail.setPosition(cam.getPosition().add(cam.forward).add(new Vector3(0, 0.15f, 0)));
				nail.create();
				if (Math.random() > 0.3) {
					playerAMMO++;
				}
			}
			else if (gun == Gun.RocketLauncher) {
				Rocket rocket = new Rocket(this, cam, rocketMesh, 1f, 90, true);
				rocket.texture = Textures.loadTexture("ltex/rocket.png", 9728);
				rocket.setScale(new Vector3(0.4f, 0.4f, 0.4f));
				rocket.setPosition(cam.getPosition().add(cam.forward).add(new Vector3(0, 0.15f, 0)));
				rocket.create();
			}
		}
	}
	public void updateController() {
		camAABB.min = cam.getPosition();
		camAABB.max = camAABB.min.add(new Vector3(1, 2, 1));
		cam.yaw(Mouse.getDX() / 1.8f);
		cam.pitch(-Mouse.getDY() / 1.8f);
		//System.out.println("(" + cam.getPosition().x + ", " + cam.getPosition().y + ", " + cam.getPosition().z + ")");
		cam.aabbUpdate(camAABB, mapAABB);
		lit.setActive(false);
		float drag = 10f;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Vector3 back = cam.forward.mul(speed);
			cam.velocity.x += back.x / drag;
			cam.velocity.z += back.z / drag;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Vector3 forward = cam.forward.mul(-speed);  
			cam.velocity.x += forward.x / drag;
			cam.velocity.z += forward.z / drag;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Vector3 right = cam.right.mul(-speed);
			cam.velocity.x += right.x / drag;
			cam.velocity.z += right.z / drag;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Vector3 left = cam.right.mul(speed);
			cam.velocity.x += left.x / drag;
			cam.velocity.z += left.z / drag;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && cam.velocity.y == 0 && !camIsInWater) {
			cam.velocity.y = 0.5f;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && camIsInWater) {
			cam.velocity.y += 0.018f;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_W) ||
			Keyboard.isKeyDown(Keyboard.KEY_A) ||
			Keyboard.isKeyDown(Keyboard.KEY_S) ||
			Keyboard.isKeyDown(Keyboard.KEY_D)) && !camIsInWater) {
			headBobbing();
		}
		if (Mouse.isButtonDown(0)) {
			gunShot();
		}
		if (camAABB.intersects(waterAABB)) {
			camIsInWater = true;
			cam.gravity = -0.004f;
		}
		else {
			camIsInWater = false;
			cam.gravity = -0.025f;
		}
		
		while (Mouse.next()) {
			if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
				//gunShot();
			}
		}
		
		if (gunAni != null)
			gunAni.update();
		
		/*for (int i = 0; i < obj.mesh.vertices.length-1; i++) {
			Vector3 vtx = obj.mesh.vertices[i];
			Vector3 fvtx = obj.mesh.vertices[i+1];
			float ldis = (float)Line2D.ptLineDist((float)vtx.x, (float)vtx.z, (float)fvtx.x, (float)fvtx.z,
					(float)-cam.getPosition().x, (float)-cam.getPosition().z);
			if (!Float.isNaN(ldis)) {
				if (ldis < 0.001f) {
					canGo = false;
				}
			}
			
		}*/
	}
	public void update() {
		if (playerHP <= 0 && !died) {
			System.out.println("You Died!");
			died = true;
		}
	}
	public void createCamera() {
		cam = new Camera();
		cam.gravity = -0.025f;
		cam.setPosition(new Vector3(6f, 6, 6f));
		camAABB = new AABB(cam.getPosition(), cam.getPosition().add(new Vector3(1, 2, 1)));
	}
	public void createRenderer() {
		renderer = new Renderer();
		renderer.init(cam);
	}
	public void createGL() {
		glman = new GLManager();
		glman.create();
	}
	public void createDisplay() {
		DisplayManager.createDisplay("LATON");
	}
}
