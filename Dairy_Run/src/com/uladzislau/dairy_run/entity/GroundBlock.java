package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.world.Map;

public class GroundBlock extends Entity {

	public enum Theme {
		GRASS, SNOW;
	}

	private Theme theme;

	private int length;

	private boolean regular_block;
	private boolean lower_block_is_regular;

	private boolean hasMouseDownTouchedMe;
	private boolean hasMouseDownTouchedLowerMe;

	public GroundBlock(int x, int y, int width, int height, int length, Theme theme) {
		super(x, y, width, height);
		this.length = length;
		this.regular_block = true;
		this.lower_block_is_regular = true;
		this.hasMouseDownTouchedMe = false;
		this.theme = theme;
	}

	public GroundBlock() {
		super(0, 0, 0, 0);
	}

	public void randomize() {
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 40) == 5) {
			this.regular_block = false;
		} else {
			this.regular_block = true;
		}
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 40) == 5) {
			this.lower_block_is_regular = false;
		} else {
			this.lower_block_is_regular = true;
		}
	}

	public void setPosition(boolean forward) {
		if (forward) {
			setX(getX() + this.length * Map.size);
		} else {
			setX(getX() - (this.length * Map.size));
		}
	}

	public void update(int current_scroll) {
		if (getX() + Map.size + current_scroll < 0) {
			randomize();
			setPosition(true);
		}
		if (InputManager.pointersDown[0]) {
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, this.getX() + current_scroll,
					this.getY() - this.getHeight(), this.getWidth(), this.getHeight())) {
				if (!this.hasMouseDownTouchedMe) {
					this.regular_block ^= true;
					this.hasMouseDownTouchedMe = true;
				}
			}
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, this.getX() + current_scroll,
					this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight())) {
				if (!this.hasMouseDownTouchedLowerMe) {
					this.lower_block_is_regular ^= true;
					this.hasMouseDownTouchedLowerMe = true;
				}
			}
		} else {
			this.hasMouseDownTouchedMe = false;
			this.hasMouseDownTouchedLowerMe = false;
		}
	}

	public void update(float delta) {
		if (InputManager.pointersDown[0]) {
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, this.getX(), this.getY() - this.getHeight(),
					this.getWidth(), this.getHeight())) {
				if (!this.hasMouseDownTouchedMe) {
					this.regular_block ^= true;
					this.hasMouseDownTouchedMe = true;
				}
			}
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, this.getX(), this.getY() - this.getHeight() * 2,
					this.getWidth(), this.getHeight())) {
				if (!this.hasMouseDownTouchedLowerMe) {
					this.lower_block_is_regular ^= true;
					this.hasMouseDownTouchedLowerMe = true;
				}
			}
		} else {
			this.hasMouseDownTouchedMe = false;
			this.hasMouseDownTouchedLowerMe = false;
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.regular_block) {
			switch (this.theme) {
			case GRASS:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.GRASS), this.getX() + current_scroll,
						this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
				break;
			case SNOW:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.SNOW), this.getX() + current_scroll,
						this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
				break;
			default:
				break;
			}
		} else {
			switch (this.theme) {
			case GRASS:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.GRASS_WITH_SMILE), this.getX() + current_scroll, this.getY()
						- this.getHeight(), this.getWidth(), this.getHeight());
				break;
			case SNOW:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.SNOW_WITH_SMILE), this.getX() + current_scroll,
						this.getY() - this.getHeight(), this.getWidth(), this.getHeight());
				break;
			default:
				break;
			}
		}

		if (this.lower_block_is_regular) {
			switch (this.theme) {
			case GRASS:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.DIRT), this.getX() + current_scroll,
						this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight());
				break;
			case SNOW:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.SNOW_DIRT), this.getX() + current_scroll,
						this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight());
				break;
			default:
				break;
			}
		} else {
			switch (this.theme) {
			case GRASS:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.DIRT_WITH_SMILE), this.getX() + current_scroll,
						this.getY() - this.getHeight() * 2, this.getWidth(), this.getHeight());
				break;
			case SNOW:
				sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.SNOW_DIRT_WITH_SMILE), this.getX() + current_scroll, this.getY()
						- this.getHeight() * 2, this.getWidth(), this.getHeight());
				break;
			default:
				break;
			}
		}
	}

}