package com.uladzislau.dairy_run;

import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.game_state.GameState;
import com.uladzislau.dairy_run.game_state.MainMenu;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;

public class DairyRun implements ApplicationListener {

	public static final byte TERMINATE = -1;
	public static final byte MAIN_MENU = 0;
	public static final byte PLAY = 1;

	public GameState current_state;
	public GameState previous_state;
	private GameState main_menu;
	private GameState play;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	@Override
	public void create() {

		long start = System.currentTimeMillis();

		ScreenUtil.init();
		InfoUtil.init();

		InputManager inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
		Gdx.input.setCatchBackKey(true);

		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();

		this.main_menu = new MainMenu();
		this.play = new Play();
		this.main_menu.initialize(this.shapeRenderer, this.batch);
		this.play.initialize(this.shapeRenderer, this.batch);
		this.play.update(0);
		this.current_state = this.play;

		System.out.println("Init Time: " + (System.currentTimeMillis() - start) + "ms");
	}

	@Override
	public void dispose() {
	}

	public void update(float delta) {
		this.current_state.update(delta);
	}

	@Override
	public void render() {

		update((Gdx.graphics.getDeltaTime()));

		this.current_state.render();

	}

	public void changeState(byte state_id) {
		this.previous_state = this.current_state;
		switch (state_id) {
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
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void exit() {
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
		Gdx.app.exit();
	}

}
