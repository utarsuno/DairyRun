package com.uladzislau.dairy_run.math.geometry;

import com.uladzislau.dairy_run.manager.InputManager;

public class Rectanglei {

	private int x;
	private int y;
	private int width;
	private int height;

	public Rectanglei(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}

	public Rectanglei(float x, float y, float w, float h) {
		this.setX((int) x);
		this.setY((int) y);
		this.setWidth((int) w);
		this.setHeight((int) h);
	}

	public boolean isCollidingWithRect(Rectanglei rectangle) {
		if (this.x + this.width < rectangle.getX() || this.x > rectangle.getX() + rectangle.getWidth()
				|| this.y > rectangle.getY() + rectangle.getHeight() || this.y + this.height < rectangle.getY()) {
			return true;
		}
		return false;
	}

	public boolean isCollidingWithRect(Rectanglef rectangle) {
		if (this.x + this.width < rectangle.getX() || this.x > rectangle.getX() + rectangle.getWidth()
				|| this.y > rectangle.getY() + rectangle.getHeight() || this.y + this.height < rectangle.getY()) {
			return true;
		}
		return false;
	}

	public boolean isPointInside(int x, int y) {
		if (x > this.x && x < this.x + this.width && y > this.y - (int) (this.height * .20f) && y < this.y + this.height) {
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

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isMouseInside() {
		return (this.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y));
	}

	/**
	 * @return the y value of the top of the rectangle.
	 */
	public int getTop() {
		return this.y + this.height;
	}

}