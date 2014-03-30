package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class ClickableText {

	public static final byte TEXT = 0;
	public static final byte BUTTON = 1;
	public static final byte ANIMATED_TEXT = 2;
	public static final byte ANIMATED_BUTTON = 2;

	private boolean collide = false;

	private byte type;
	private Rectanglei rectangle;
	private boolean first_title = true;
	private CharSequence title;
	private CharSequence title2;
	private ColorXv initialColorXv;
	private final ColorXv colorXv;
	private ColorXv highlightColorXv;
	private final DeltaTimer highlightTimer;

	private int chars_finished;
	private final DeltaTimer animatationTimer;
	private boolean animate = true;

	public ClickableText(CharSequence title, Rectanglei rectanglei, ColorXv initialColor, ColorXv highlightColor, int duration) {
		this.title = title;
		this.highlightTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, duration);
		this.initialColorXv = initialColor;
		this.highlightColorXv = highlightColor;
		this.colorXv = new ColorXv();
		this.colorXv.setTo(this.initialColorXv);
		this.rectangle = rectanglei;
		this.animatationTimer = new DeltaTimer();
	}

	public ClickableText(CharSequence title, CharSequence title2, ColorXv color1, ColorXv color2, int duration) {
		this.title = title;
		this.title2 = title2;
		this.highlightTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, duration);
		this.initialColorXv = new ColorXv(color1.getR(), color1.getG(), color1.getB(), color1.getA());
		this.highlightColorXv = new ColorXv(color2.getR(), color2.getG(), color2.getB(), color2.getA());
		this.colorXv = new ColorXv();
		this.colorXv.setTo(this.initialColorXv);
		this.animatationTimer = new DeltaTimer();
	}

	private boolean first = true;
	private boolean activated = false;
	private boolean sound_played = false;

	public void update(float delta) {

		switch (this.type) {
		case ANIMATED_TEXT:
			if (this.animate) {
				this.animatationTimer.update(delta);
				if (this.animatationTimer.isFinished()) {
					// AudioManager.SOUND.INTERFACE_SOUND_01.playSound();
					this.animatationTimer.subtract(this.animatationTimer.getDuration());
					this.chars_finished++;
					if (this.title.length() <= this.chars_finished) {
						this.animate = false;
					}
				}
			}
			break;
		default:
			break;
		}

		this.highlightTimer.update(delta);

		switch (InfoUtil.CURRENT_PLATEFORM) {
		case InfoUtil.DESKTOP:
			if (this.isMouseOverMe()) {
				this.collide = true;
			}
			break;
		case InfoUtil.ANDRIOD:
			if (this.isMouseDownOnMe()) {
				this.collide = true;
			}
			break;
		default:
			break;
		}

		if (this.collide) {
			this.activated = true;
			this.colorXv.setTo(this.highlightColorXv);
			if (!this.sound_played) {
				switch (this.type) {
				case ClickableText.TEXT:
					AudioManager.SOUND.INTERFACE_01.playSound();
					this.sound_played = true;
					break;
				case ClickableText.BUTTON:
					AudioManager.SOUND.INTERFACE_01.playSound();
					this.sound_played = true;
					break;
				default:
					break;
				}
			}
			this.collide = false;
		} else {
			this.sound_played = false;
			if (!this.first) {
				this.highlightTimer.reset();
				this.first = false;
			}
			if (this.activated) {
				this.highlightTimer.reset();
				this.activated = false;
			}
			this.colorXv.setColorToFrom(this.initialColorXv, this.highlightColorXv, this.highlightTimer.percentComplete());
		}
	}

	public void render(SpriteBatch sprite_batch, BitmapFont font) {
		switch (this.type) {
		case ANIMATED_TEXT:
			font.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA());
			CharSequence charSequence = this.title.subSequence(0, this.chars_finished);
			font.drawMultiLine(sprite_batch, charSequence, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
			break;
		default:
			font.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA());
			if (this.first_title) {
				font.draw(sprite_batch, this.title, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
			} else {
				font.draw(sprite_batch, this.title2, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
			}
			break;
		}
	}

	public void render(SpriteBatch sprite_batch, BitmapFont font, boolean centeredX) {
		switch (this.type) {
		case ANIMATED_TEXT:
			font.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA());
			CharSequence charSequence = this.title.subSequence(0, this.chars_finished);
			font.drawMultiLine(sprite_batch, charSequence, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
			break;
		default:
			font.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA());
			if (centeredX) {
				if (this.first_title) {
					font.draw(sprite_batch, this.title, getRectanglei().getX() - getRectanglei().getWidth() / 2, getRectanglei().getY()
							+ getRectanglei().getHeight());
				} else {
					font.draw(sprite_batch, this.title2, getRectanglei().getX() - getRectanglei().getWidth() / 2, getRectanglei().getY()
							+ getRectanglei().getHeight());
				}
			} else {
				if (this.first_title) {
					font.draw(sprite_batch, this.title, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
				} else {
					font.draw(sprite_batch, this.title2, getRectanglei().getX(), getRectanglei().getY() + getRectanglei().getHeight());
				}
			}

			break;
		}
	}

	public void setType(byte type) {
		this.type = type;
	}

	public CharSequence getTitle() {
		return this.title;
	}

	public Rectanglei getRectanglei() {
		return this.rectangle;
	}

	public void setRectangle(Rectanglei rectangle) {
		this.rectangle = rectangle;
	}

	public boolean isFirstTitle() {
		return this.first_title;
	}

	public void setFirstTitle(boolean first_title) {
		this.first_title = first_title;
	}

	public void inverseTitle() {
		this.first_title ^= true;
	}

	public void setColor(float r, float g, float b, float a) {
		this.colorXv.setR(r);
		this.colorXv.setG(g);
		this.colorXv.setB(b);
		this.colorXv.setA(a);
	}

	public void setTitle(String string) {
		this.title = string;
	}

	public ColorXv getHighlightColorXv() {
		return this.highlightColorXv;
	}

	public void setHighlightColorXv(ColorXv highlightColorXv) {
		this.highlightColorXv = highlightColorXv;
	}

	public ColorXv getInitialColorXv() {
		return this.initialColorXv;
	}

	public void setInitialColorXv(ColorXv initialColorXv) {
		this.initialColorXv = initialColorXv;
	}

	public boolean isMouseDownOnMe() {
		if (InputManager.pointersDown[0]) {
			return this.rectangle.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y);
		}
		return false;
	}

	public boolean isMouseOverMe() {
		return this.rectangle.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y);
	}

	public void setDelay(int delay) {
		this.animatationTimer.setDuration(delay);
	}

	public void collide() {
		this.collide = true;
	}

	public void reset() {
		this.highlightTimer.reset();
		this.colorXv.setColorToFrom(this.initialColorXv, this.highlightColorXv, this.highlightTimer.percentComplete());
	}

	@Override
	public String toString() {
		return "x: " + getRectanglei().getX() + "\ty: " + getRectanglei().getY() + "\tw: " + getRectanglei().getWidth() + "\th: " + getRectanglei().getHeight();
	}

	public void render(SpriteBatch sprite_batch, boolean centerX) {

		FontManager.FONT.PIXEL_REGULAR.render(sprite_batch, (String) this.title, this.colorXv, this.rectangle.getX(), this.rectangle.getY(),
				this.rectangle.getHeight(), centerX);

		// if (centerX) {
		// FontManager.FONT.PIXEL_REGULAR.render(sprite_batch, (String) this.title, new Color(this.colorXv.getR(), this.colorXv.getG(),
		// this.colorXv.getB(),
		// this.colorXv.getA()), getRectanglei().getX() - this.getRectanglei().getWidth() / 2, getRectanglei().getX() +
		// getRectanglei().getWidth(),
		// getRectanglei().getY(), getRectanglei().getY() + getRectanglei().getHeight());
		// } else {
		// FontManager.FONT.PIXEL_REGULAR.render(sprite_batch, (String) this.title, new Color(this.colorXv.getR(), this.colorXv.getG(),
		// this.colorXv.getB(),
		// this.colorXv.getA()), getRectanglei().getX(), getRectanglei().getX() + getRectanglei().getWidth(), getRectanglei().getY(),
		// getRectanglei()
		// .getY() + getRectanglei().getHeight());
		// }

	}

	public void finish() {
		this.highlightTimer.reset();
		this.collide = false;
		this.sound_played = false;
		if (!this.first) {
			this.highlightTimer.reset();
			this.first = false;
		}
		if (this.activated) {
			this.highlightTimer.reset();
			this.activated = false;
		}
		this.colorXv.setColorToFrom(this.initialColorXv, this.highlightColorXv, 0.0f);
	}

}