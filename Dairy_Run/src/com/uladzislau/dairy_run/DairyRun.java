package com.uladzislau.dairy_run;

import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.GameState;
import com.uladzislau.dairy_run.game_state.MainMenu;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;

public class DairyRun implements ApplicationListener {

	public static final byte PREVIOUS_STATE = -2;
	public static final byte TERMINATE = -1;
	public static final byte MAIN_MENU = 0;
	public static final byte PLAY = 1;

	public GameState current_state;
	public GameState previous_state;
	private GameState main_menu;
	private GameState play;

	private ResourceManager resourceManager;

	public static long start_time;

	public static boolean paused = false;
	public static boolean transitioning_states = false;

	@Override
	public void create() {

		DairyRun.start_time = System.currentTimeMillis();

		ScreenUtil.init();
		InfoUtil.init();
		Map.init();
		this.resourceManager = new ResourceManager();
		this.resourceManager.initialize_all_resources();

		// Receive the user's input.
		InputManager inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);

		this.main_menu = new MainMenu(this, DairyRun.MAIN_MENU);
		this.play = new Play(this, DairyRun.PLAY);
		this.main_menu.initialize(this.resourceManager.getShapeRenderer(), this.resourceManager.getSpriteBatch());
		this.play.initialize(this.resourceManager.getShapeRenderer(), this.resourceManager.getSpriteBatch());
		this.current_state = this.main_menu;
		this.previous_state = this.current_state;

		DairyRun.paused = false;

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
		transitioning_states = true;
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
			DairyRun.exit();
			break;
		case MAIN_MENU:
			this.current_state = this.main_menu;
			break;
		case PLAY:
			this.current_state = this.play;
			this.play.stateChangedToThis();
			break;
		default:
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
		DairyRun.paused = true;
		this.current_state.pause();
		AudioManager.pauseAllMusic();
	}

	@Override
	public void resume() {
		DairyRun.paused = false;
		this.current_state.resume();
		AudioManager.resumeAllMusic();
	}

	@Override
	public void dispose() {
		this.resourceManager.dipose_all_resources();
	}

	public static void exit() {
		// This function will eventually have the pause and dispose functions called.
		Gdx.app.exit();
	}

	public ResourceManager getResourceManager() {
		return this.resourceManager;
	}

}
