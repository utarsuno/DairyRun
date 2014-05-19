package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class MilkButton extends CircleButton {

	private short milk_type;

	private Play play;

	public MilkButton(float x, float y, float radius, short type, Play play) {
		super(x, y, radius);
		this.play = play;
		this.milk_type = type;
		super.inititialize();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void render() {
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(this.milk_type), this.getX() - Map.size / 2,
				this.getY() - Map.size / 2, Map.size, Map.size);
	}

	@Override
	public void doButtonAction() {
		this.play.attemptToDeliver(this.milk_type);
	}

}
