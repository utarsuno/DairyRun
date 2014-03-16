package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;

public abstract class Entity {

	private SpriteBatch sprite_batch;

	private int x;
	private int y;
	private int width;
	private int height;

	private Play play;

	public Entity(Play play) {
		this.play = play;
	}

	public Entity(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
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

	public Play getPlay() {
		return this.play;
	}

	public SpriteBatch getSpriteBatch() {
		return this.sprite_batch;
	}

	public void setSpriteBatch(SpriteBatch sprite_batch) {
		this.sprite_batch = sprite_batch;
	}

}