package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.TextureManager;

public class Player {

	public static final byte MAX_LIFE = 6;

	private int original_x;
	private int x;
	private int y;
	private byte life;

	private boolean render_player;

	private Play play;

	public Player(int x, int y, Play play) {
		this.life = MAX_LIFE;
		this.original_x = x;
		this.x = x;
		this.y = y;
		this.play = play;
		render_player = true;
	}

	public void update(float delta) {
		TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.update(delta);
		TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.setFrameTime((int) (80 / (this.play.getVelocity() / 5)));
	}

	public void render(SpriteBatch sb, int current_scroll) {

		if (render_player) {
			// Render the player.
			sb.draw(TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrame(), this.x, this.y, Map.size, Map.size);
		}

		// Render the player's health at the top right of the screen.
		Heart.render(sb, ScreenUtil.screen_width - Map.size * 3, ScreenUtil.screen_height - Map.size, life);
	}

	public byte getLife() {
		return life;
	}

	public void setLife(byte life) {
		this.life = life;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXToOriginalX() {
		this.x = this.original_x;
	}

	public void renderPlayer(boolean b) {
		this.render_player = b;
	}

}
