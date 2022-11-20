package laton.main;

import laton.engine.Animation;
import laton.engine.GameObject;

public class ShotgunAnimation extends GunAnimation {
	GameObject shotgun;
	int tick = 0;
	int current_keyframe = 0;
	public ShotgunAnimation(GameObject shotgun) {
		ended = true;
		keyframes = 7;
		this.shotgun = shotgun;
	}
	@Override
	public void init() {
		shotgun.getPosition().z += 0.7f;
	}
	@Override
	public void update() {
		if (!ended) {
			tick++;
			if (tick > 3) {
				onAnimation(current_keyframe);
				current_keyframe++;
				tick = 0;
				if (current_keyframe >= keyframes) {
					current_keyframe = 0;
					ended = true;
					return;
				}
			}
		}
		else {
			current_keyframe = 0;
			tick = 0;
		}
	}
	@Override
	public void onAnimation(int keyframe) {
		if (ended) return;
		this.shotgun.getPosition().z -= 0.1f;
	}

}
