package com.uladzislau.dairy_run.math.geometry;

public class Rectanglef {

	private float x;
	private float y;
	private float width;
	private float height;

	public Rectanglef(float x, float y, float width, float height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}

	public boolean isCollidingWithRect(Rectanglei rectangle) {
		if (x + width < rectangle.getX() || x > rectangle.getX() + rectangle.getWidth() || y > rectangle.getY() + rectangle.getHeight()
				|| y + height < rectangle.getY()) {
			return false;
		}
		return true;
	}
	
	public boolean isCollidingWithRect(Rectanglef rectangle) {
		if (x + width < rectangle.getX() || x > rectangle.getX() + rectangle.getWidth() || y > rectangle.getY() + rectangle.getHeight()
				|| y + height < rectangle.getY()) {
			return false;
		}
		return true;
	}

	public boolean isPointInside(int x, int y) {
		if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
			return true;
		}
		return false;
	}

	public boolean isPointInside(float x, float y) {
		if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return ("X: " + this.x + "\tY: " + this.y + "\tWidth: " + this.width + "\tHeight: " + this.height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
