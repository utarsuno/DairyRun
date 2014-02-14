package com.uladzislau.dairy_run.manager.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;

public class House {

	public static final byte TAN_HOUSE = 0;
	public static final byte DARK_BLUE_HOUSE = 3;
	public static final byte LIGHT_BLUE_HOUSE = 6;

	public static final byte BROWN_ROOF = 0;
	public static final byte YELLOW_ROOF = 3;
	public static final byte BLUE_ROOF = 6;

	public void render(SpriteBatch sb, int x, int y, int width, int height, byte house, byte roof) {

		// Layer 0.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 25 + house), x, y,
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()); // Bottom
																																	// left.
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 25 + 1 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 25 + 2 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2, y, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		// Layer 1.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 23 + house), x,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 23 + 1 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 23 + 2 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		// Layer 2.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 24 + house), x,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 24 + 1 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 24 + 2 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()
				* 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		// /////////

		// Layer 3.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + roof),
				x - TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()
						* 3, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 1 + roof), x, y
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 3, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 1 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()
						* 3, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 1 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 3, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 2 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 3,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 3, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		// Layer 4.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + roof), x,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 1 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 2 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		// Layer 5.

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + roof), x,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 1 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()
						* 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 26 + 2 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * 2,
				y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 2, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

	}

}
