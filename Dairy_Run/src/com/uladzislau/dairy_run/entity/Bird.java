package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;

public class Bird {
	
	private int x;
	private int y;
	
	public void render(SpriteBatch sb, int current_scroll) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 10 + 15), x + current_scroll, y, Map.size, Map.size);
	}
	
}
