package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class PowerUpButton extends CircleButton {

	public enum Power {
		TIME_SLOW, SCREEN_CLEAR, NUCLEAR;
	}

	private Power power;
	private int stock = 0;
	private Play play;

	public PowerUpButton(float x, float y, float radius, Power power, Play play) {
		super(x, y, radius);
		this.power = power;
		this.play = play;
		super.inititialize();
	}

	@Override
	public void doButtonAction() {
		// TODO: Fully implement this
		switch (this.power) {
		case NUCLEAR:
			this.play.setVelocity(this.play.getVelocity() * 5);
			break;
		case SCREEN_CLEAR:
			break;
		case TIME_SLOW:
			this.play.setVelocity(this.play.getVelocity() / 5);
			break;
		default:
			break;
		}
		this.stock--;
	}

	@Override
	public void render(SpriteBatch sb) {
		switch (this.power) {
		case NUCLEAR:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_THREE), this.getX() - Map.size / 2, this.getY() - Map.size
					/ 2, Map.size, Map.size);
			break;
		case SCREEN_CLEAR:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_TWO), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
					Map.size, Map.size);
			break;
		case TIME_SLOW:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_ONE), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
					Map.size, Map.size);
			break;
		default:
			break;
		}
	}

	public Power getPower() {
		return this.power;
	}

	public void setPower(Power power) {
		this.power = power;
	}

	public int getStock() {
		return this.stock;
	}

	public void incrementStock() {
		this.stock++;
	}

}
