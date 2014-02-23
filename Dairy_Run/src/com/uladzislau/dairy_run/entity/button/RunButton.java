package com.uladzislau.dairy_run.entity.button;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class RunButton extends Button {

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
		this.velocity_increase = 0.05f;
		this.player_position_increase = ScreenUtil.screen_width * 0.01f;
		for (int i = 0; i < this.track.length; i++) {
			track[i] = true;
		}
		this.deltaTimers = new ArrayList<DeltaTimer>();
		this.timer_transition = new ArrayList<Boolean>();
		this.pixels = new ArrayList<Float>();
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < InputManager.pointers.length; i++) {
			pointerCircles[i].setX(InputManager.pointers[i].x);
			pointerCircles[i].setY(InputManager.pointers[i].y);
		}
		for (int j = 0; j < InputManager.pointersDown.length; j++) {
			if (InputManager.pointersDown[j]) {
				if (this.isCollidingWithAnotherCirclef(pointerCircles[j])) {
					if (track[j]) {
						this.increments[j] = true;
						this.track[j] = false;
					}
				} else {
					this.increments[j] = false;
				}
			} else {
				this.track[j] = true;
			}
		}
		for (int i = 0; i < this.increments.length; i++) {
			if (this.increments[i]) {
				this.play.setVelocity(this.play.getVelocity() + this.velocity_increase);
				// TODO: Add a system that only adds a new delta timer when there is no more reusable, expired, deltaTimers.
				this.deltaTimers.add(new DeltaTimer());
				this.timer_transition.add(true);
				this.pixels.add(0f);
				this.increments[i] = false;
			}
		}
		for (int i = 0; i < this.deltaTimers.size(); i++) {
			this.deltaTimers.get(i).update(delta);

			if (this.timer_transition.get(i)) {
				this.pixels.set(i, this.player_position_increase * ((float) this.deltaTimers.get(i).getTotalDelta() / acceleration_time));
				if (this.deltaTimers.get(i).getTotalDelta() > acceleration_time) {
					this.deltaTimers.get(i).setTotalDelta(0);
					this.timer_transition.set(i, false);
				}
			} else {
				this.pixels.set(i, this.player_position_increase
						* (1.0f - ((float) this.deltaTimers.get(i).getTotalDelta() / deccelartion_time)));
				if (this.deltaTimers.get(i).getTotalDelta() > deccelartion_time) {
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
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 19), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
				Map.size, Map.size);
	}

	@Override
	public void debugRender(ShapeRenderer sr) {
		sr.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		sr.begin(ShapeType.Filled);
		sr.circle(this.getX(), this.getY(), this.getRadius());
		sr.end();
		sr.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		sr.begin(ShapeType.Filled);
		for (int i = 0; i < 8; i++) {
			sr.circle(pointerCircles[i].getX(), pointerCircles[i].getY(), pointerCircles[i].getRadius());
		}
		sr.end();
	}

	public float getVelocity_increase() {
		return velocity_increase;
	}

	public void setVelocity_increase(float velocity_increase) {
		this.velocity_increase = velocity_increase;
	}

	public float getPlayerPositionIncrease() {
		return player_position_increase;
	}

}
