package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class JumpButton extends CircleButton {

	private Play play;

	private boolean currently_jumping;
	private boolean falling_down;
	private DeltaTimer deltaTimer;
	private int jump_height;
	private float jump_time;

	public JumpButton(float x, float y, float radius, Play play) {
		super(x, y, radius);
		this.play = play;
		this.currently_jumping = false;
		this.falling_down = false;
		this.deltaTimer = new DeltaTimer();
		this.jump_height = Map.size * 3;
		this.jump_time = 600;
	}

	private boolean play_sound = true;
	private boolean play_landing_sound = false;
	
	@Override
	public void update(float delta) {
		super.update(delta);
		for (int i = 0; i < this.increments.length; i++) {
			if (this.increments[i]) {
				if (!this.currently_jumping) {
					this.currently_jumping = true;
					if (this.play_sound) {
						AudioManager.SOUND.JUMP.playSound();
						this.play_sound = false;
					}
					this.play_landing_sound = true;
					this.increments[i] = false;
				}
			}
		}
		if (this.currently_jumping) {
			this.deltaTimer.update(delta);
			if (!this.falling_down) {
				if (this.deltaTimer.getTotalDelta() < this.jump_time / 2) {
					this.play.getPlayer().setY(
							(int) (this.play.ground_level + this.jump_height * (this.deltaTimer.getTotalDelta() / this.jump_time)));
				} else {
					this.deltaTimer.setTotalDelta(0);
					this.falling_down = true;
				}
			} else {
				if (this.deltaTimer.getTotalDelta() < this.jump_time / 2) {
					this.play.getPlayer().setY(
							(int) (this.play.ground_level + this.jump_height / 2 - this.jump_height
									* (this.deltaTimer.getTotalDelta() / this.jump_time)));
				} else {
					this.currently_jumping = false;
				}
			}
		} else {
			this.deltaTimer.setTotalDelta(0);
			this.play.getPlayer().setY(this.play.ground_level);
			if (this.play_landing_sound) {
				//AudioManager.SOUND.LAND.playSound();
				this.play_landing_sound = false;
				this.play_sound = true;
			}
			this.falling_down = false;
		}

	}

	public void render(SpriteBatch sb) {
		if (this.currently_jumping) {
			this.play.getPlayer().renderPlayer(false);
			if (this.falling_down) {
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(23), this.play.getPlayer().getX(), this.play.getPlayer()
						.getY(), Map.size, Map.size);
			} else {
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(21), this.play.getPlayer().getX(), this.play.getPlayer()
						.getY(), Map.size, Map.size);
			}
		} else {
			this.play.getPlayer().renderPlayer(true);
		}
		// Render the button.
		sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 0.9f);
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 23), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
				Map.size, Map.size);
		sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 1.0f);
	}

}
