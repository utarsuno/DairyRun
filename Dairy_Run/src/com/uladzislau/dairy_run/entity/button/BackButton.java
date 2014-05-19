package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.game_state.GameStateManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;

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
	public void render(ColorXv colorXv) {
		Color temp = ResourceManager.getSpriteBatch().getColor();
		ResourceManager.getSpriteBatch().setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 25), this.getX() - this.getRadius(),
				this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
		ResourceManager.getSpriteBatch().setColor(temp);
	}

	@Override
	public void doButtonAction() {
		this.dairyRun.getGameStateManager().changeState(GameStateManager.STATE.PREVIOUS_STATE);
	}

	@Override
	public void render() {
	}

}
