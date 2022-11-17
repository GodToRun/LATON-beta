package laton.main;

import java.util.ArrayList;

import laton.engine.Camera;
import laton.engine.Mesh;
import laton.engine.Textures;
import vecutils.Vector3;

public class Container extends Monster {
	Game laton;
	public Container(Game laton, Camera cam, Mesh mesh) {
		super(cam, mesh);
		this.laton = laton;
		hp = 20;
		haveAI = false;
		texture = Textures.loadTexture("ltex/container.png", 9728);
	}
	@Override
	public void onDeath() {
		super.onDeath();
		for (Monster mon : laton.monsters) {
			if (mon.died) continue;
			Vector3 d = position.distance(mon.getPosition());
			float len = (float)Math.sqrt(d.x*d.x + d.y*d.y + d.z*d.z);
			if (25-len > 0)
				mon.damage((int)((25-len) * 5));
		}
		ExplosionObject obj = new ExplosionObject();
		obj.setPosition(getPosition());
		obj.create();
		setActive(false);
	}

}
