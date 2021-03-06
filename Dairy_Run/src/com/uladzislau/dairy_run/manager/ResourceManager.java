package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.game_state.Level;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.world.Map;

public class ResourceManager {

	public boolean texture_initialized;
	public boolean music_initialized;
	public boolean sound_initialized;

	public static SpriteBatch spriteBatch;

	private AudioManager audioManager;

	public void initialize_all_resources_and_information(DairyRun dairyRun) {
		this.texture_initialized = false;
		this.music_initialized = false;
		this.sound_initialized = false;
		this.music_initializer.start();
		this.sound_initializer.start();

		AudioManager.setAudioLevel(1.0f);
		AudioManager.setMusicLevel(1.0f);
		AudioManager.setSoundLevel(1.0f);

		ScreenUtil.init();
		InfoUtil.init();
		Map.init();

		this.audioManager = new AudioManager();

		this.spriteBatch = new SpriteBatch();

		// Textures are created here because OpenGL context may only exist on the main thread.
		for (TextureManager.Spritesheet spritesheet : TextureManager.Spritesheet.values()) {
			spritesheet.initialize();
		}
		TextureManager.TextureXv.BACKGROUND.initialize();
		TextureManager.Spritesheet.BACKGROUNDS.setHeight((ScreenUtil.screen_height));
		TextureManager.Spritesheet.BACKGROUNDS.setWidth((int) (ScreenUtil.screen_height / 63.0f * 231.0f));
		TextureManager.Spritesheet.PIXEL_SPRITESHEET.setHeight(Map.size);
		TextureManager.Spritesheet.PIXEL_SPRITESHEET.setWidth(Map.size);
		for (TextureManager.Animation_Spritesheet animation_spritesheet : TextureManager.Animation_Spritesheet.values()) {
			animation_spritesheet.initialize();
		}
		FontManager.Font.PIXEL_REGULAR.initialize();
		this.texture_initialized = true;

		StaticGUI.inititialize(dairyRun);

		Level.createEndlessLevel();

		System.out.println("Textures + Fonts Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Thread music_initializer = new Thread() {
		@Override
		public void run() {
			for (AudioManager.MusicXv music : AudioManager.MusicXv.values()) {
				music.initialize();
			}
			System.out.println("Music Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
			ResourceManager.this.music_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

	private Thread sound_initializer = new Thread() {
		@Override
		public void run() {
			for (AudioManager.SoundXv sound : AudioManager.SoundXv.values()) {
				sound.initialize();
			}
			System.out.println("Sound Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
			ResourceManager.this.sound_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

	public static String credits_information() {
		String returnString = null;
		for (TextureManager.TextureXv texture : TextureManager.TextureXv.values()) {
			returnString += texture.getName() + "\t: " + texture.getSource(); //$NON-NLS-1$
			returnString += "\n"; //$NON-NLS-1$
		}
		for (TextureManager.Spritesheet sprite_sheet : TextureManager.Spritesheet.values()) {
			returnString += sprite_sheet.getName() + "\t: " + sprite_sheet.getSource(); //$NON-NLS-1$
			returnString += "\n"; //$NON-NLS-1$
		}
		for (AudioManager.SoundXv sound : AudioManager.SoundXv.values()) {
			returnString += sound.getName() + "\t: " + sound.getSource(); //$NON-NLS-1$
			returnString += "\n"; //$NON-NLS-1$
		}
		for (AudioManager.MusicXv music : AudioManager.MusicXv.values()) {
			returnString += music.getName() + "\t: " + music.getSource(); //$NON-NLS-1$
			returnString += "\n"; //$NON-NLS-1$
		}
		for (FontManager.Font font : FontManager.Font.values()) {
			returnString += font.getName() + "\t: " + font.getSource(); //$NON-NLS-1$
			returnString += "\n"; //$NON-NLS-1$
		}
		return returnString;
	}

	public static void dipose_all_resources() {
		spriteBatch.dispose();
		TextureManager.TextureXv.BACKGROUND.dispose();
		TextureManager.Spritesheet.PIXEL_SPRITESHEET.dispose();
		for (TextureManager.Animation_Spritesheet animation_sprite_sheet : TextureManager.Animation_Spritesheet.values()) {
			animation_sprite_sheet.dispose();
		}
		for (AudioManager.SoundXv sound : AudioManager.SoundXv.values()) {
			sound.dispose();
		}
		for (AudioManager.MusicXv music : AudioManager.MusicXv.values()) {
			music.dispose();
		}
		for (FontManager.Font font : FontManager.Font.values()) {
			font.dispose();
		}
	}

	public static SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public AudioManager getAudioManager() {
		return this.audioManager;
	}

}