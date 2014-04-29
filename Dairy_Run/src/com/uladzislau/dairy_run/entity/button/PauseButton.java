package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.manager.TextureManager;

public class PauseButton extends CircleButton {

	private DairyRun dr;

	public PauseButton(float x, float y, float radius, DairyRun dr) {
		super(x, y, radius);
		this.dr = dr;
		super.inititialize();
	}

	@Override
	public void doButtonAction() {
		this.dr.getGameStateManager().inStatePause();
	}

	@Override
	public void render(SpriteBatch sb) {
	}

	@Override
	public void render(SpriteBatch sb, ColorXv colorXv) {
		Color temp = sb.getColor();
		sb.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
		// Render the button.
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.PAUSE), this.getX() - this.getRadius(), this.getY() - this.getRadius(),
				this.getRadius() * 2, this.getRadius() * 2);
		sb.setColor(temp);
	}

}
