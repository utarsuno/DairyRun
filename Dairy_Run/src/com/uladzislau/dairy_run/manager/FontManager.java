package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontManager {

	public static final short _0 = 31 * 14 + 14;
	public static final short _1 = 31 * 14 + 15;
	public static final short _2 = 31 * 14 + 16;
	public static final short _3 = 31 * 14 + 17;
	public static final short _4 = 31 * 14 + 18;
	public static final short _5 = 31 * 14 + 19;
	public static final short _6 = 31 * 15 + 13;
	public static final short _7 = 31 * 15 + 14;
	public static final short _8 = 31 * 15 + 15;
	public static final short _9 = 31 * 15 + 16;
	public static final short _x = 31 * 15 + 17;
	public static final short _period = 31 * 15 + 18;

	public static enum FONT {
		DEFAULT_FONT("Default font");

		private final String name;

		FONT(String name) {
			this.name = name;
		}

		public void render(SpriteBatch sb, String string, int x, int y, int size) {
			char[] c = string.toCharArray();
			for (int i = 0; i < c.length; i++) {
				switch (c[i]) {
				case '0':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_0), x + i * ((size / 2)), y, size, size);
					break;
				case '1':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_1), x + i * (size / 2), y, size, size);
					break;
				case '2':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_2), x + i * (size / 2), y, size, size);
					break;
				case '3':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_3), x + i * (size / 2), y, size, size);
					break;
				case '4':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_4), x + i * (size / 2), y, size, size);
					break;
				case '5':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_5), x + i * (size / 2), y, size, size);
					break;
				case '6':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_6), x + i * (size / 2), y, size, size);
					break;
				case '7':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_7), x + i * (size / 2), y, size, size);
					break;
				case '8':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_8), x + i * (size / 2), y, size, size);
					break;
				case '9':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_9), x + i * (size / 2), y, size, size);
					break;
				case 'x':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_x), x + i * (size / 2), y, size, size);
					break;
				case '.':
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(_period), x + i * (size / 2), y, size, size);
				default:
					break;
				}
			}
		}

		public String getName() {
			return name;
		}

	}

}