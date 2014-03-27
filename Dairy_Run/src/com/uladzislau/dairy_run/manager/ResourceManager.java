package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.world.Map;

public class ResourceManager {

	public boolean texture_initialized;
	public boolean music_initialized;
	public boolean sound_initialized;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;
	
	private AudioManager audioManager;

	public void initialize_all_resources_and_information(DairyRun dr) {
		this.texture_initialized = false;
		this.music_initialized = false;
		this.sound_initialized = false;
		this.music_initializer.start();
		this.sound_initializer.start();

		ScreenUtil.init();
		InfoUtil.init();
		Map.init();
		
		this.audioManager = new AudioManager();

		this.shapeRenderer = new ShapeRenderer();
		this.spriteBatch = new SpriteBatch();

		// Textures are created here because OpenGL context may only exist on the main thread.
		for (TextureManager.SPRITESHEET spritesheet : TextureManager.SPRITESHEET.values()) {
			spritesheet.initialize();
		}
		TextureManager.SPRITESHEET.BACKGROUNDS.setHeight((ScreenUtil.screen_height));
		TextureManager.SPRITESHEET.BACKGROUNDS.setWidth((int) (ScreenUtil.screen_height / 63.0f * 231.0f));
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setHeight(Map.size);
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setWidth(Map.size);
		for (TextureManager.ANIMATION_SPRITESHEET animation_spritesheet : TextureManager.ANIMATION_SPRITESHEET.values()) {
			animation_spritesheet.initialize();
		}
		FontManager.FONT.PIXEL_REGULAR.initialize();
		this.texture_initialized = true;

		StaticGUI.inititialize(dr);

		System.out.println("Textures + Fonts Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	private Thread music_initializer = new Thread() {
		@Override
		public void run() {
			for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
				music.initialize();
			}
			System.out.println("Music Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
			ResourceManager.this.music_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

	private Thread sound_initializer = new Thread() {
		@Override
		public void run() {
			for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
				sound.initialize();
			}
			System.out.println("Sound Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
			ResourceManager.this.sound_initialized = true;
			Thread.currentThread().interrupt();
			return;
		}
	};

	public static String credits_information() {
		String returnString = null;
		for (TextureManager.TEXTURE texture : TextureManager.TEXTURE.values()) {
			returnString += texture.getName() + "\t: " + texture.getSource();
			returnString += "\n";
		}
		for (TextureManager.SPRITESHEET sprite_sheet : TextureManager.SPRITESHEET.values()) {
			returnString += sprite_sheet.getName() + "\t: " + sprite_sheet.getSource();
			returnString += "\n";
		}
		for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
			returnString += sound.getName() + "\t: " + sound.getSource();
			returnString += "\n";
		}
		for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
			returnString += music.getName() + "\t: " + music.getSource();
			returnString += "\n";
		}
		for (FontManager.FONT font : FontManager.FONT.values()) {
			returnString += font.getName() + "\t: " + font.getSource();
			returnString += "\n";
		}
		return returnString;
	}

	public void dipose_all_resources() {
		this.spriteBatch.dispose();
		this.shapeRenderer.dispose();
		for (TextureManager.TEXTURE texture : TextureManager.TEXTURE.values()) {
			texture.dispose();
		}
		TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.dispose();
		for (TextureManager.ANIMATION_SPRITESHEET animation_sprite_sheet : TextureManager.ANIMATION_SPRITESHEET.values()) {
			animation_sprite_sheet.dispose();
		}
		for (AudioManager.SOUND sound : AudioManager.SOUND.values()) {
			sound.dispose();
		}
		for (AudioManager.MUSIC music : AudioManager.MUSIC.values()) {
			music.dispose();
		}
		for (FontManager.FONT font : FontManager.FONT.values()) {
			font.dispose();
		}
	}

	public ShapeRenderer getShapeRenderer() {
		return this.shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}

	public SpriteBatch getSpriteBatch() {
		return this.spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	public AudioManager getAudioManager() {
		return this.audioManager;
	}

}