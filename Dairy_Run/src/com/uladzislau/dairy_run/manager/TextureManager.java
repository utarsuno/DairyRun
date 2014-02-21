package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class TextureManager {

	public static enum TEXTURE {
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

		public void init() {
			if (this.texture == null) {
				this.texture = new Texture(Gdx.files.internal("data" + java.io.File.separator + "texture" + java.io.File.separator + name
						+ ".png"));
			} else {
				StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public Texture getTexture() {
			return this.texture;
		}

		public String getSource() {
			return source;
		}

		public void dispose() {
			if (this.texture != null) {
				this.texture.dispose();
			}
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

	}

	public static enum SPRITESHEET {
		PIXEL_SPRITESHEET("pixel_spritesheet", "http://opengameart.org/content/platformer-art-pixel-redux", 31, 31, 2, 2, 21, 21), BACKGROUNDS(
				"backgrounds_sh", "http://opengameart.org/content/platformer-art-pixel-redux", 3, 1, 0, 0, 231, 63);

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

		public void init() {
			if (this.texture == null) {
				this.texture = new Texture(Gdx.files.internal("data" + java.io.File.separator + "texture" + java.io.File.separator + name
						+ ".png"));
				// this.texture.setFilter(TextureFilter.MipMapNearestNearest,
				// TextureFilter.MipMapNearestNearest);
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
				initialized = true;
			} else {
				StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public TextureRegion getFrame(int frame) {
			return this.frames[frame];
		}

		public String getName() {
			return name;
		}

		public String getSource() {
			return source;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void render(SpriteBatch sb, int i, int j, int k) {
			sb.draw(getFrame(i), j, k, this.width, this.height);
		}

		public void dispose() {
			if (this.texture != null) {
				this.texture.dispose();
			}
		}

		public boolean isInitialized() {
			return initialized;
		}

	}

	public static enum ANIMATION_SPRITESHEET {
		PIXEL_WALKING("pixel_walking", "http://opengameart.org/content/platformer-art-pixel-redux", 90, new TextureRegion[] {
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(28), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(29) });

		private final String name;
		private final String source;
		private int width;
		private int height;
		private int cols;
		private int rows;
		private int subimage_pixel_width;
		private int subimage_pixel_height;
		private DeltaTimer deltaTimer;
		private int frame_time;
		private int current_frame;
		private int max_frames;
		private Texture texture;
		private TextureRegion[] frames;
		private boolean needToInit;
		private boolean initialized;

		ANIMATION_SPRITESHEET(String name, String source, int cols, int rows, int subimage_pixel_width, int subimage_pixel_height,
				int frame_time, int max_frames) {
			this.name = name;
			this.source = source;
			this.setDeltaTimer(new DeltaTimer());
			this.setCols(cols);
			this.setRows(rows);
			this.setSubimage_pixel_width(subimage_pixel_width);
			this.setSubimage_pixel_height(subimage_pixel_height);
			this.frame_time = frame_time;
			this.frames = new TextureRegion[this.rows * this.cols];
			this.max_frames = max_frames;
			this.needToInit = true;
			this.initialized = false;
		}

		ANIMATION_SPRITESHEET(String name, String source, int frame_time, TextureRegion frames[]) {
			this.name = name;
			this.source = source;
			this.setDeltaTimer(new DeltaTimer());
			this.frames = frames;
			this.setSubimage_pixel_width(frames[0].getRegionWidth());
			this.setSubimage_pixel_height(frames[0].getRegionHeight());
			this.frame_time = frame_time;
			this.max_frames = this.frames.length;
			this.needToInit = false;
			this.initialized = false;
		}

		public void init() {
			if (this.needToInit) {
				if (this.texture == null) {
					this.texture = new Texture(Gdx.files.internal("data" + java.io.File.separator + "texture" + java.io.File.separator
							+ name + ".png"));
					int x = 0;
					int y = 0;
					int r = 0;
					for (int i = 0; i < this.cols * this.rows; i++) {
						this.frames[i] = new TextureRegion(this.texture, x, y, this.subimage_pixel_width, this.subimage_pixel_height);
						r++;
						if (r == this.rows) {
							x = 0;
							y += this.subimage_pixel_height;
							r = 0;
						} else {
							x += this.subimage_pixel_width;
						}
					}
					initialized = true;
				} else {
					StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
				}
			}
		}

		public void update(float delta) {
			this.deltaTimer.update(delta);
			if (this.deltaTimer.isGreaterThanOrEqualTo(this.frame_time)) {
				this.deltaTimer.subtract(this.frame_time);
				this.current_frame++;
				if (this.current_frame >= max_frames) {
					this.current_frame = 0;
				}
			}
		}

		public Texture getTexture() {
			return this.texture;
		}

		public TextureRegion getCurrentFrame() {
			return this.frames[this.current_frame];
		}

		public String getSource() {
			return source;
		}

		public void dispose() {
			if (this.texture != null) {
				this.texture.dispose();
			}
		}

		public int getCols() {
			return cols;
		}

		public void setCols(int cols) {
			this.cols = cols;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public int getSubimage_pixel_width() {
			return subimage_pixel_width;
		}

		public void setSubimage_pixel_width(int subimage_pixel_width) {
			this.subimage_pixel_width = subimage_pixel_width;
		}

		public int getSubimage_pixel_height() {
			return subimage_pixel_height;
		}

		public void setSubimage_pixel_height(int subimage_pixel_height) {
			this.subimage_pixel_height = subimage_pixel_height;
		}

		public DeltaTimer getDeltaTimer() {
			return deltaTimer;
		}

		public void setDeltaTimer(DeltaTimer deltaTimer) {
			this.deltaTimer = deltaTimer;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setFrameTime(int delta_time) {
			this.frame_time = delta_time;
		}

		public boolean isInitialized() {
			return this.initialized;
		}

	}

}