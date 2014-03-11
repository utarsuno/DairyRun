package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;

public class Tree {

	public static final short TREE_HEIGHT_ONE_DESIGN_ONE = 31 * 22 + 13;
	public static final short TREE_HEIGHT_ONE_DESIGN_TWO = 31 * 21 + 13;

	private short short_tree_type;

	private int x;
	private int y;
	private int type;

	int position_in_array;
	int array_size;

	public Tree(int y, int i, int array_size) {
		this.position_in_array = i;
		this.array_size = array_size;
		this.y = y;
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
	}

	public void resetPosition(int current_scroll) {
		int r = Dice.get_Random_Integer_From_Min_To_Max(0, 1);
		if (Dice.nextBoolean()) {
			r *= -1;
		}
		if (current_scroll != 0) {
			this.x += this.array_size * Map.size;
		} else {
			this.x = this.position_in_array * Map.size * 3 + r * Map.size;
		}
	}

	public short getShortTreeType() {
		return this.short_tree_type;
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
			resetPosition(current_scroll);
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.type == 1) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.short_tree_type), this.x + current_scroll, this.y, Map.size,
					Map.size);
		} else if (this.type == 2) {
			// Base
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 1), this.x + current_scroll, this.y, Map.size, Map.size);
			// Green block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 9), this.x + current_scroll, this.y + Map.size,
					Map.size, Map.size);
			// Green block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 9), this.x + current_scroll, this.y + Map.size * 2,
					Map.size, Map.size);
			// Left block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 8), this.x - Map.size + current_scroll, this.y
					+ Map.size, Map.size, Map.size);
			// Left block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 8), this.x - Map.size + current_scroll, this.y
					+ Map.size * 2, Map.size, Map.size);
			// Right block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 10), this.x + Map.size + current_scroll, this.y
					+ Map.size, Map.size, Map.size);
			// Right block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 22 + 10), this.x + Map.size + current_scroll, this.y
					+ Map.size * 2, Map.size, Map.size);
			// Top block
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 21 + 0), this.x + current_scroll, this.y + Map.size * 3,
					Map.size, Map.size);

		}
	}

}
