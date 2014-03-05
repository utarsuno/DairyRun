package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class Chaser {

	private int x;

	private short milk_wanted[];
	private float velocity;

	private DeltaTimer delayTimer;
	private DeltaTimer accelerationTimer;
	private DeltaTimer yellTimer;
	
	private boolean yelling = false;

	private Play play;

	public Chaser(short[] milks_not_delievered, float current_scroll, float v, int acceleration_time, Play play) {
		this.delayTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, Dice.get_Random_Integer_From_Min_To_Max(100, 2000));
		this.accelerationTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, acceleration_time);
		this.yellTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 2000);
		this.setMilkWanted(milks_not_delievered);
		this.setVelocity(v);
		this.play = play;
		this.x = (int) (-Map.size);
		randomize();
	}

	private void randomize() {

	}

	public void update(float delta) {
		if (!this.delayTimer.isFinished()) {
			this.delayTimer.update(delta);
		} else {
			this.x += this.velocity - this.play.getVelocity();
			this.yellTimer.update(delta);
			if (this.yellTimer.isFinished()) {
				this.yelling ^= true;
				this.yellTimer.reset();
			}
		}
	}

	public void render(SpriteBatch sb, int current_scroll) {
		if (this.yelling) {
			//TODO: Use a different font.
			FontManager.FONT.BLOCK_FONT.render(sb, Color.RED, "MILK!", this.x - 50, this.play.ground_level + Map.size * 2);
		}
		sb.draw(TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrame(), this.x, this.play.ground_level, Map.size, Map.size);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public short[] getMilk_wanted() {
		return milk_wanted;
	}

	public void setMilkWanted(short milk_wanted[]) {
		this.milk_wanted = milk_wanted;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float v) {
		this.velocity = v;
	}
}
