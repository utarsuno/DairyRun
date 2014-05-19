package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;

public class MusicButton extends CircleButton {

	private DairyRun dr;

	public MusicButton(float x, float y, float radius, DairyRun dr) {
		super(x, y, radius);
		super.inititialize();
		this.dr = dr;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void render(ColorXv colorXv) {
		Color temp = ResourceManager.getSpriteBatch().getColor();
		ResourceManager.getSpriteBatch().setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
		// Render the button.
		if (AudioManager.isMusicOn()) {
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 23), this.getX() - this.getRadius(),
					this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
		} else {
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 24), this.getX() - this.getRadius(),
					this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
		}
		ResourceManager.getSpriteBatch().setColor(temp);
	}

	@Override
	public void doButtonAction() {
		AudioManager.toggleAllAudio();
		if (AudioManager.isAudioOn()) {
			if (this.dr.getGameStateManager().current_state.getMusic().isPaused()) {
				this.dr.getGameStateManager().current_state.getMusic().play();
			}
		} else {
			if (this.dr.getGameStateManager().current_state.getMusic().isPlaying()) {
				this.dr.getGameStateManager().current_state.getMusic().pause();
			}
		}
	}

	@Override
	public void render() {
	}

}
