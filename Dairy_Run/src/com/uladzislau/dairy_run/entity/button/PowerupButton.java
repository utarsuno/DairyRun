package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.gui.AnimatedText;
import com.uladzislau.dairy_run.gui.AnimatedText.AnimatedTextType;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class PowerUpButton extends CircleButton {

	public enum Power {
		TIME_SLOW, SCREEN_CLEAR, NUCLEAR;
	}

	private Power power;
	private int stock = 0;
	private Play play;

	private DeltaTimer deltaTimer;
	private AnimatedText animated_text;

	private float old_velocity;
	private boolean power_is_currently_occuring;

	public PowerUpButton(float x, float y, float radius, Power power, SpriteBatch sb, Play play) {
		super(x, y, radius);
		this.power = power;
		this.play = play;
		this.deltaTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 5000);
		super.inititialize();
		inititialize();
		this.power_is_currently_occuring = false;
		this.play.setColorToRenderTimeIn(Color.RED);
	}

	@Override
	public void inititialize() {
		switch (this.power) {
		case NUCLEAR:
			this.animated_text = new AnimatedText(ScreenUtil.screen_width / 2, ScreenUtil.screen_height / 2, ScreenUtil.screen_height / 2
					+ Map.size / 2, "+1 slow down".length() * Map.size, Map.size, "+1 slow down", new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f,
					0.0f, 0.0f), 2000, AnimatedTextType.FADE_UP);
			break;
		case SCREEN_CLEAR:
			this.animated_text = new AnimatedText(ScreenUtil.screen_width / 2, ScreenUtil.screen_height / 2, ScreenUtil.screen_height / 2 + Map.size,
					"+1 house clearer".length() * Map.size, Map.size, "+1 house clearer", new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 0.0f),
					2000, AnimatedTextType.FADE_UP);
			break;
		case TIME_SLOW:
			this.animated_text = new AnimatedText(ScreenUtil.screen_width / 2, ScreenUtil.screen_height / 2, ScreenUtil.screen_height / 2
					+ Map.size / 2 + Map.size, "+1 speed up".length() * Map.size, Map.size, "+1 speed up", new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f,
					0.0f, 0.0f, 0.0f), 2000, AnimatedTextType.FADE_UP);
			break;
		default:
			break;
		}
		this.animated_text.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		this.animated_text.update(delta);
		this.deltaTimer.update(delta);

		if (this.power_is_currently_occuring) {
			switch (this.power) {
			case NUCLEAR:
				this.play.setTimeToRender(((int) ((5000.0f - this.deltaTimer.getTotalDelta()) / 1000.0f)) + 1);
				break;
			case TIME_SLOW:
				this.play.setTimeToRender(((int) ((5000.0f - this.deltaTimer.getTotalDelta()) / 1000.0f)) + 1);
				break;
			// $CASES-OMITTED$
			default:
				break;
			}
		}

		if (this.deltaTimer.isFinished() && this.power_is_currently_occuring) {
			switch (this.power) {
			case NUCLEAR:
				this.play.setVelocity(this.old_velocity);
				break;
			case TIME_SLOW:
				this.play.setVelocity(this.old_velocity);
				break;
				//$CASES-OMITTED$
			default:
				break;
			}
			this.power_is_currently_occuring = false;
			this.play.setCustomTimer(false);
		}
	}

	@Override
	public void doButtonAction() {
		// TODO: Fully implement this
		if (this.stock > 0 && !this.power_is_currently_occuring) {
			this.power_is_currently_occuring = true;
			this.deltaTimer.reset();
			switch (this.power) {
			case NUCLEAR:
				this.play.setCustomTimer(true);
				this.old_velocity = this.play.getVelocity();
				break;
			case SCREEN_CLEAR:
				this.play.clearNextHouses(5);
				this.deltaTimer.finish();
				break;
			case TIME_SLOW:
				this.old_velocity = this.play.getVelocity();
				this.play.setVelocity(this.play.getVelocity() / 5.0f);
				this.play.setCustomTimer(true);
				break;
			default:
				break;
			}
			this.stock--;
		}
	}

	@Override
	public void render() {
		this.animated_text.render();
		if (this.stock == 0) {
			ResourceManager.getSpriteBatch().setColor(ResourceManager.getSpriteBatch().getColor().r, ResourceManager.getSpriteBatch().getColor().r,
					ResourceManager.getSpriteBatch().getColor().r, 0.2f);
		}
		switch (this.power) {
		case NUCLEAR:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_THREE),
					this.getX() - Map.size / 2, this.getY() - Map.size / 2, Map.size, Map.size);
			break;
		case SCREEN_CLEAR:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_TWO),
					this.getX() - Map.size / 2, this.getY() - Map.size / 2, Map.size, Map.size);
			break;
		case TIME_SLOW:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.POWER_UP_ONE),
					this.getX() - Map.size / 2, this.getY() - Map.size / 2, Map.size, Map.size);
			break;
		default:
			break;
		}
		if (this.stock == 0) {
			ResourceManager.getSpriteBatch().setColor(ResourceManager.getSpriteBatch().getColor().r, ResourceManager.getSpriteBatch().getColor().r,
					ResourceManager.getSpriteBatch().getColor().r, 1.0f);
		}
	}

	@Override
	public void reset() {
		this.stock = 0;
		this.power_is_currently_occuring = false;
		this.play.setCustomTimer(false);
		this.animated_text.end();
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
		this.animated_text.play();
	}

}
