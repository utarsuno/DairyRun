package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;

public class Tree {

	public static final short TREE_HEIGHT_ONE_DESIGN_ONE = 31 * 22 + 13;
	public static final short TREE_HEIGHT_ONE_DESIGN_TWO = 31 * 21 + 13;

	private short short_tree_type;

	private int x;
	private int y;
	private int type;

	public Tree(int y) {
		this.y = y;
		randomize(0);
	}

	private void randomize(int current_scroll) {
		this.type = Dice.get_Random_Integer_From_Min_To_Max(1, 3);
		if (this.type == 1) {
			if (Dice.nextBoolean()) {
				this.short_tree_type = TREE_HEIGHT_ONE_DESIGN_ONE;
			} else {
				this.short_tree_type = TREE_HEIGHT_ONE_DESIGN_TWO;
			}
		}
		this.x += ScreenUtil.screen_width + Dice.get_Random_Integer_From_Min_To_Max(5, 20) * Map.size;
	}

	public void render(SpriteBatch sb, int x, int y, int height) {
		// // Base
		// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 1), x, y, Map.size, Map.size);
		// // Green block
		// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 9), x, y + Map.size, Map.size, Map.size);
		// // Left block
		// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 8), x - Map.size, y + Map.size, Map.size, Map.size);
		// // Right block
		// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 10), x + Map.size, y + Map.size, Map.size, Map.size);
		// // Right block
		// sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 21 + 0), x, y + Map.size * 2, Map.size, Map.size);

		if (height == 1) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 1), x, y, Map.size, Map.size);
		} else if (height == 2) {

		}

	}

	public short getShortTreeType() {
		return short_tree_type;
	}

	public void setShort_tree_type(byte type) {
		this.short_tree_type = type;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void update(int current_scroll) {
		if (this.x + current_scroll + Map.size < 0) {
			randomize(current_scroll);
		}
	}

	public void render(SpriteBatch batch, int current_scroll) {
		if (type == 1) {
			batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.short_tree_type), x + current_scroll, y, Map.size,
					Map.size);
		} else if (type == 2) {

		}
	}

}
