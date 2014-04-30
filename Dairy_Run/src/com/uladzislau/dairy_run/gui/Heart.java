package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Heart {

	public static void render(SpriteBatch sb, int x, int y, int health) {
		switch (health) {
		case 5:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 4:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 3:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 2:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 1:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 0:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		default:
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		}
	}

}