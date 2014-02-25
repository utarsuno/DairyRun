package com.uladzislau.dairy_run.manager;

import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.information.ScreenUtil;

public class ResourceManager {

	public boolean texture_initialized;
	public boolean music_initialized;
	public boolean sound_initialized;

	public void initialize_all_resources() {
		texture_initialized = false;
		music_initialized = false;
		sound_initialized = false;
		music_initializer.start();
		sound_initializer.start();
		// Textures are created here because OpenGL context may only exist on the main thread.
		for (TextureManager.SPRITESHEET spritesheet : TextureManager.SPRITESHEET.values()) {
			spritesheet.init();
		}
		TextureManager.SPRITESHEET.BACKGROUNDS.setHeight((int) (ScreenUtil.screen_height));
		TextureManager.SPRITESHEET.BACKGROUNDS.setWidth((int) (ScreenUtil.screen_height / 63.0f * 231.0f));
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setHeight(Map.size);
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setWidth(Map.size);
		for (TextureManager.ANIMATION_SPRITESHEET animation_spritesheet : TextureManager.ANIMATION_SPRITESHEET.values()) {
			animation_spritesheet.init();
		}
		FontManager.FONT.BLOCK_FONT.init();
		texture_initialized = true;
		System.out.println("Textures + Fonts Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	private Thread music_initializer = new Thread() {
		public void run() {
			for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
				music.init();
			}
			System.out.println("Music Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
			music_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

	private Thread sound_initializer = new Thread() {
		public void run() {
			for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
				sound.init();
			}
			System.out.println("Sound Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
			sound_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

}
