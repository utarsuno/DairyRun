package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.Color;
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

		this.musicSlider = new Slider(Map.size, Map.size * 5, ScreenUtil.screen_width - Map.size * 5, Map.size, ColorXv.GREEN, ColorXv.RED);

		this.musicPercentage = new ClickableText("100%", new Rectanglei(ScreenUtil.screen_width - Map.size * 4, Map.size * 5, Map.size * 2, Map.size),
				ColorXv.BLACK, ColorXv.PURPLE, 800);

		this.soundSlider = new Slider(Map.size, Map.size * 2, ScreenUtil.screen_width - Map.size * 5, Map.size, ColorXv.GREEN, ColorXv.RED);

		this.soundPercentage = new ClickableText("100%", new Rectanglei(ScreenUtil.screen_width - Map.size * 4, Map.size * 2, Map.size * 2, Map.size),
				ColorXv.BLACK, ColorXv.PURPLE, 800);
	}

	@Override
	public void update(float delta) {
		this.musicSlider.update((int) (delta * 1000.0f));
		this.musicPercentage.update(delta);
		this.soundSlider.update((int) (delta * 1000.0f));
		this.soundPercentage.update(delta);

		AudioManager.setAudioLevel(this.soundSlider.percentageFull());
		this.soundPercentage.setTitle("" + (int) (AudioManager.getSoundLevel() * 100) + "%");

		AudioManager.setMusicLevel(this.musicSlider.percentageFull());
		this.musicPercentage.setTitle("" + (int) (AudioManager.getMusicLevel() * 100) + "%");

		StaticGUI.music_button.update(delta);
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();
		Background.render(this.sprite_batch, Background.BLUE);

		FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Sound", Color.BLACK, Map.size, Map.size * 3, Map.size, false);
		this.soundSlider.render(this.sprite_batch);
		this.soundPercentage.render(this.sprite_batch, true);

		FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Music", Color.BLACK, Map.size, Map.size * 6, Map.size, false);
		this.musicSlider.render(this.sprite_batch);
		this.musicPercentage.render(this.sprite_batch, true);

		GroundBlock.render(this.sprite_batch, Map.size / 2, GroundBlock.SNOW);
		GroundBlock.render(this.sprite_batch, 0, GroundBlock.SNOW_GROUND);
		StaticGUI.music_button.render(this.sprite_batch);
		StaticGUI.back_button.render(this.sprite_batch);
	}

	@Override
	public void stateChangedToThis() {
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