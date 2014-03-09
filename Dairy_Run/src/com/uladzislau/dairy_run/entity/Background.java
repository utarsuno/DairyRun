package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.TextureManager;

public class Background {

	public static final float SCROLL_RATE = (1.0f / 6.0f);

	public static final byte BLUE = 0;
	public static final byte GREEN = 1;
	public static final byte BROWN = 2;

	private byte type;

	private int x;

	public Background(int x, byte type) {
		this.x = x;
		this.type = type;
	}

	public void render(SpriteBatch sb) {
		switch (this.type) {
		case BLUE:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 0, this.x, 0);
			break;
		case GREEN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 1, this.x, 0);
			break;
		case BROWN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 2, this.x, 0);
			break;
		default:
			break;
		}
	}

	public void render(SpriteBatch sb, int xp) {
		switch (this.type) {
		case BLUE:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 0, xp, 0);
			break;
		case GREEN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 1, xp, 0);
			break;
		case BROWN:
			TextureManager.SPRITESHEET.BACKGROUNDS.render(sb, 2, xp, 0);
			break;
		default:
			break;
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return this.type;
	}

}