package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.TextureManager;

public class Background extends Entity {

	public static final float SCROLL_RATE = (1.0f / 6.0f);

	public static final byte BLUE = 0;
	public static final byte GREEN = 1;
	public static final byte BROWN = 2;

	private byte type;

	public Background(int x, int y, int width, int height, byte type) {
		super(x, y, width, height);
		setType(type);
	}

	public void update(int current_scroll) {
		if (getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() + current_scroll * Background.SCROLL_RATE < 0) {
			setX(getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() * 2);
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		// Make sure background is on-screen before rendering.
		if (getX() + current_scroll * Background.SCROLL_RATE < ScreenUtil.screen_width) {
			switch (this.type) {
			case BLUE:
				TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BLUE, (int) (this.getX() + current_scroll * Background.SCROLL_RATE),
						this.getY());
				break;
			case GREEN:
				TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, GREEN, (int) (this.getX() + current_scroll * Background.SCROLL_RATE),
						this.getY());
				break;
			case BROWN:
				TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BROWN, (int) (this.getX() + current_scroll * Background.SCROLL_RATE),
						this.getY());
				break;
			default:
				break;
			}
		}
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return this.type;
	}

	public static void render(SpriteBatch sb, byte t) {
		switch (t) {
		case BLUE:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BLUE, 0, 0);
			break;
		case GREEN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, GREEN, 0, 0);
			break;
		case BROWN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BROWN, 0, 0);
			break;
		default:
			break;
		}
	}
}