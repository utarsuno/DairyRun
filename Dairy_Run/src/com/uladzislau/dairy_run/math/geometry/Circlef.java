package com.uladzislau.dairy_run.math.geometry;

public class Circlef {

	private float x;
	private float y;
	private float radius;
	private float area;

	public Circlef(float x, float y, float radius) {
		this.setX(x);
		this.setY(y);
		this.setRadius(radius);
		this.setArea((float) Math.PI * (radius * radius));
	}

	public boolean isCollidingWithAnotherCirclef(Circlef circlef) {
		int dx = (int) (getX() - circlef.getX());
		int dy = (int) (getY() - circlef.getY());
		float radiusSum = getRadius() + circlef.getRadius();
		return dx * dx + dy * dy <= radiusSum * radiusSum;
	}
	
	public boolean isPointInsideOrOnMe(int px, int py) {
		if (Math.pow((px - this.x),2) + Math.pow((py - this.y), 2) <= (this.radius * this.radius)) {
			return true;
		}
		return false;
	}

	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getRadius() {
		return this.radius;
	}

	public void setRadius() {
		this.radius = (float) Math.sqrt(getArea() / Math.PI);
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getArea() {
		return this.area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public void setArea() {
		this.area = (float) Math.PI * (this.radius * this.radius);
	}

}