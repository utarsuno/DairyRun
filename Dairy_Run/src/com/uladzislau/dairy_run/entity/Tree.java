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

	private int total_number_of_trees;

	public Tree(int y, int total_number_of_trees) {
		this.y = y;
		this.total_number_of_trees = total_number_of_trees;
		randomize();
	}

	private void randomize() {
		this.type = Dice.get_Random_Integer_From_Min_To_Max(1, 2);
		if (this.type == 1) {
			if (Dice.nextBoolean()) {
				this.short_tree_type = TREE_HEIGHT_ONE_DESIGN_ONE;
			} else {
				this.short_tree_type = TREE_HEIGHT_ONE_DESIGN_TWO;
			}
		}
		this.x += ScreenUtil.screen_width + Dice.get_Random_Integer_From_Min_To_Max(5, 20) * Map.size;
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
		if (this.x + current_scroll + Map.size * 2 < 0) {
			randomize();
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (type == 1) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.short_tree_type), x + current_scroll, y, Map.size, Map.size);
		} else if (type == 2) {
			// Base
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 1), x + current_scroll, y, Map.size, Map.size);
			// Green block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 9), x + current_scroll, y + Map.size, Map.size,
					Map.size);
			// Green block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 9), x + current_scroll, y + Map.size * 2, Map.size,
					Map.size);
			// Left block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 8), x - Map.size + current_scroll, y + Map.size,
					Map.size, Map.size);
			// Left block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 8), x - Map.size + current_scroll, y + Map.size * 2,
					Map.size, Map.size);
			// Right block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 10), x + Map.size + current_scroll, y + Map.size,
					Map.size, Map.size);
			// Right block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 10), x + Map.size + current_scroll, y + Map.size * 2,
					Map.size, Map.size);
			// Top block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 21 + 0), x + current_scroll, y + Map.size * 3, Map.size,
					Map.size);

		}
	}

}
