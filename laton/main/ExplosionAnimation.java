package laton.main;

import laton.engine.Animation;
import laton.engine.Textures;

public class ExplosionAnimation extends Animation {
	ExplosionObject obj;
	boolean ended = false;
	public ExplosionAnimation(ExplosionObject obj) {
		this.obj = obj;
		keyframes = 4;
	}
	@Override
	public void onAnimation(int keyframe) {
		if (ended) return;
		
		if (keyframe == 0) {
			obj.texture = Textures.loadTexture("ltex/exp2.png", 9728);
		}
		else if (keyframe == 1) {
			obj.texture = Textures.loadTexture("ltex/exp3.png", 9728);
		}
		else if (keyframe == 2) {
			obj.texture = Textures.loadTexture("ltex/exp4.png", 9728);
		}
		else if (keyframe == 3) {
			ended = true;
			obj.setActive(false);
		}
	}

}
