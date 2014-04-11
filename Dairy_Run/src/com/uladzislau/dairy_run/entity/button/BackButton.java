package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.game_state.GameStateManager;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public class BackButton extends CircleButton {

	private DairyRun dairyRun;

	public BackButton(float x, float y, float radius, DairyRun dairyRun) {
		super(x, y, radius);
		this.dairyRun = dairyRun;
		super.inititialize();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void render(SpriteBatch sb, ColorXv colorXv) {
		Color temp = sb.getColor();
		sb.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 25), this.getX() - this.getRadius(), this.getY() - this.getRadius(),
				this.getRadius() * 2, this.getRadius() * 2);
		sb.setColor(temp);
	}

	@Override
	public void doButtonAction() {
		this.dairyRun.getGameStateManager().changeState(GameStateManager.PREVIOUS_STATE);
	}

	@Override
	public void render(SpriteBatch sb) {
	}

}
