package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.game_state.GameStateManager;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public class BackButton extends CircleButton {

	private Circlef[] pointerCircles = new Circlef[8];
	{
		for (int i = 0; i < 8; i++) {
			this.pointerCircles[i] = new Circlef(0, 0, ScreenUtil.screen_diagonal * 0.01f);
		}
	}
	private boolean[] track = new boolean[8];
	private boolean[] increments = new boolean[8];

	private DairyRun dairyRun;

	public BackButton(float x, float y, float radius, DairyRun dr) {
		super(x, y, radius);
		this.dairyRun = dr;
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
				this.dairyRun.getGameStateManager().changeState(GameStateManager.PREVIOUS_STATE);
				this.increments[i] = false;
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 25), this.getX() - this.getRadius(),
				this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
	}

}
