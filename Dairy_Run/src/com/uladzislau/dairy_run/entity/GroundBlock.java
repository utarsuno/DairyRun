package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;

public class GroundBlock {

	private int x;
	private int y;
	private int length;

	private boolean regular_block;
	// TODO: This may or may not be added later.
	private boolean water_block;

	public GroundBlock(int x, int y, int length) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.water_block = false;
	}

	private void randomize(int current_scroll) {
		setX(getX() + this.length * Map.size);
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 100) == 5) {
			this.regular_block = true;
		} else {
			this.regular_block = false;
			if (Dice.nextBoolean()) {
				this.water_block = true;
			} else {
				this.water_block = false;
			}
		}
	}

	public void update(int current_scroll) {
		if (getX() + Map.size + current_scroll < 0) {
			randomize(current_scroll);
		}
	}

	public void render(SpriteBatch sb, int x) {
		if (this.regular_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 12 + 29), x, this.y - Map.size, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 3), x, this.y - Map.size * 2, Map.size, Map.size);
		} else {
			// if (this.water_block) {
			// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 0 + 10), x, y - Map.size / 2, Map.size, Map.size);
			// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 1 + 10), x, y - (Map.size / 2) * 2, Map.size, Map.size);
			// } else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), x, this.y - Map.size, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 3), x, this.y - Map.size * 2, Map.size, Map.size);
			// }
		}
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

}