package com.uladzislau.dairy_run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.uladzislau.dairy_run.game_state.GameStateManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class DairyRun implements ApplicationListener {

	private GameStateManager gameStateManager;

	private ResourceManager resourceManager;

	public static long start_time;

	public static boolean paused = false;

	@SuppressWarnings("unused")
	@Override
	public void create() {

		DairyRun.start_time = System.currentTimeMillis();

		new InputManager(this);

		this.resourceManager = new ResourceManager();
		this.resourceManager.initialize_all_resources_and_information(this);

		this.gameStateManager = new GameStateManager(this, this.resourceManager, this.resourceManager.getAudioManager());

		DairyRun.paused = false;

		StaticUtil.log("Create Method Init Time ", (System.currentTimeMillis() - DairyRun.start_time) + "ms");
	}

	public void update(float delta) {
		if (!paused) {
			this.gameStateManager.update(delta);
		}
	}

	@Override
	public void render() {
		// Update the game logic before rendering.
		update((Gdx.graphics.getDeltaTime()));

		// Render the game if it is not paused. This if statement may not be needed.
		if (!paused) {
			// Clear the 2D screen.
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			// LibGDX might already be calling this.
			Gdx.gl.glEnable(GL10.GL_CULL_FACE);
			Gdx.gl.glCullFace(GL10.GL_FRONT);
			this.gameStateManager.render();
		}
	}

	@Override
	public void resize(int width, int height) {
		// Resizing is not currently supported.
	}

	@Override
	public void pause() {
		DairyRun.paused = true;
		this.gameStateManager.pauseCurrentState();
	}

	@Override
	public void resume() {
		DairyRun.paused = false;
		this.gameStateManager.resumeCurrentState();
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

	public GameStateManager getGameStateManager() {
		return this.gameStateManager;
	}

}
