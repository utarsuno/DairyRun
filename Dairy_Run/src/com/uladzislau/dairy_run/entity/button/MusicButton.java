package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;

public class MusicButton extends CircleButton {

	public MusicButton(float x, float y, float radius) {
		super(x, y, radius);
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
		// Render the button.
		if (AudioManager.isMusicOn()) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 23), this.getX() - this.getRadius(), this.getY() - this.getRadius(),
					this.getRadius() * 2, this.getRadius() * 2);
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 24), this.getX() - this.getRadius(), this.getY() - this.getRadius(),
					this.getRadius() * 2, this.getRadius() * 2);
		}
		sb.setColor(temp);
	}

	@Override
	public void doButtonAction() {
		AudioManager.setMusicOn(!AudioManager.isMusicOn());
	}

	@Override
	public void render(SpriteBatch sb) {
	}

}
