package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerupButton extends CircleButton {

	public enum POWER {
		time_slow, screen_clear, nuclear;
	}

	private POWER power;
	private int stock = 0;

	public PowerupButton(float x, float y, float radius) {
		super(x, y, radius);
	}

	@Override
	public void doButtonAction() {
		switch (this.power) {
		case nuclear:
			break;
		case screen_clear:
			break;
		case time_slow:
			break;
		default:
			break;
		}
		this.stock--;
	}

	@Override
	public void render(SpriteBatch sb) {
		switch (this.power) {
		case nuclear:
			break;
		case screen_clear:
			break;
		case time_slow:
			break;
		default:
			break;
		}
	}

	public POWER getPower() {
		return this.power;
	}

	public void setPower(POWER power) {
		this.power = power;
	}

	public int getStock() {
		return this.stock;
	}

	public void incrementStock() {
		this.stock++;
	}

}
