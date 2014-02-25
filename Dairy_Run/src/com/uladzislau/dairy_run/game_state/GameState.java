package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;

public abstract class GameState {

	protected ShapeRenderer shapeRenderer;
	protected SpriteBatch batch;

	protected DairyRun dairy_run;

	public GameState(DairyRun dairy_run) {
		this.dairy_run = dairy_run;
	}

	public byte state_id;

	protected boolean first_update = true;
	protected boolean first_render = true;

	public abstract void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch);

	public abstract void update(float delta);

	public abstract void render();

	public abstract void entered();

	public abstract void resume();

	public abstract void dispose();

}
