package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class JumpButton extends Button {

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
		this.jump_time = 1000;
	}

	public void update(float delta) {

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
		for (int i = 0; i < this.increments.length; i++) {
			if (this.increments[i]) {
				if (!this.currently_jumping) {
					this.currently_jumping = true;
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
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 23), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
				Map.size, Map.size);
	}

}
