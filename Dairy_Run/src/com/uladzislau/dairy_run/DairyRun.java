package com.uladzislau.dairy_run;

import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.GameState;
import com.uladzislau.dairy_run.game_state.MainMenu;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class DairyRun implements ApplicationListener {

	public static final byte PREVIOUS_STATE = -2;
	public static final byte TERMINATE = -1;
	public static final byte MAIN_MENU = 0;
	public static final byte PLAY = 1;

	public GameState current_state;
	public GameState previous_state;
	private GameState main_menu;
	private GameState play;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	public static long start_time;

	private ResourceManager resourceManager;

	public static boolean paused = false;

	@Override
	public void create() {

		start_time = System.currentTimeMillis();

		ScreenUtil.init();
		InfoUtil.init();
		Map.init();
		this.resourceManager = new ResourceManager();
		this.resourceManager.initialize_all_resources();

		// Receive the user's input.
		InputManager inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
		Gdx.input.setCatchBackKey(true);

		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();

		this.main_menu = new MainMenu(this);
		this.play = new Play(this);
		this.main_menu.initialize(this.shapeRenderer, this.batch);
		this.play.initialize(this.shapeRenderer, this.batch);
		this.current_state = this.main_menu;
		this.previous_state = this.current_state;

		paused = false;

		System.out.println("Create Method Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	public void update(float delta) {
		if (!paused) {
			this.current_state.update(delta);
		}
	}

	@Override
	public void render() {
		update((Gdx.graphics.getDeltaTime()));

		if (!paused) {
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			this.current_state.render();
		}
	}

	public void changeState(byte state_id) {
		if (state_id != PREVIOUS_STATE) {
			this.previous_state = this.current_state;
		}
		switch (state_id) {
		case PREVIOUS_STATE:
			GameState tempstate = this.previous_state;
			this.current_state = this.previous_state;
			this.previous_state = tempstate;
			break;
		case TERMINATE:
			this.exit();
			break;
		case MAIN_MENU:
			this.current_state = this.main_menu;
			break;
		case PLAY:
			this.current_state = this.play;
			break;
		}
	}

	@Override
	public void resize(int width, int height) {
		ScreenUtil.init();
		Map.init();
	}

	@Override
	public void pause() {
		paused = true;
		AudioManager.pauseAllMusic();
		StaticUtil.log("State", "paused");
	}

	@Override
	public void resume() {
		paused = false;
		AudioManager.resumeAllMusic();
		StaticUtil.log("State", "resumed");
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.shapeRenderer.dispose();
		for (TextureManager.TEXTURE texture : TextureManager.TEXTURE.values()) {
			texture.dispose();
		}
		for (TextureManager.SPRITESHEET sprite_sheet : TextureManager.SPRITESHEET.values()) {
			sprite_sheet.dispose();
		}
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

	public void exit() {
		// This function will eventually have the pause and dispose functions called.
		Gdx.app.exit();
	}

	public ResourceManager getResourceManager() {
		return this.resourceManager;
	}

}
