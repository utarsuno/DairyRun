package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public abstract class CircleButton extends Circlef {

	public CircleButton(float x, float y, float radius) {
		super(x, y, radius);
	}

	public abstract void update(float delta);

	public abstract void render(SpriteBatch sb);

	public void debugRender(ShapeRenderer sr) {
	}

	public void reset() {
	}
}
