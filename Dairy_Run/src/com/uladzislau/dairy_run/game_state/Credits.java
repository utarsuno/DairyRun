package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.game_state.GameStateManager.STATE;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Credits extends GameState {

	private int layer_y[];

	public Credits(DairyRun dairy_run, STATE state) {
		super(dairy_run, state);
	}

	private float y_offset;

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.shape_renderer = shapeRenderer;
		this.sprite_batch = batch;
		this.layer_y = new int[Map.number_of_vertical_blocks * 2];
		for (int i = 0; i < this.layer_y.length; i++) {
			this.layer_y[i] = Map.size * 2 * i;
		}
	}

	@Override
	public void update(float delta) {
		this.y_offset = 0.5f * delta;
		for (int i = 0; i < this.layer_y.length; i++) {
			this.layer_y[i] -= this.y_offset;
			if (this.layer_y[i] + Map.size * 2 < 0) {
				this.layer_y[i] += this.layer_y.length * Map.size * 2;
			}
		}
		TextureManager.ANIMATION_SPRITESHEET.CLIMBING_STAIRS.update(delta);
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();

		for (int i = 0; i < this.layer_y.length; i++) {
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(TextureManager.LADDER), Map.size, this.layer_y[i], Map.size * 2,
					Map.size * 2);
		}

		this.sprite_batch.draw(TextureManager.ANIMATION_SPRITESHEET.CLIMBING_STAIRS.getCurrentFrame(), Map.size, Map.size, Map.size * 2, Map.size * 2);
		StaticGUI.back_button.render(this.sprite_batch, ColorXv.ORANGE);
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