package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.gui.Slider;
import com.uladzislau.dairy_run.gui.StaticGUI;

public class Options extends GameState {

	private Slider audioSlider;

	public Options(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.sprite_batch = batch;
		this.shape_renderer = shapeRenderer;
		this.audioSlider = new Slider(50, 50, 500, 500, ColorXv.GREEN, ColorXv.RED);
	}

	@Override
	public void update(float delta) {
		this.audioSlider.update((int) (delta * 1000.0f));
		StaticGUI.music_button.update(delta);
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();
		Background.render(this.sprite_batch, Background.BLUE);
		GroundBlock.render(this.sprite_batch, 0, GroundBlock.SNOW);
		StaticGUI.music_button.render(this.sprite_batch);
		StaticGUI.back_button.render(this.sprite_batch);
		this.sprite_batch.end();
		this.audioSlider.render();
	}

	@Override
	public void stateChangedToThis() {
	}

	@Override
	public void stateFinishedFadingInToExit() {
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
	}

	@Override
	public void stateFinishedFadingOut() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
