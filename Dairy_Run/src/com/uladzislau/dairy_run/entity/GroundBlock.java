package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.world.Map;

public class GroundBlock extends Entity {

	private int length;

	private boolean regular_block;
	private boolean regular_ground_block;
	// TODO: This may or may not be added later.
	private boolean water_block;

	public GroundBlock(int x, int y, int width, int height, int length) {
		super(x, y, width, height);
		this.length = length;
		this.water_block = false;
	}

	private void randomize(int current_scroll) {
		setX(getX() + this.length * Map.size);
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 40) == 5) {
			this.regular_block = true;
		} else {
			this.regular_block = false;
			if (Dice.nextBoolean()) {
				this.water_block = true;
			} else {
				this.water_block = false;
			}
		}
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 60) == 5) {
			this.regular_ground_block = true;
		} else {
			this.regular_ground_block = false;
		}
	}

	public void update(int current_scroll) {
		if (getX() + Map.size + current_scroll < 0) {
			randomize(current_scroll);
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.regular_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 12 + 29), this.getX() + current_scroll,
					this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), this.getX() + current_scroll,
					this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
		}

		if (this.regular_ground_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 3), this.getX() + current_scroll,
					this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight());
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 4), this.getX() + current_scroll,
					this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight());
		}
	}

}