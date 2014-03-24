package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class TextureManager {

	public enum TEXTURE implements Resource {
		NONE("none", "none");

		private final String name;
		private final String source;
		private int width;
		private int height;
		private Texture texture;

		TEXTURE(String name, String source) {
			this.name = name;
			this.source = source;
		}

		@Override
		public void initialize() {
			if (this.texture == null) {
				this.texture = new Texture(Gdx.files.internal("data" + java.io.File.separator + "texture" + java.io.File.separator
						+ this.name + ".png"));
			} else {
				StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public Texture getTexture() {
			return this.texture;
		}

		@Override
		public String getSource() {
			return this.source;
		}

		@Override
		public void dispose() {
			if (this.texture != null) {
				this.texture.dispose();
			}
		}

		public int getWidth() {
			return this.width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return this.height;
		}

		public void setHeight(int height) {
			this.height = height;
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

	public static enum SPRITESHEET implements TextureResource {
		PIXEL_SPRITESHEET("pixel_spritesheet", "http://opengameart.org/content/platformer-art-pixel-redux", 31, 31, 2, 2, 21, 21), BACKGROUNDS(
				"Backgrounds", "http://opengameart.org/content/platformer-art-pixel-redux", PIXEL_SPRITESHEET, 793, 835, 3, 1, 0, 0, 231,
				63);

		private final String name;
		private final String source;
		private int width;
		private int height;
		private int x_padding;
		private int y_padding;
		private int cols;
		private int rows;
		private int subimage_pixel_width;
		private int subimage_pixel_height;
		private Texture texture;
		private TextureRegion[] frames;
		private int start_pixel_x = -1;
		private int start_pixel_y;
		private SPRITESHEET spritesheet;
		private boolean initialized;

		SPRITESHEET(String name, String source, int cols, int rows, int x_padding, int y_padding, int subimage_pixel_width,
				int subimage_pixel_height) {
			this.name = name;
			this.source = source;
			this.cols = cols;
			this.rows = rows;
			this.x_padding = x_padding;
			this.y_padding = y_padding;
			this.subimage_pixel_width = subimage_pixel_width;
			this.subimage_pixel_height = subimage_pixel_height;
			this.frames = new TextureRegion[this.rows * this.cols];
		}

		SPRITESHEET(String name, String source, SPRITESHEET spritesheet, int start_pixel_x, int start_pixel_y, int cols, int rows,
				int x_padding, int y_padding, int subimage_pixel_width, int subimage_pixel_height) {
			this.name = name;
			this.source = source;
			this.spritesheet = spritesheet;
			this.start_pixel_x = start_pixel_x;
			this.start_pixel_y = start_pixel_y;
			this.cols = cols;
			this.rows = rows;
			this.x_padding = x_padding;
			this.y_padding = y_padding;
			this.subimage_pixel_width = subimage_pixel_width;
			this.subimage_pixel_height = subimage_pixel_height;
			this.frames = new TextureRegion[this.rows * this.cols];
		}

		@Override
		public void initialize() {
			if (this.start_pixel_x == -1) {
				if (this.texture == null) {
					this.texture = new Texture(Gdx.files.internal("data" + java.io.File.separator + "texture" + java.io.File.separator
							+ this.name + ".png"));
					this.texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
					int x = 0;
					int y = 0;
					int r = 0;
					for (int i = 0; i < this.cols * this.rows; i++) {
						this.frames[i] = new TextureRegion(this.texture, x + this.x_padding, y + this.y_padding, this.subimage_pixel_width,
								this.subimage_pixel_height);
						r++;
						if (r == this.rows) {
							x = 0;
							y += this.subimage_pixel_height + this.y_padding;
							r = 0;
						} else {
							x += this.subimage_pixel_width + this.x_padding;
						}
					}
					this.initialized = true;
				} else {
					StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
				}
			} else {
				int x = 0;
				int y = 0;
				int r = 0;
				for (int i = 0; i < this.cols * this.rows; i++) {
					this.frames[i] = new TextureRegion(this.spritesheet.getTexture(), this.start_pixel_x + x + this.x_padding,
							this.start_pixel_y + y + this.y_padding, this.subimage_pixel_width, this.subimage_pixel_height);
					r++;
					if (r == this.rows) {
						x = 0;
						y += this.subimage_pixel_height + this.y_padding;
						r = 0;
					} else {
						x += this.subimage_pixel_width + this.x_padding;
					}
				}
				this.initialized = true;
			}
		}

		public TextureRegion getFrame(int frame) {
			return this.frames[frame];
		}

		public Texture getTexture() {
			return this.texture;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getSource() {
			return this.source;
		}

		public int getWidth() {
			return this.width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return this.height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void render(SpriteBatch sb, int i, int x, int y) {
			sb.draw(getFrame(i), x, y, this.width, this.height);
		}

		@Override
		public void dispose() {
			if (this.texture != null) {
				this.initialized = false;
				this.texture.dispose();
				this.texture = null;
			} else {
				StaticUtil.error("Spritesheet Error: ", this.name + " has already been disposed.");
			}
		}

		public boolean isInitialized() {
			return this.initialized;
		}

		@Override
		public String currentStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void resize() {
			
		}

	}

	public enum ANIMATION_SPRITESHEET implements Resource {
		PIXEL_WALKING("pixel_walking", "http://opengameart.org/content/platformer-art-pixel-redux", 90, new int[] { 28, 29 }), SAD_PIXEL_WALKING(
				"pixel_walking", "http://opengameart.org/content/platformer-art-pixel-redux", 90, new int[] { 31 * 6 + 28, 31 * 6 + 29 });

		private String name;
		private String source;
		private DeltaTimer deltaTimer;
		private int frames[];
		private int frame_time;
		private int current_frame;
		private boolean initialized;

		ANIMATION_SPRITESHEET(String name, String source, int frame_time, int frames[]) {
			this.setName(name);
			this.source = source;
			this.setDeltaTimer(new DeltaTimer());
			this.frames = frames;
			this.frame_time = frame_time;
			this.initialized = false;
		}

		@Override
		public void initialize() {
			this.initialized = true;
		}

		public void update(float delta) {
			this.deltaTimer.update(delta);
			if (this.deltaTimer.isGreaterThanOrEqualTo(this.frame_time)) {
				this.deltaTimer.subtract(this.frame_time);
				this.current_frame++;
				if (this.current_frame >= this.frames.length) {
					this.current_frame = 0;
				}
			}
		}

		public TextureRegion getCurrentFrame() {
			return TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.frames[this.current_frame]);
		}

		@Override
		public String getSource() {
			return this.source;
		}

		@Override
		public void dispose() {
			this.initialized = false;
		}

		public DeltaTimer getDeltaTimer() {
			return this.deltaTimer;
		}

		public void setDeltaTimer(DeltaTimer deltaTimer) {
			this.deltaTimer = deltaTimer;
		}

		public void setFrameTime(int delta_time) {
			this.frame_time = delta_time;
		}

		public boolean isInitialized() {
			return this.initialized;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String currentStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getCurrentFrameNumber() {
			return this.current_frame;
		}

	}

}