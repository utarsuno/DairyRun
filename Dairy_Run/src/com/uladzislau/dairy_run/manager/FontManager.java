package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class FontManager {

	// BMFont from http://www.angelcode.com/products/bmfont/ was used to generate the .fnt file.

	public static enum FONT {
		DEFAULT_FONT("default_font", 1.0f, 1.0f), TEST_FONT("font");

		private final String name;
		private BitmapFont font;
		private float x_scale;
		private float y_scale;
		private boolean initialized;

		FONT(String name, float x_scale, float y_scale) {
			this.name = name;
			this.setXScale(x_scale);
			this.setYScale(y_scale);
			initialized = false;
		}

		TextureAtlas spriteSheet;
		private Array<Sprite> characters;

		public Sprite get(int c) {
			return this.characters.get(c);
		}

		FONT(String name) {
			this.name = name;
			spriteSheet = new TextureAtlas(Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + name
					+ ".txt"));
			characters = spriteSheet.createSprites();
			for (int i = 0; i < characters.size; i++) {
				characters.get(i).setSize(300.0f, 300.0f);
				characters.get(i).setX(100);
			}
			initialized = true;
		}

		public void testRender() {

		}

		public void init() {
			if (this.font == null) {
				this.font = new BitmapFont(Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + name
						+ ".fnt"), Gdx.files.internal("data" + java.io.File.separator + "font" + java.io.File.separator + name + ".png"),
						false);
				this.initialized = true;
			} else {
				StaticUtil.error("Font Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public void render(SpriteBatch sb, String string, int x, int y) {
			this.font.draw(sb, string, x, y);
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

		public void dispose() {
			if (this.font != null) {
				this.font.dispose();
			}
		}

		public float getXScale() {
			return x_scale;
		}

		public void setXScale(float x_scale) {
			if (this.font != null) {
				this.font.setScale(x_scale, y_scale);
			}
			this.x_scale = x_scale;
		}

		public float getYScale() {
			return y_scale;
		}

		public void setYScale(float y_scale) {
			if (this.font != null) {

				this.font.setScale(x_scale, y_scale);
			}
			this.y_scale = y_scale;
		}

		public boolean isInitialized() {
			return initialized;
		}

		public void setColor(float r, float g, float b, float a) {
			this.font.setColor(r, g, b, a);
		}

	}

}