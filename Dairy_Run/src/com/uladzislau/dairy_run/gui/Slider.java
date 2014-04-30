package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.MathUtil;

public class Slider {

	private Rectanglei rectanglei;
	private float limit;
	private boolean slidable;
	private final ColorXv colorXv;
	private final ColorXv minColorXv;
	private final ColorXv maxColorXv;

	private int target_position;
	private float current_position;
	private float old_velocity;
	private float velocity;
	private final float acceleration = 0.01f;

	public Slider(int x, int y, int width, int height, ColorXv minColorXv, ColorXv maxColorXv) {
		this.setRectangle(new Rectanglei(x, y, width, height));
		this.colorXv = new ColorXv();
		this.minColorXv = minColorXv;
		this.maxColorXv = maxColorXv;
		this.limit = 1.0f;
		this.current_position = this.rectanglei.getX() + this.rectanglei.getWidth();
		this.target_position = (int) this.current_position;
		this.slidable = true;
	}

	private boolean dragging = true;

	public void update(int delta) {

		if (this.slidable) {
			if (InputManager.pointersDown[0]) {
				if (this.rectanglei.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
					this.target_position = InputManager.pointers[0].x;
					this.dragging = true;
				} else {
					if (this.dragging) {
						if (InputManager.pointers[0].x < this.rectanglei.getX()) {
							this.target_position = this.rectanglei.getX();
						} else if (InputManager.pointers[0].x > this.rectanglei.getX() + this.rectanglei.getWidth()) {
							this.target_position = this.rectanglei.getX() + this.rectanglei.getWidth();
						} else {
							this.target_position = InputManager.pointers[0].x;
						}
					}
				}
			} else {
				this.dragging = false;
			}
		}

		if (percentageFull() > this.limit) {
			this.current_position = this.rectanglei.getX() + (int) (+this.rectanglei.getWidth() * this.limit);
			this.old_velocity = 0;
			this.velocity = 0;
			this.target_position = (int) this.current_position;
		}

		if ((this.current_position != this.target_position)) {
			this.old_velocity = this.velocity;

			if (this.current_position > this.target_position) {
				this.velocity += -this.acceleration * delta;
				if ((this.current_position + ((this.old_velocity + this.velocity) / 2.0f) * delta) < this.target_position) {
					this.old_velocity = 0;
					this.velocity = 0;
					this.current_position = this.target_position;
				} else {
					this.current_position += ((this.old_velocity + this.velocity) / 2.0f) * delta;
				}
			} else {
				this.velocity += this.acceleration * delta;
				if ((this.current_position + ((this.old_velocity + this.velocity) / 2.0f) * delta) > this.target_position) {
					this.old_velocity = 0;
					this.velocity = 0;
					this.current_position = this.target_position;
				} else {
					this.current_position += ((this.old_velocity + this.velocity) / 2.0f) * delta;
				}
			}

		}

		this.colorXv.setColorToFrom(this.minColorXv, this.maxColorXv, percentageFull());

	}

	public void render(SpriteBatch sb) {

		sb.setColor(1.0f, 1.0f, 1.0f, 0.15f);
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.BLACK), this.rectanglei.getX(), this.rectanglei.getY(), this.rectanglei.getWidth(),
				this.rectanglei.getHeight());

		if (!this.slidable) {
			sb.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA() / 2);
		} else {
			sb.setColor(this.colorXv.getR(), this.colorXv.getG(), this.colorXv.getB(), this.colorXv.getA());
		}

		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.WHITE), this.rectanglei.getX(), this.rectanglei.getY(), this.rectanglei.getWidth()
				* this.percentageFull(), this.rectanglei.getHeight());

		sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public boolean isBeingDragging() {
		return this.dragging;
	}

	public void setLimit(float limit) {
		this.limit = limit;
	}

	public Rectanglei getRectangle() {
		return this.rectanglei;
	}

	public void setRectangle(Rectanglei rectangle) {
		this.rectanglei = rectangle;
	}

	public float percentageFull() {
		if (1.0f - MathUtil.round((this.current_position - this.rectanglei.getX()) / (this.rectanglei.getWidth()), 2) < 0.009) {
			return 1.0f;
		} else if (MathUtil.round((this.current_position - this.rectanglei.getX()) / (this.rectanglei.getWidth()), 2) < 0.009) {
			return 0.0f;
		}
		return (float) MathUtil.round((this.current_position - this.rectanglei.getX()) / (this.rectanglei.getWidth()), 2);
	}

	public void setSlidable(boolean slidable) {
		this.slidable = slidable;
	}

	public ColorXv getColorXv() {
		return this.colorXv;
	}

	public void setPosition(int position) {
		this.current_position = position;
	}

	public int getPosition() {
		return (int) this.current_position;
	}

	public void setTargetPosition(int target_position) {
		this.target_position = target_position;
	}

	public int getTargetPosition() {
		return this.target_position;
	}
}