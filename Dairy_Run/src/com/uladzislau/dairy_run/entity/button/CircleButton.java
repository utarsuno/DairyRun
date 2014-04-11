package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public abstract class CircleButton extends Circlef {

	private Circlef[] pointerCircles;
	private boolean[] track;
	private boolean[] increments;

	public CircleButton(float x, float y, float radius) {
		super(x, y, radius);
	}

	public void inititialize() {
		this.pointerCircles = new Circlef[InputManager.pointers.length];
		for (int i = 0; i < InputManager.pointers.length; i++) {
			this.pointerCircles[i] = new Circlef(0, 0, ScreenUtil.screen_diagonal * 0.01f);
		}
		this.track = new boolean[InputManager.pointers.length];
		this.increments = new boolean[InputManager.pointers.length];
	}

	public void update(float delta) {
		for (int i = 0; i < InputManager.pointers.length; i++) {
			this.pointerCircles[i].setX(InputManager.pointers[i].x);
			this.pointerCircles[i].setY(InputManager.pointers[i].y);
		}
		for (int j = 0; j < InputManager.pointersDown.length; j++) {
			if (InputManager.pointersDown[j]) {
				if (this.isCollidingWithAnotherCirclef(this.pointerCircles[j])) {
					if (this.track[j]) {
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
		for (int i = 0; i < this.increments.length; i++) {
			if (this.increments[i]) {
				doButtonAction();
				this.increments[i] = false;
			}
		}
	}

	public abstract void doButtonAction();

	public abstract void render(SpriteBatch sb);

	public void render(SpriteBatch sb, ColorXv colorXv) {
	}

	public boolean[] getTrack() {
		return this.track;
	}

	public void setTrack(boolean[] track) {
		this.track = track;
	}

	public boolean[] getIncrements() {
		return this.increments;
	}

	public void setIncrements(boolean[] increments) {
		this.increments = increments;
	}

	public void reset() {
	}
}
