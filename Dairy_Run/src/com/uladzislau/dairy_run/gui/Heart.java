package com.uladzislau.dairy_run.gui;

import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Heart {

	public static void render(int x, int y, int health) {
		switch (health) {
		case 11:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 3, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 10:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 3, y, Map.size, Map.size);

			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 3, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 9:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 3, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 8:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x - Map.size * 3, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 7:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 6:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x - Map.size * 2, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 5:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 4:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 3:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 2:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 1:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		case 0:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.HALF_FULL_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		default:
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size, y, Map.size, Map.size);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.EMPTY_HEART), x + Map.size * 2, y, Map.size, Map.size);
			break;
		}
	}

}