package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.P;

public class Chaser {

	private int x;

	private short milk_wanted[];
	private float velocity;

	private DeltaTimer delayTimer;
	private DeltaTimer accelerationTimer;
	private DeltaTimer yellTimer;

	private boolean alive;
	private boolean killable;

	private int character;

	private boolean yelling = false;

	private Play play;

	private boolean removable;

	// TODO: Create a better system for this.
	public static int number_of_chasers_created = 0;

	// TODO: Possible have a static rectangle method (in geometry class) which takes dimensions and determines if they are colliding
	private Rectanglei rectanglei;

	public Chaser(short[] milks_not_delievered, float current_scroll, float v, int acceleration_time, Play play) {
		number_of_chasers_created++;
		this.delayTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, Dice.get_Random_Integer_From_Min_To_Max(250, 2000));
		this.accelerationTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, acceleration_time);
		this.yellTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 2000);
		this.setMilkWanted(milks_not_delievered);
		this.setVelocity((float) (v + (v * number_of_chasers_created * 0.01)));
		this.play = play;
		this.x = (-Map.size);
		this.removable = false;
		this.alive = true;
		this.killable = false;
		this.rectanglei = new Rectanglei(x, this.play.ground_level, Map.size, Map.size);
		randomize();
	}

	private void randomize() {
		this.character = (31 * Dice.get_Random_Integer_From_Min_To_Max(1, 4));
	}

	public void update(float delta) {
		if (!this.delayTimer.isFinished()) {
			this.delayTimer.update(delta);
		} else {
			this.x += this.velocity - this.play.getVelocity();
			if (this.x + Map.size > 0) {
				this.killable = true;
			}
			this.yellTimer.update(delta);
			if (this.yellTimer.isFinished()) {
				this.yelling ^= true;

				// int random = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
				// if (random == 0) {
				// AudioManager.SOUND.MILK.playSound();
				// } else if (random == 1) {
				// AudioManager.SOUND.MILK2.playSound();
				// } else if (random == 2) {
				// AudioManager.SOUND.MILK3.playSound();
				// }
				this.yellTimer.reset();
			}
			if (this.killable) {
				if (this.x + Map.size < 0) {
					this.removable = true;
				}
			}
			this.rectanglei.setX(this.getX());
			if (this.x + Map.size > this.play.getPlayer().getX() && this.x + Map.size < this.play.getPlayer().getX() + Map.size) {
				this.play.getPlayer().loseOneLife();
				// TODO: add action / text / animation here
				this.removable = true;
			}
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.yelling) {
			FontManager.FONT.PIXEL_REGULAR.render(sb, "MILK!", Color.RED, this.x - Map.size / 4, this.x + Map.size / 4 + Map.size,
					this.play.ground_level + Map.size * 1.0f, this.play.ground_level + Map.size * 1.5f);
		}
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.character + 28
				+ TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrameNumber()), this.x, this.play.ground_level, Map.size,
				Map.size);
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public short[] getMilk_wanted() {
		return this.milk_wanted;
	}

	public void setMilkWanted(short milk_wanted[]) {
		this.milk_wanted = milk_wanted;
	}

	public float getVelocity() {
		return this.velocity;
	}

	public void setVelocity(float v) {
		this.velocity = v;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isRemovable() {
		return this.removable;
	}

}
