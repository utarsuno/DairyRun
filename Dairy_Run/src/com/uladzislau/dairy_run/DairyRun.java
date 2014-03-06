package com.uladzislau.dairy_run;

import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.GameState;
import com.uladzislau.dairy_run.game_state.MainMenu;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.P;

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
	public static boolean transitioning_states;
	public static boolean fading_out;

	private DeltaTimer transitioning_states_timer;

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

		DairyRun.transitioning_states = false;
		DairyRun.fading_out = false;
		this.transitioning_states_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 250);

		DairyRun.paused = false;

		System.out.println("Create Method Init Time: " + (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	public void update(float delta) {
		if (!paused) {
			if (DairyRun.transitioning_states) {
				this.transitioning_states_timer.update(delta);
				if (this.transitioning_states_timer.isFinished()) {
					if (DairyRun.fading_out) {
						InputManager.setIgnoreInput(false);
						DairyRun.transitioning_states = false;
					} else {
						this.actuallyChangeState();
						this.transitioning_states_timer.reset();
						DairyRun.fading_out = true;
					}
				}
			}
			this.current_state.update(delta);
		}
	}

	@Override
	public void render() {
		update((Gdx.graphics.getDeltaTime()));

		if (!paused) {
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			this.current_state.render();

			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.resourceManager.getShapeRenderer().begin(ShapeType.Filled);
			if (DairyRun.fading_out) {
				this.resourceManager.getShapeRenderer()
						.setColor(0.0f, 0.0f, 0.0f, 1.0f - this.transitioning_states_timer.percentComplete());
			} else {
				this.resourceManager.getShapeRenderer().setColor(0.0f, 0.0f, 0.0f, this.transitioning_states_timer.percentComplete());
			}
			this.resourceManager.getShapeRenderer().rect(0, 0, ScreenUtil.screen_width, ScreenUtil.screen_height);
			this.resourceManager.getShapeRenderer().end();

		}
	}

	public void changeState(byte state_id) {
		transitioning_states = true;
		this.transitioning_states_timer.reset();
		InputManager.setIgnoreInput(true);
		this.state_to_change_to = state_id;
		DairyRun.fading_out = false;
	}

	private byte state_to_change_to;

	private void actuallyChangeState() {
		if (this.state_to_change_to != PREVIOUS_STATE) {
			this.previous_state = this.current_state;
		}
		switch (this.state_to_change_to) {
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
