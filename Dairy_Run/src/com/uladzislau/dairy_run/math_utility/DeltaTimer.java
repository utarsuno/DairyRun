package com.uladzislau.dairy_run.math_utility;

public class DeltaTimer {

	public static final byte DEFAULT = 0;
	public static final byte RUN_ONCE = 1;
	public static final byte RESETING = 2;
	public static final byte BOUNCE = 3;
	public static final byte BOUNCE_ONCE = 4;

	private int type;

	private int duration;

	private boolean tick = true;
	private boolean tick_up = true;

	private int total_delta;

	public DeltaTimer() {
		this.total_delta = 0;
		this.tick = true;
		this.type = DEFAULT;
	}

	public DeltaTimer(int type, int duration) {
		this.type = type;
		this.duration = duration;
		this.tick = true;
	}

	public boolean isFinished() {
		if (this.total_delta >= this.duration) {
			return true;
		}
		return false;
	}

	public void end() {
		this.total_delta = this.duration;
		this.update(1);
	}

	public float percentComplete() {
		return (float) this.getTotalDelta() / this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setType(byte type) {
		this.type = type;
		this.tick = true;
	}

	public void update(int delta) {
		switch (this.type) {
		case DEFAULT:
			this.total_delta += delta;
			break;
		case RUN_ONCE:
			if (this.tick) {
				this.total_delta += delta;
				if (this.isGreaterThanOrEqualTo(this.duration)) {
					this.total_delta = this.duration;
					this.tick = false;
				}
			}
			break;
		case BOUNCE_ONCE:
			if (this.tick) {
				if (this.tick_up) {
					this.total_delta += delta;
				} else {
					this.total_delta -= delta;
				}
				if (this.isGreaterThanOrEqualTo(this.duration)) {
					this.tick_up = false;
				} else if (this.isLessThan(0)) {
					this.tick = false;
				}
			}
			break;
		default:
			break;
		}
	}

	private boolean isLessThan(int delta_time) {
		return (this.total_delta > delta_time);
	}

	public int getTotalDelta() {
		return this.total_delta;
	}

	public void setTotalDelta(int td) {
		this.total_delta = td;
	}

	public boolean isGreaterThanOrEqualTo(int delta_time) {
		return (this.total_delta >= delta_time);
	}

	public void reset() {
		this.total_delta = 0;
		this.tick = true;
		this.tick_up = true;
	}

	public void subtract(int time) {
		this.total_delta -= time;
	}

	public boolean isGreaterThanOrEqualToDuration() {
		return isGreaterThanOrEqualTo(this.duration);
	}

	public void update(float delta) {
		this.update((int) (delta * 1000.0f));
	}

	public void finish() {
		this.setTotalDelta(this.duration);
	}

}