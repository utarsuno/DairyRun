package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.gui.Slider;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.world.Map;

public class Options extends GameState {

	private Slider musicSlider;
	private ClickableText musicPercentage;

	private Slider soundSlider;
	private ClickableText soundPercentage;

	public Options(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.sprite_batch = batch;
		this.shape_renderer = shapeRenderer;
		this.soundSlider = new Slider(Map.size, Map.size * 2, ScreenUtil.screen_width - Map.size * 4, Map.size, ColorXv.GREEN, ColorXv.RED);

		this.soundPercentage = new ClickableText("100%", new Rectanglei(ScreenUtil.screen_width - Map.size * 3, Map.size * 2, Map.size * 2,
				Map.size), new ColorXv(ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(),
				ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);
	}

	@Override
	public void update(float delta) {
		this.soundSlider.update((int) (delta * 1000.0f));
		this.soundPercentage.update(delta);
		this.soundPercentage.setTitle("" + AudioManager.getSoundLevel() + "%");
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
		this.soundPercentage.render(this.sprite_batch, true);
		this.sprite_batch.end();
		this.soundSlider.render();
	}

	@Override
	public void stateChangedToThis() {
		AudioManager.stopAllMusic();
		AudioManager.MUSIC.TEMP_OPTIONS.loop(1.0f);
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
