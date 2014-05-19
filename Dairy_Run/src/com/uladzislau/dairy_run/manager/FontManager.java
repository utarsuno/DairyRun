package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.utility.StaticUtil;
import com.uladzislau.dairy_run.world.Map;

public class FontManager {

	// BMFont from http://www.angelcode.com/products/bmfont/ was used to generate the text .png and .fnt file.

	public enum Font implements Resource { // 1.0f, 1.0f);
		PIXEL_REGULAR("pixel_regular", "Kenny Donation Pack"); //$NON-NLS-1$ //$NON-NLS-2$

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
				this.font = new BitmapFont(Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + this.name + ".fnt"), Gdx.files.internal("data" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						+ java.io.File.separator + "font" + java.io.File.separator + this.name + ".png"), false); //$NON-NLS-1$ //$NON-NLS-2$

				this.setXScale(1.0f);
				this.setYScale(1.0f);

				float width_scale = ((float) Map.size) / ((float) this.getWidth("A")); //$NON-NLS-1$
				float height_scale = ((float) Map.size) / ((float) this.getHeight("A")); //$NON-NLS-1$
				this.setXScale(width_scale * this.x_scale);
				this.setYScale(height_scale * this.y_scale);

				this.initialized = true;
			} else {
				StaticUtil.error("Font Error", "You are trying to init " + this.name + " twice."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}

		public void render(String string, ColorXv colorXv, int x, int y, int h, boolean centerX, int x_position_limit) {
			if (x_position_limit == -1) {
				render(string, colorXv, (float) x, (float) y, (float) h, centerX, ScreenUtil.screen_width);
			} else {
				render(string, colorXv, (float) x, (float) y, (float) h, centerX, x_position_limit);
			}
		}

		public void render(String string, Color color, int x, int y, int h, boolean centerX, int x_position_limit) {
			if (x_position_limit == -1) {
				render(string, color, (float) x, (float) y, (float) h, centerX, ScreenUtil.screen_width);
			} else {
				render(string, color, (float) x, (float) y, (float) h, centerX, x_position_limit);
			}
		}

		public void render(String string, Color color, float x, float y, float h, boolean centerX, int x_position_limit) {
			this.font.setColor(color);
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = (h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);

			if (x_position_limit == -1) {
				x_position_limit = ScreenUtil.screen_width;
			}

			if (x_position_limit != -2) {
				if (this.getWidth(string) + x > x_position_limit) {
					float x_over_scale = x + this.getWidth(string);
					x_over_scale /= (x_position_limit);
					x_over_scale = 1.0f / x_over_scale;

					this.setXScale(x_over_scale * this.x_scale);
					this.setYScale(x_over_scale * this.y_scale);
				}
			}

			if (centerX) {
				this.font.draw(ResourceManager.getSpriteBatch(), string, x - this.getWidth(string) / 2, y + h);
			} else {
				this.font.draw(ResourceManager.getSpriteBatch(), string, x, y + h);
			}

			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
			this.font.setColor(Color.WHITE);
		}

		public void render(String string, ColorXv colorXv, float x, float y, float h, boolean centerX, int x_position_limit) {
			this.font.setColor(colorXv.getR(), colorXv.getG(), colorXv.getB(), colorXv.getA());
			float x_scale_temp = this.x_scale;
			float y_scale_temp = this.y_scale;
			float height_scale = (h / (this.getHeight(string)));
			this.setXScale(height_scale * this.x_scale);
			this.setYScale(height_scale * this.y_scale);

			if (x_position_limit == -1) {
				x_position_limit = ScreenUtil.screen_width;
			}

			if (x_position_limit != -2) {
				if (this.getWidth(string) + x > x_position_limit) {
					float x_over_scale = x + this.getWidth(string);
					x_over_scale /= (x_position_limit);
					x_over_scale = 1.0f / x_over_scale;

					this.setXScale(x_over_scale * this.x_scale);
					this.setYScale(x_over_scale * this.y_scale);
				}
			}

			if (centerX) {
				this.font.draw(ResourceManager.getSpriteBatch(), string, x - this.getWidth(string) / 2, y + h);
			} else {
				this.font.draw(ResourceManager.getSpriteBatch(), string, x, y + h);
			}

			this.setXScale(x_scale_temp);
			this.setYScale(y_scale_temp);
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
				StaticUtil.error("Font error: ", this.name + " has already been disposed."); //$NON-NLS-1$ //$NON-NLS-2$
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
			return "Initialized: " + this.initialized; //$NON-NLS-1$
		}

	}
}