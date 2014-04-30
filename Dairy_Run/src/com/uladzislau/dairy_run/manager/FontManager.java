package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.utility.StaticUtil;
import com.uladzislau.dairy_run.world.Map;

public class FontManager {

	// BMFont from http://www.angelcode.com/products/bmfont/ was used to generate the text .png and .fnt file.

	public enum Font implements FontResource { // 1.0f, 1.0f);
		PIXEL_REGULAR("pixel_regular", "Kenny Donation Pack");

		private final String name;
		private final String source;
		private BitmapFont font;
		private float x_scale;
		private float y_scale;
		private boolean initialized;

		Font(String name, String source) {
			this.name = name;
			this.source = source;
			this.setXScale(1.0f);
			this.setYScale(1.0f);
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.font == null) {
				this.font = new BitmapFont(Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + this.name + ".fnt"),
						Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + this.name + ".png"), false);

				this.setXScale(1.0f);
				this.setYScale(1.0f);

				float width_scale = ((float) Map.size) / ((float) this.getWidth("A"));
				float height_scale = ((float) Map.size) / ((float) this.getHeight("A"));
				this.setXScale(width_scale * this.x_scale);
				this.setYScale(height_scale * this.y_scale);

				this.initialized = true;
			} else {
				StaticUtil.error("Font Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public void render(SpriteBatch sb, String string, int x, int y) {
			this.font.draw(sb, string, x, y);
		}

		public void render(SpriteBatch sprite_batch, String string, Color color, float x1, float x2, float y1, float y2) {
			this.font.setColor(color);
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;

			float height_scale = ((y2 - y1) / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);

			this.font.draw(sprite_batch, string, x1, y1 + (y2 - y1));
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, Color color, float x1, float x2, float y1, float y2, int centerAtX) {
			this.font.setColor(color);
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;

			float width_scale = ((x2 - x1) / (this.getWidth(string)));
			float height_scale = ((y2 - y1) / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);

			this.font.draw(sprite_batch, string, centerAtX - this.getWidth(string) / 2, y1 + (y2 - y1));
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, Color color, int x, int y1, int h) {
			this.font.setColor(color);
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = ((float) h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);
			this.font.draw(sprite_batch, string, x - this.getWidth(string) / 2, y1 + h);
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, Color color, int x, int y, int h, boolean centered) {
			this.font.setColor(color);
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = ((float) h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);
			if (centered) {
				this.font.draw(sprite_batch, string, x - this.getWidth(string) / 2, y + h);
			} else {
				this.font.draw(sprite_batch, string, x, y + h);
			}
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, ColorXv colorXv, int x, int y, int h, boolean centerX) {
			this.font.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = ((float) h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);
			if (centerX) {
				this.font.draw(sprite_batch, string, x - this.getWidth(string) / 2, y + h);
			} else {
				this.font.draw(sprite_batch, string, x, y + h);
			}
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, ColorXv colorXv, int x, int y, int h, boolean centerX, int x_position_limit) {
			this.font.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = ((float) h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);

			if (this.getWidth(string) + x > x_position_limit) {
				float x_over_scale = (float) (x + this.getWidth(string));
				x_over_scale /= (float) (x_position_limit);
				x_over_scale = 1.0f / x_over_scale;

				this.setXScale(x_over_scale * this.x_scale);
				this.setYScale(x_over_scale * this.y_scale);
			}

			if (centerX) {
				this.font.draw(sprite_batch, string, x - this.getWidth(string) / 2, y + h);
			} else {
				this.font.draw(sprite_batch, string, x, y + h);
			}
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(SpriteBatch sprite_batch, String string, float x1, float x2, float y1, float y2) {
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;

			// float width_scale = ((x2 - x1) / (this.getWidth(string)));
			float height_scale = ((y2 - y1) / (this.getHeight(string)));
			// this.setXScale(width_scale * this.x_scale);
			this.setXScale(height_scale);
			this.setYScale(height_scale * this.y_scale);

			this.font.draw(sprite_batch, string, x1, y1);
			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
		}

		public void render(SpriteBatch sb, Color color, String string, int x, int y) {
			this.font.setColor(color);
			this.font.draw(sb, string, x, y);
			this.font.setColor(Color.WHITE);
		}

		public BitmapFont getTexture() {
			return this.font;
		}

		public int getWidth(String string) {
			return (int) this.font.getBounds(string).width;
		}

		public int getHeight(String string) {
			return (int) this.font.getBounds(string).height;
		}

		@Override
		public void dispose() {
			if (this.font != null) {
				this.initialized = false;
				this.font.dispose();
				this.font = null;
			} else {
				StaticUtil.error("Font error: ", this.name + " has already been disposed.");
			}
		}

		public float getXScale() {
			return this.x_scale;
		}

		public void setXScale(float x_scale) {
			if (this.font != null) {
				this.font.setScale(x_scale, this.y_scale);
			}
			this.x_scale = x_scale;
		}

		public float getYScale() {
			return this.y_scale;
		}

		public void setYScale(float y_scale) {
			if (this.font != null) {
				this.font.setScale(this.x_scale, y_scale);
			}
			this.y_scale = y_scale;
		}

		public boolean isInitialized() {
			return this.initialized;
		}

		public void setColor(float r, float g, float b, float a) {
			this.font.setColor(r, g, b, a);
		}

		public BitmapFont getFont() {
			return this.font;
		}

		@Override
		public String getSource() {
			return this.source;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String currentStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void resize() {
			this.setXScale(1.0f);
			this.setYScale(1.0f);

			float width_scale = ((float) Map.size) / ((float) this.getWidth("A"));
			float height_scale = ((float) Map.size) / ((float) this.getHeight("A"));
			this.setXScale(width_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);
		}

		public void setColor(ColorXv colorXv) {
			this.font.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
		}

	}

}