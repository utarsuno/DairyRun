package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class FontManager {

	// BMFont from http://www.angelcode.com/products/bmfont/ was used to generate the .fnt file.

	public enum FONT implements Resource {
		BLOCK_FONT("block_font", "Kenny Donation Pack", 1.0f, 1.0f);

		private final String name;
		private final String source;
		private BitmapFont font;
		private float x_scale;
		private float y_scale;
		private boolean initialized;

		FONT(String name, String source, float x_scale, float y_scale) {
			this.name = name;
			this.source = source;
			this.setXScale(x_scale);
			this.setYScale(y_scale);
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.font == null) {
				this.font = new BitmapFont(Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + this.name
						+ ".fnt"), Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + this.name
						+ ".png"), false);
				while (Math.abs(this.getWidth("A") - Map.size) > 0.1f) {
					if (this.getWidth("Hello World") - Map.size < 0.1f) {
						this.setXScale(this.getXScale() - 0.01f);
					} else {
						this.setXScale(this.getXScale() + 0.01f);
					}
				}
				while (Math.abs(this.getHeight("Hello World") - Map.size) > 0.1f) {
					if (this.getHeight("Hello World") - Map.size < 0.1f) {
						this.setYScale(this.getYScale() + 0.01f);
					} else {
						this.setYScale(this.getYScale() - 0.01f);
					}
				}
				this.font.setScale(this.x_scale, this.y_scale);
				this.initialized = true;
			} else {
				StaticUtil.error("Font Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public void render(SpriteBatch sb, String string, int x, int y) {
			this.font.draw(sb, string, x, y);
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

	}

}