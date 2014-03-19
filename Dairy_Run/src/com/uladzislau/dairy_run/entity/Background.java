package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public void render(SpriteBatch sb, int current_scroll) {
		switch (this.type) {
		case BLUE:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BLUE, this.getX() + current_scroll, this.getY());
			break;
		case GREEN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, GREEN, this.getX() + current_scroll, this.getY());
			break;
		case BROWN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, BROWN, this.getX() + current_scroll, this.getY());
			break;
		default:
			break;
		}
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return this.type;
	}

}