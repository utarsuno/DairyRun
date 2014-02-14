package com.uladzislau.dairy_run.math;

public class Vector2f {

	public static final Vector2f ZERO_VECTOR2F = new Vector2f(0.0f,0.0f);
	
	public float x;
	public float y;
	public float magnitude;

	public Vector2f() {
		this.x = 0;
		this.y = 0;
		setMagnitude();
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
		setMagnitude();
	}

	public Vector2f(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
		setMagnitude();
	}

	public Vector2f(int x, int y) {
		this.x = x;
		this.y = y;
		setMagnitude();
	}

	public Vector2f(int x, double y) {
		this.x = x;
		this.y = (float) y;
		setMagnitude();
	}

	public Vector2f(double x, int y) {
		this.x = (float) x;
		this.y = y;
		setMagnitude();
	}

	public Vector2f(float x, double y) {
		this.x = x;
		this.y = (float) y;
		setMagnitude();
	}

	public Vector2f(double x, float y) {
		this.x = (float) x;
		this.y = y;
		setMagnitude();
	}

	public Vector2f(float x, int y) {
		this.x = x;
		this.y = y;
		setMagnitude();
	}

	public Vector2f(int x, float y) {
		this.x = x;
		this.y = y;
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
		this.magnitude = (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vector2f subtract(Vector2f vector2f) {
		return new Vector2f(x - vector2f.x, y - vector2f.y);
	}

	public static Vector2f directionVector2fFrom_To_(Vector2f vector2f_1, Vector2f vector2f_2) {

		Vector2f vector2f = new Vector2f();

		vector2f = (vector2f_2.subtract(vector2f_1));
		vector2f.normalize();

		return vector2f;
	}

	public static float distanceFrom_To_(Vector2f v1, Vector2f v2) {
		Vector2f length = v1.subtract(v2);
		return length.magnitude;
	}

}