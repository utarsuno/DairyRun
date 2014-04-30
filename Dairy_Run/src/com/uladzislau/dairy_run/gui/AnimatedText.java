package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Entity;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class AnimatedText extends Entity {

	public enum AnimatedTextType {
		FADE_UP
	}

	private DeltaTimer deltaTimer;
	private String text;
	private Color starting_color;
	private Color ending_color;
	private Color current_color;
	private float ending_y;
	private float starting_y;
	AnimatedTextType type;

	public AnimatedText(float x, float y, float ending_y, int width, int height, String text, Color starting_color, Color ending_color, int fade_time,
			AnimatedTextType type, SpriteBatch sprite_batch) {
		super(x, y, width, height, sprite_batch);
		this.text = text;
		this.setStartingColor(starting_color);
		this.setEndingColor(ending_color);
		this.current_color = new Color();
		this.setStartingY(y);
		this.setEndingY(ending_y);
		this.type = type;
		this.deltaTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, fade_time);
	}

	@Override
	public void update(float delta) {
		this.deltaTimer.update(delta);
		this.current_color.r = ColorXv.getRComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.g = ColorXv.getGComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.b = ColorXv.getBComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.a = ColorXv.getAComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		switch (this.type) {
		case FADE_UP:
			this.setY((this.ending_y - this.starting_y) * this.deltaTimer.percentComplete() + this.starting_y);
			break;
		default:
			break;
		}
	}

	@Override
	public void render() {
		FontManager.Font.PIXEL_REGULAR.render(this.getSpriteBatch(), this.text, this.current_color, (int) this.getX(), (int) this.getY(), this.getHeight());
	}

	public void play() {
		this.deltaTimer.reset();
		this.current_color.r = ColorXv.getRComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.g = ColorXv.getGComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.b = ColorXv.getBComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.current_color.a = ColorXv.getAComponentToFrom(this.ending_color, this.starting_color, this.deltaTimer.percentComplete());
		this.setY(this.starting_y);
	}
	
	public void end() {
		this.deltaTimer.end();
	}

	public DeltaTimer getDeltaTimer() {
		return this.deltaTimer;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getEndingY() {
		return this.ending_y;
	}

	public void setEndingY(float ending_y) {
		this.ending_y = ending_y;
	}

	public float getStartingY() {
		return this.starting_y;
	}

	public void setStartingY(float starting_y) {
		this.starting_y = starting_y;
	}

	public Color getStartingColor() {
		return this.starting_color;
	}

	public void setStartingColor(Color starting_color) {
		this.starting_color = starting_color;
	}

	public Color getEndingColor() {
		return this.ending_color;
	}

	public void setEndingColor(Color ending_color) {
		this.ending_color = ending_color;
	}

}
