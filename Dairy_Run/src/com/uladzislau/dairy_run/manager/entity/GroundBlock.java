package com.uladzislau.dairy_run.manager.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;

public class GroundBlock {
	
	private int x;
	
	public GroundBlock(int x) {
		this.x = x;
	}

	public void render(SpriteBatch sb, int x) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), x, 0, Map.size, Map.size);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
}
