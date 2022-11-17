package laton.engine;

import vecutils.Vector3;

public abstract class IEvent {
	public abstract void onActive();
	public abstract void update();
	public boolean ended = false;
	public boolean started = false;
	public Vector3 origin;
}
