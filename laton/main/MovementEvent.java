package laton.main;

import laton.engine.GameObject;
import laton.engine.IEvent;
import laton.engine.Sector;
import vecutils.Vector3;

public class MovementEvent extends IEvent {
	Sector sector;
	Vector3 origin, per;
	int tick = 0, maxTick;
	GameObject map;
	public MovementEvent(GameObject map, Sector sector, Vector3 per, int tick) {
		this.sector = sector;
		this.per = per;
		this.maxTick = tick;
		this.map = map;
	}
	@Override
	public void onActive() {
		started = true;
	}

	@Override
	public void update() {
		if (ended || !started) return;
		for (int i = 0; i < sector.vecs.size(); i++) {
			sector.vecs.get(i).x += per.x;
			sector.vecs.get(i).y += per.y;
			sector.vecs.get(i).z += per.z;
		}
		sector.refAABB.min.x += per.x;
		sector.refAABB.min.y += per.y;
		sector.refAABB.min.z += per.z;
		
		sector.refAABB.max.x += per.x;
		sector.refAABB.max.y += per.y;
		sector.refAABB.max.z += per.z;
		
		map.create();
		tick++;
		if (tick >= maxTick) {
			ended = true;
		}
	}

}
