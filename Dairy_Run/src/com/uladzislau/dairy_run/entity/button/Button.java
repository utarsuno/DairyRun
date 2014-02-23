package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public abstract class Button extends Circlef {

	protected Circlef[] pointerCircles = new Circlef[8];
	{
		for (int i = 0; i < 8; i++) {
			pointerCircles[i] = new Circlef(0, 0, ScreenUtil.screen_diagonal * 0.01f);
		}
	}
	protected boolean[] track = new boolean[8];
	protected boolean[] increments = new boolean[8];

	public Button(float x, float y, float radius) {
		super(x, y, radius);
	}

	public abstract void update(float delta);

	public abstract void render(SpriteBatch sb);

	public void debugRender(ShapeRenderer sr) {

	}
}
