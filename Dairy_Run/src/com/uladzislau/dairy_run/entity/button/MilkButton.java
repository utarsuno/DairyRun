package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public class MilkButton extends CircleButton {

	public static final short REGULAR = 31 * 6 + 20;
	public static final short CHOCOLATE = 31 * 6 + 21;
	public static final short STRAWBERRY = 31 * 6 + 22;

	private short milk_type;

	private Circlef[] pointerCircles = new Circlef[8];
	{
		for (int i = 0; i < 8; i++) {
			this.pointerCircles[i] = new Circlef(0, 0, ScreenUtil.screen_diagonal * 0.01f);
		}
	}
	private boolean[] track = new boolean[8];
	private boolean[] increments = new boolean[8];

	private Play play;

	public MilkButton(float x, float y, float radius, short type, Play play) {
		super(x, y, radius);
		this.play = play;
		this.milk_type = type;
	}

	@Override
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
				deliverMilk();
				this.increments[i] = false;
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		// Render the button.
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.milk_type), this.getX() - Map.size / 2, this.getY() - Map.size
				/ 2, Map.size, Map.size);
	}

	private void deliverMilk() {
		this.play.attemptToDeliver(this.milk_type);
	}

}
