package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.gui.Heart;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.MathUtil;
import com.uladzislau.dairy_run.world.Map;

public class Player {

	public static final byte MAX_LIFE = 5;

	public static final short READY_TO_SPRINT = 22;

	public static final short SPRINTING = 23;

	private int original_x;
	private int x;
	private int y;
	private byte life;

	private Rectanglei player_rectanglei;

	private int number_of_milks_delivered;

	private boolean render_player;
	private boolean scared;

	private Play play;

	public Player(int x, int y, Play play) {
		this.life = MAX_LIFE;
		this.original_x = x;
		this.scared = false;
		this.x = x;
		this.y = y;
		this.play = play;
		this.player_rectanglei = new Rectanglei(x, y, Map.size, Map.size);
		this.render_player = true;
	}

	public void update(float delta, int current_scroll) {
		this.player_rectanglei.setX(this.x);
		if (this.play.getChasers().size() != 0) {
			this.scared = true;
		} else {
			this.scared = false;
		}
		if (this.scared) {
			TextureManager.Animation_Spritesheet.SAD_PIXEL_WALKING.update(delta);
			TextureManager.Animation_Spritesheet.SAD_PIXEL_WALKING.setFrameTime((int) (180 / (this.play.getVelocity() / 5)));
		} else {
			TextureManager.Animation_Spritesheet.WALKING.update(delta);
			TextureManager.Animation_Spritesheet.WALKING.setFrameTime((int) (180 / (this.play.getVelocity() / 5)));
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {

		if (this.render_player) {
			// Render the player.
			if (this.scared) {
				sb.draw(TextureManager.Animation_Spritesheet.SAD_PIXEL_WALKING.getCurrentFrame(), this.x, this.y, Map.size, Map.size);
			} else {
				sb.draw(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrame(), this.x, this.y, Map.size, Map.size);
			}
		}

	}

	public void renderPlayerStats(SpriteBatch sb, int current_scroll) {
		// Render the current velocity.
		FontManager.Font.PIXEL_REGULAR.render(sb, "" + MathUtil.round(this.play.getVelocity(), 2), Color.BLACK, Map.size * 0.1f, Map.size * 1.9f,
				ScreenUtil.screen_height - Map.size * 0.9f, ScreenUtil.screen_height - Map.size * 0.1f);

		// Render the current number of milks delivered.
		FontManager.Font.PIXEL_REGULAR.render(sb, "" + getNumberOfMilksDelivered(), Color.BLACK, Map.size * 0.1f, Map.size * 0.9f, ScreenUtil.screen_height
				- Map.size * 1.9f, ScreenUtil.screen_height - Map.size * 1.1f);

		// Render the player's health at the top right of the screen.
		Heart.render(sb, ScreenUtil.screen_width - Map.size * 3, ScreenUtil.screen_height - Map.size, this.life);
	}

	public void reset() {
		this.x = this.original_x;
		this.player_rectanglei.setX(this.original_x);
		this.number_of_milks_delivered = 0;
		this.life = MAX_LIFE;
	}

	public void debugRender(ShapeRenderer sr) {
		sr.begin(ShapeType.Line);
		sr.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		sr.rect(this.getRectanglei().getX(), this.getRectanglei().getY(), Map.size, Map.size);
		sr.end();
	}

	public void loseOneLife() {
		this.life--;
		if (this.life >= 0) {
			int r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
			if (r == 0) {
				AudioManager.SoundXv.PAIN_ONE.playSound();
			} else if (r == 1) {
				AudioManager.SoundXv.PAIN_TWO.playSound();
			} else if (r == 2) {
				AudioManager.SoundXv.PAIN_THREE.playSound();
			}
		}
		if (this.life < 0) {
			if (Dice.nextBoolean()) {
				AudioManager.SoundXv.DEATH_ONE.playSound();
			} else {
				AudioManager.SoundXv.DEATH_TWO.playSound();
			}
			this.play.lose();
		}
	}

	public byte getLife() {
		return this.life;
	}

	public void setLife(byte life) {
		this.life = life;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
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
		this.number_of_milks_delivered++;
	}

	public int getNumberOfMilksDelivered() {
		return this.number_of_milks_delivered;
	}

	public Rectanglei getRectanglei() {
		return this.player_rectanglei;
	}

	public boolean isScared() {
		return this.scared;
	}

	public void setScared(boolean scared) {
		this.scared = scared;
	}

	public static void render(SpriteBatch sprite_batch, int x, int y, int w, int h, short type) {
		switch (type) {
		case Player.READY_TO_SPRINT:
			sprite_batch.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(type), x, y, w, h);
			break;
		case SPRINTING:
			sprite_batch.draw(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrame(), x, y, w, h);
			break;
		default:
			break;
		}
	}

	public static void render(SpriteBatch sprite_batch, int x, int y, int w, int h, short type, int xr, int yr, int rotation) {
		switch (type) {
		case Player.READY_TO_SPRINT:
			// sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(type), (float) x, (float) y, (float) w, (float) h,
			// 1.0f, 1.0f, (float) rotation);
			break;
		case SPRINTING:
			// sprite_batch.draw(TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrame(), (float) x, (float) y, (float) w,
			// (float) h, 1.0f, 1.0f, (float) rotation);
			sprite_batch.draw(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrame(), x, y, xr, yr, w, h, 1.0f, 1.0f, rotation);
			break;
		default:
			break;
		}
	}

	public static void render(SpriteBatch sprite_batch, int x, int y, int w, int h, short type, boolean xFlip) {
		switch (type) {
		case Player.READY_TO_SPRINT:
			break;
		case SPRINTING:
			sprite_batch.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame((TextureManager.Animation_Spritesheet.WALKING.getCurrentFrameNumber()
					+ (31 * 6) + 26)), x, y, w, h);
			break;
		default:
			break;
		}
	}
}