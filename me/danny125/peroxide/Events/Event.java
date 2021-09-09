package me.danny125.peroxide.Events;

public class Event<T> {
	public boolean cancelled;
	public EventType type;
	public EventDirection direction;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public EventDirection getDirection() {
		return direction;
	}
	public void setDirection(EventDirection direction) {
		this.direction = direction;
	}
	public boolean isPre() {
		if(type == EventType.PRE) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isPost() {
		if(type == EventType.POST) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isIncoming() {
		if(direction == EventDirection.INCOMING) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isOutgoing() {
		if(direction == EventDirection.INCOMING) {
			return true;
		}else {
			return false;
		}
	}
}
