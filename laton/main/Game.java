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
	public String[][] codes = {
		new String[] { "set event1_o mvc2_yard mvt 0 -0.11 0 90",
				"set eventd_o1 mvcd_door1 mvt 0 0 0.16 80",
				"set eventf_o2 mvcf_floor1 mvt 0 0.15 0 82",
				"set event0_o3 mvc_MV1 mvt 0 0.12 0 80",
				"set eventex null ext"},
		new String[] { "set event1 mvc1 mvt 0 0.14 0 30",
				"set event2 mvc2 mvt 0 0.14 0 60",
				"set event3 mvc3 mvt 0 -0.14 0 80" },
		new String[] { },
		new String[] { },
		new String[] { }
	};
	AABB waterAABB;
	GameObject obj, water, shotgun;
	GunAnimation gunAni;
	OBJLoader shotgunMesh, rocketLauncherMesh, nailgunMesh, nailMesh, rocketMesh;
	public Gun gun = Gun.Shotgun;
	boolean died = false;
	boolean camIsInWater = false;
	int playerHP = 100, playerAMMO = 20000;
	ArrayList<IEvent> events = new ArrayList<IEvent>();
	public ArrayList<Monster> monsters = new ArrayList<Monster>();
	public ArrayList<Pickable> pickables = new ArrayList<Pickable>();
	void updateGun() {
		if (gun == Gun.Shotgun) {
			shotgun.mesh = shotgunMesh;
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
		shotgun.create();
	}
	OBJLoader loadLevel(String modelName, String[] latonShell) {
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
		OBJLoader loader = (OBJLoader)obj.mesh;
		for (Sector sec : loader.model.sectors) {
			if (sec.name.startsWith("grunt")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 4.65f;
				gruntAt(pos);
			}
			else if (sec.name.startsWith("knight")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 4.75f;
				knightAt(pos);
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
				pos.y += 0.35f;
				healthBoxAt(pos);
			}
			else if (sec.name.startsWith("nailgun")) {
				Vector3 pos = sec.vecs.get(0).copy();
				pos.y += 1.3f;
				gunAt(new OBJLoader("lmodels/nailgun.obj"), Gun.Nailgun, pos);
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
		
		
		loadLevel("lmodels/m1.obj", codes[level-1]);
		
		shotgunMesh = new OBJLoader("lmodels/shotgun.obj");
		rocketLauncherMesh = new OBJLoader("lmodels/rocketl.obj");
		nailgunMesh = new OBJLoader("lmodels/nailgun.obj");
		shotgunMesh.renderInSpace = false;
		rocketLauncherMesh.renderInSpace = false;
		nailgunMesh.renderInSpace = false;
		shotgun = new GameObject(shotgunMesh);
		shotgun.setPosition(new Vector3(0, -0.4f, -1.6f));
		nailMesh = new OBJLoader("lmodels/nail.obj");
		rocketMesh = new OBJLoader("lmodels/rocket.obj");
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
							if (dis.x < 4f && dis.y < 6f && dis.z < 4f) {
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
	void gunAt(Mesh gunMesh, Gun gun, Vector3 position) {
		GunPickable box = new GunPickable(this, gun, gunMesh, "ltex/nailgun.png");
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
		renderer.offset = new Vector3(0, -1 + val, 0);
		if (-val > -0.18f) {
			shotgun.offset = new Vector3(0, 0, val / 1.2f);
		}
		si += 0.17f;
	}
	void gunShot() {
		if (gunAni.ended && playerAMMO > 0) {
			playerAMMO--;
			gunAni.init();
			gunAni.ended = false;
			if (gun == Gun.Shotgun) {
				for (int i = 0; i < 40; i++) {
					Vector3 fwd = cam.getPosition().add(cam.forward.mul(i * -2));
					for (Monster mon : monsters) {
						if (!mon.died) {
							Vector3 dis = fwd.distance(mon.getPosition());
							if (dis.x < 1.5f && dis.z < 1.5f) {
								mon.damage(20);
								return;
							}
						}
					}
					
				}
			}
			else if (gun == Gun.Nailgun) {
				Rocket nail = new Rocket(this, cam, nailMesh, 2f, 10, false);
				nail.setScale(new Vector3(0.1f, 0.1f, 0.1f));
				nail.setPosition(cam.getPosition().add(cam.forward));
				nail.create();
			}
			else if (gun == Gun.RocketLauncher) {
				Rocket rocket = new Rocket(this, cam, rocketMesh, 1f, 90, true);
				rocket.texture = Textures.loadTexture("ltex/rocket.png", 9728);
				rocket.setScale(new Vector3(0.4f, 0.4f, 0.4f));
				rocket.setPosition(cam.getPosition().add(cam.forward));
				rocket.create();
			}
		}
	}
	public void updateController() {
		camAABB.min = cam.getPosition().add(new Vector3(0, -1, 0));
		camAABB.max = camAABB.min.add(new Vector3(1, 2, 1));
		cam.yaw(Mouse.getDX() / 1.8f);
		cam.pitch(-Mouse.getDY() / 1.8f);
		//System.out.println("(" + cam.getPosition().x + ", " + cam.getPosition().y + ", " + cam.getPosition().z + ")");
		cam.aabbUpdate(camAABB, mapAABB);
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
		cam.gravity = -0.25f;
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
