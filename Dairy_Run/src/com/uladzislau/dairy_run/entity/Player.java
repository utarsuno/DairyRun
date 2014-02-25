package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;

public class Player {

	public static final byte MAX_LIFE = 6;

	private int original_x;
	private int x;
	private int y;
	private byte life;

	private Rectanglei player_rectanglei;

	private int number_of_milks_delivered;

	private boolean render_player;

	private Play play;

	public Player(int x, int y, Play play) {
		this.life = MAX_LIFE;
		this.original_x = x;
		this.x = x;
		this.y = y;
		this.play = play;
		this.player_rectanglei = new Rectanglei(x, y, Map.size, Map.size);
		render_player = true;
	}

	public void update(float delta, int current_scroll) {
		this.player_rectanglei.setX(this.x);
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

	public void debugRender(ShapeRenderer sr) {
		sr.begin(ShapeType.Line);
		sr.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		sr.rect(this.getRectanglei().getX(), this.getRectanglei().getY(), Map.size, Map.size);
		sr.end();
	}

	public void loseOneLife() {
		if (life >= 0) {
			int r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
			if (r == 0) {
				AudioManager.SOUND.PAIN_ONE.playSound();
			} else if (r == 1) {
				AudioManager.SOUND.PAIN_TWO.playSound();
			} else if (r == 2) {
				AudioManager.SOUND.PAIN_THREE.playSound();
			}
		}
		this.life--;
		if (this.life < 0) {
			if (Dice.nextBoolean()) {
				AudioManager.SOUND.DEATH_ONE.playSound();
			} else {
				AudioManager.SOUND.DEATH_TWO.playSound();
			}
		}
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

	public void incrementNumberOfMilksDelivered() {
		number_of_milks_delivered++;
	}

	public int getNumberOfMilksDelivered() {
		return number_of_milks_delivered;
	}

	public Rectanglei getRectanglei() {
		return this.player_rectanglei;
	}

}
