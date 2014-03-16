package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Heart {

	public static final short FULL = 31 * 12 + 13;
	public static final short HALF_FULL = 31 * 12 + 14;
	public static final short EMPTY = 31 * 12 + 15;

	public static void render(SpriteBatch sb, int x, int y, int health) {
		switch (health) {
		case 6:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 5:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(HALF_FULL), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 4:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 3:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(HALF_FULL), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 2:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 1:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(HALF_FULL), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size * 2, y, Map.size, Map.size);
			break;
		default:
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(EMPTY), x + Map.size * 2, y, Map.size, Map.size);
			break;
		}
	}

}