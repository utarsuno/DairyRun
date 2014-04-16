package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.game_state.GameStateManager.STATE;

public abstract class GameState {

	protected ShapeRenderer shape_renderer;
	protected SpriteBatch sprite_batch;

	protected final GameStateManager.STATE state;

	protected boolean first_update = true;
	protected boolean first_render = true;

	protected DairyRun dairy_run;

	public GameState(DairyRun dairy_run, GameStateManager.STATE state) {
		this.dairy_run = dairy_run;
		this.state = state;
	}

	public abstract void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch);

	public abstract void update(float delta);

	public abstract void render();

	public abstract void stateChangedToThis();
	
	public abstract void stateFinishedFadingInToExit();
	
	public abstract void stateFinishedFadingInToEntrance();
	
	public abstract void stateFinishedFadingOut();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public STATE getState() {
		return this.state;
	}

	public void inStatePause() {
		// This method was created for the play state.
	}

}
