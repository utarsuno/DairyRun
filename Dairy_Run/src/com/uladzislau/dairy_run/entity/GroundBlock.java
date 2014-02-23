package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;

public class GroundBlock {

	private int x;
	private int y;
	private int length;

	private boolean regular_block;

	public GroundBlock(int x, int y, int length) {
		this.x = x;
		this.y = y;
		this.length = length;
	}

	private void randomize(int current_scroll) {
		setX(getX() + this.length * Map.size);
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 20) == 0) {
			regular_block = true;
		} else {
			regular_block = false;
		}
	}

	public void update(int current_scroll) {
		if (getX() + Map.size + current_scroll < 0) {
			randomize(current_scroll);
		}
	}

	public void render(SpriteBatch sb, int x) {
		if (this.regular_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 12 + 29), x, y - Map.size, Map.size, Map.size);
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), x, y - Map.size, Map.size, Map.size);
		}
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 3), x, y - Map.size * 2, Map.size, Map.size);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}