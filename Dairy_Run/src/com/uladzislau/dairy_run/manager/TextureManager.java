package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class TextureManager {

	public static enum TEXTURE {
		GRASS_BLOCK_SQUARE("grass_block_square", "alfonso has it"), GRASS_BLOCK_ROUNDED("grass_block_rounded", "alfonso has it"), COLOR_208_244_247(
				"color_208_244_247", "self-created");

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
				this.texture = new Texture(Gdx.files.internal("data/texture/" + name + ".png"));
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
				this.texture = new Texture(Gdx.files.internal("data/texture/" + name + ".png"));
				// this.texture.setFilter(TextureFilter.MipMapNearestNearest,
				// TextureFilter.MipMapNearestNearest);
			} else {
				StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
			}
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

	}

	public static enum ANIMATION_SPRITESHEET {
		PLAYER_WALKING("player_walking_sh", "alfonso has it", 4, 3, 67, 94, 80, (4 * 3) - 1), PIXEL_WALKING("pixel_walking_sh",
				"http://opengameart.org/content/platformer-art-pixel-redux", 1, 2, 16, 21, 80, 2);

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
		}

		public void init() {
			if (this.texture == null) {
				this.texture = new Texture(Gdx.files.internal("data/texture/" + name + ".png"));
			} else {
				StaticUtil.error("Texture Error", "You are trying to init " + this.name + " twice.");
			}
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

	}

}