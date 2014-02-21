package com.uladzislau.dairy_run.manager;

import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.information.ScreenUtil;

public class ResourceManager {

	public static boolean texture_initialized;
	public static boolean audio_initialized;
	public static boolean font_initialized;

	public static void initialize_all_resources() {
		texture_initialized = false;
		audio_initialized = false;
		font_initialized = false;
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
//		for (FontManager.FONT font : FontManager.FONT.values()) {
//			font.init();
//		}
		FontManager.FONT.DEFAULT_FONT.init();
		//FontManager
		font_initialized = true;
	}

	private static Thread audio_initializer = new Thread() {
		public void run() {
			for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
				sound.init();
			}
			for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
				music.init();
			}
			audio_initialized = true;
		}
	};

}
