package laton.main;

import laton.engine.GameObject;
import laton.engine.IEvent;
import laton.engine.Sector;
import vecutils.Vector3;

public class ExitEvent extends IEvent {
	Game laton;
	public ExitEvent(Game laton) {
		this.laton = laton;
	}
	@Override
	public void onActive() {
		if (!started) {
			laton.level++;
			laton.loadLevel("lmodels/m" + laton.level + ".obj", laton.codes[laton.level-1]);
		}
		started = true;
	}

	@Override
	public void update() {
		if (ended || !started) return;
	}

}
