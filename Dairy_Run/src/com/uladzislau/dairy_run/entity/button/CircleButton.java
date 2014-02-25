package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public abstract class CircleButton extends Circlef {

	protected Circlef[] pointerCircles = new Circlef[8];
	{
		for (int i = 0; i < 8; i++) {
			pointerCircles[i] = new Circlef(0, 0, ScreenUtil.screen_diagonal * 0.01f);
		}
	}
	protected boolean[] track = new boolean[8];
	protected boolean[] increments = new boolean[8];

	public CircleButton(float x, float y, float radius) {
		super(x, y, radius);
	}

	public void update(float delta) {
		// TODO: Fix this to allow two buttons to be pressed at the same time.
		for (int i = 0; i < InputManager.pointers.length; i++) {
			pointerCircles[i].setX(InputManager.pointers[i].x);
			pointerCircles[i].setY(InputManager.pointers[i].y);
		}
		for (int j = 0; j < InputManager.pointersDown.length; j++) {
			if (InputManager.pointersDown[j]) {
				if (this.isCollidingWithAnotherCirclef(pointerCircles[j])) {
					if (track[j]) {
						this.increments[j] = true;
						this.track[j] = false;
					}
				} else {
					this.increments[j] = false;
				}
			} else {
				this.track[j] = true;
			}
		}
	}

	public abstract void render(SpriteBatch sb);

	public void debugRender(ShapeRenderer sr) {

	}
}
