package com.uladzislau.dairy_run.manager;

import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.information.ScreenUtil;

public class ResourceManager {

	public static boolean texture_initialized;
	public static boolean audio_initialized;

	public static void initialize_all_resources() {
		texture_initialized = false;
		audio_initialized = false;
		audio_initializer.start();
		// Textures are created here because OpenGL context may only exist on the main thread.
		for (TextureManager.SPRITESHEET spritesheet : TextureManager.SPRITESHEET.values()) {
			spritesheet.init();
		}
		for (TextureManager.ANIMATION_SPRITESHEET animation_spritesheet : TextureManager.ANIMATION_SPRITESHEET.values()) {
			animation_spritesheet.init();
		}
		TextureManager.SPRITESHEET.BACKGROUNDS.setHeight((int) (ScreenUtil.screen_height));
		TextureManager.SPRITESHEET.BACKGROUNDS.setWidth((int) (ScreenUtil.screen_height / 63.0f * 231.0f));
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setHeight(Map.size);
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setWidth(Map.size);
		texture_initialized = true;
		System.out.println("Textures Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	private static Thread audio_initializer = new Thread() {
		public void run() {
			for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
				music.init();
			}
			for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
				sound.init();
			}
			System.out.println("Audio Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
			audio_initialized = true;
		}
	};

}
