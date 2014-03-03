package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;

public abstract class GameState {

	protected ShapeRenderer shapeRenderer;
	protected SpriteBatch batch;

	protected final byte STATE_ID;

	protected boolean first_update = true;
	protected boolean first_render = true;

	protected DairyRun dairy_run;

	public GameState(DairyRun dairy_run, byte id) {
		this.dairy_run = dairy_run;
		this.STATE_ID = id;
	}

	public abstract void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch);

	public abstract void update(float delta);

	public abstract void render();

	public abstract void entered();

	public abstract void resume();

	public abstract void dispose();

}
