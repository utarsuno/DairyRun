package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;

public class MainMenu extends GameState {

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.state_id = DairyRun.MAIN_MENU;
		this.shapeRenderer = shapeRenderer;
		this.batch = batch;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render() {
	}

	@Override
	public void entered() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}

}