package laton.engine;

public abstract class Animation {
	public int keyframes;
	public int current_keyframe = 0;
	public boolean ended = false;
	public abstract void onAnimation(int keyframe);
}
