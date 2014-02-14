package com.uladzislau.dairy_run.math;

public class Vector2i {

	public static final Vector2i ZERO_VECTOR2I = new Vector2i(0.0f,0.0f);
	
	public int x;
	public int y;
	public int magnitude;

	public Vector2i() {
		this.x = 0;
		this.y = 0;
		setMagnitude();
	}

	public Vector2i(float x, float y) {
		this.x = (int) x;
		this.y = (int) y;
		setMagnitude();
	}

	public Vector2i(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		setMagnitude();
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
		setMagnitude();
	}

	public Vector2i(int x, double y) {
		this.x = x;
		this.y = (int) y;
		setMagnitude();
	}

	public Vector2i(double x, int y) {
		this.x = (int) x;
		this.y = y;
		setMagnitude();
	}

	public Vector2i(float x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		setMagnitude();
	}

	public Vector2i(double x, float y) {
		this.x = (int) x;
		this.y = (int) y;
		setMagnitude();
	}

	public Vector2i(float x, int y) {
		this.x = (int) x;
		this.y = y;
		setMagnitude();
	}

	public Vector2i(int x, float y) {
		this.x = x;
		this.y = (int) y;
		setMagnitude();
	}

	@Override
	public String toString() {
		return "X: " + this.x + "\tY: " + this.y; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void normalize() {
		if (this.magnitude == 0) {
			this.x = 0;
			this.y = 0;
		} else {
			this.x /= this.magnitude;
			this.y /= this.magnitude;
		}
	}

	private void setMagnitude() {
		this.magnitude = (int) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vector2i subtract(Vector2i vector2f) {
		return new Vector2i(this.x - vector2f.x, this.y - vector2f.y);
	}

	public static Vector2i directionVector2fFrom_To_(Vector2i vector2f_1, Vector2i vector2f_2) {

		Vector2i vector2f = new Vector2i();

		vector2f = (vector2f_2.subtract(vector2f_1));
		vector2f.normalize();

		return vector2f;
	}

}