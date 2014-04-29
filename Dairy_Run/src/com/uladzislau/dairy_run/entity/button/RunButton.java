package com.uladzislau.dairy_run.entity.button;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class RunButton extends CircleButton {

	private float velocity_increase;
	private float player_position_increase;
	private float acceleration_time = 1000;
	private float deccelartion_time = 8000;

	private ArrayList<DeltaTimer> deltaTimers;
	private ArrayList<Boolean> timer_transition;
	private ArrayList<Float> pixels;

	private Play play;

	public RunButton(float x, float y, float radius, Play play) {
		super(x, y, radius);
		this.play = play;
		this.velocity_increase = 0.50f;
		this.player_position_increase = ScreenUtil.screen_width * 0.02f;
		this.deltaTimers = new ArrayList<DeltaTimer>();
		this.timer_transition = new ArrayList<Boolean>();
		this.pixels = new ArrayList<Float>();
		super.inititialize();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		for (int i = 0; i < this.deltaTimers.size(); i++) {
			this.deltaTimers.get(i).update(delta);
			if (this.timer_transition.get(i)) {
				this.pixels.set(i, this.player_position_increase * (this.deltaTimers.get(i).getTotalDelta() / this.acceleration_time));
				if (this.deltaTimers.get(i).getTotalDelta() > this.acceleration_time) {
					this.deltaTimers.get(i).setTotalDelta(0);
					this.timer_transition.set(i, false);
				}
			} else {
				this.pixels.set(i, this.player_position_increase * (1.0f - (this.deltaTimers.get(i).getTotalDelta() / this.deccelartion_time)));
				if (this.deltaTimers.get(i).getTotalDelta() > this.deccelartion_time) {
					this.deltaTimers.remove(i);
					this.timer_transition.remove(i);
					this.pixels.remove(i);
				}
			}
		}
		this.play.getPlayer().setXToOriginalX();
		for (Float p : this.pixels) {
			this.play.getPlayer().setX((int) (this.play.getPlayer().getX() + p));
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 19), this.getX() - Map.size / 2, this.getY() - Map.size / 2, Map.size, Map.size);
	}

	public void reset() {
		this.deltaTimers.clear();
		this.timer_transition.clear();
		this.pixels.clear();
	}

	public float getVelocity_increase() {
		return this.velocity_increase;
	}

	public void setVelocity_increase(float velocity_increase) {
		this.velocity_increase = velocity_increase;
	}

	public float getPlayerPositionIncrease() {
		return this.player_position_increase;
	}

	@Override
	public void doButtonAction() {
		// TODO: Add a system that only adds a new delta timer when there is no more reusable, expired, deltaTimers.
		this.deltaTimers.add(new DeltaTimer());
		this.timer_transition.add(true);
		this.pixels.add(0f);
		this.play.setVelocity(this.play.getVelocity() + 0.1f);
		AudioManager.SoundXv.POP.playSound();
	}

}
