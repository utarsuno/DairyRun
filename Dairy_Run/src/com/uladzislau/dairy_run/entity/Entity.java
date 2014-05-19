package com.uladzislau.dairy_run.entity;

import com.uladzislau.dairy_run.game_state.Play;

public abstract class Entity {
	
	private float x;
	private float y;
	private int width;
	private int height;

	private Play play;

	public Entity(Play play) {
		this.play = play;
	}

	public Entity(float x, float y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public abstract void update(float delta);
	
	public abstract void render();

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

	public Play getPlay() {
		return this.play;
	}

}