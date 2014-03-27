package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.world.Map;

public class GroundBlock extends Entity {

	public final static short GRASS = 31 * 5 + 12; // temp
	public final static short SNOW = 31 * 2 + 3;

	private int length;

	private boolean regular_block;
	private boolean regular_ground_block;
	// TODO: This may or may not be added later.
	private boolean water_block;

	private boolean grass;
	private short grass_block;

	public GroundBlock(int x, int y, int width, int height, int length) {
		super(x, y, width, height);
		this.length = length;
		this.grass = false;
		this.water_block = false;
	}

	public void randomize() {
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
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 16) == 5) {
			if (Dice.nextBoolean()) {
				grass_block = 17;
			} else {
				grass_block = 17 + 30;
			}
			this.grass = true;
		} else {
			this.grass = false;
		}
	}

	public void setPosition(boolean forward) {
		if (forward) {
			setX(getX() + this.length * Map.size);
		} else {
			setX(getX() - (this.length * Map.size));
		}
	}

	public void update(int current_scroll) {
		if (getX() + Map.size + current_scroll < 0) {
			randomize();
			setPosition(true);
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.regular_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 12 + 29), this.getX() + current_scroll, this.getY() - this.getHeight(),
					this.getWidth(), this.getHeight());
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), this.getX() + current_scroll, this.getY() - this.getHeight(),
					this.getWidth(), this.getHeight());
		}

		if (this.regular_ground_block) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 3), this.getX() + current_scroll, this.getY() - this.getHeight() * 2,
					this.getWidth(), this.getHeight());
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 5 + 4), this.getX() + current_scroll, this.getY() - this.getHeight() * 2,
					this.getWidth(), this.getHeight());
		}

		if (this.grass) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.grass_block), this.getX() + current_scroll, this.getY(), this.getWidth(),
					this.getHeight());
		}
	}

	public static void render(SpriteBatch sb, int y, short type) {
		switch (type) {
		case SNOW:
			int tx = 0;
			while (tx < ScreenUtil.screen_width + Map.size) {
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(SNOW), tx, y, Map.size, Map.size);
				tx += Map.size;
			}
			break;
		default:
			break;
		}
	}

}