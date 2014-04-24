package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Bird extends Entity {

	public Bird(Play play) {
		super(play);
	}

	public void render(SpriteBatch sb, int current_scroll) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 10 + 15), getX() + current_scroll, getY(), Map.size, Map.size);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render() {		
	}

}
