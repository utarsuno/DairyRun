package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.Score;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MusicButton;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.world.Map;

public class MainMenu extends GameState {

	private ClickableText initiate_button;
	private ClickableText options;
	private ClickableText terminate;

	public MainMenu(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		this.terminate = new ClickableText("Exit", new Rectanglei(
				ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Exit"), Map.size * 1, Map.size * "Exit".length(),
				Map.size), new ColorXv(ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(),
				ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);

		this.initiate_button = new ClickableText("Play", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.PIXEL_REGULAR.getWidth("Play"), Map.size * 5, FontManager.FONT.PIXEL_REGULAR.getWidth("Play"), Map.size), new ColorXv(ColorXv.TEAL.getR(),
				ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);

		this.options = new ClickableText("Options", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.PIXEL_REGULAR.getWidth("Options"), Map.size * 3, FontManager.FONT.PIXEL_REGULAR.getWidth("Options"), Map.size), new ColorXv(
				ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(),
				ColorXv.BLUE.getB()), 800);
	}

	private boolean song_started = false;

	@Override
	public void update(float delta) {
		this.initiate_button.update(delta);
		this.terminate.update(delta);
		this.options.update(delta);
		if (this.initiate_button.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.LEVEL_SELECTOR);
		}
		if (this.options.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.OPTIONS);
		}
		if (this.terminate.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.TERMINATE);
		}
		// Detect if the music toggle button is pressed.
		StaticGUI.music_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();
		Background.render(this.sprite_batch, Background.GREEN);
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(19), 0, 0, Map.size * 7, Map.size * 7);
		this.initiate_button.render(this.sprite_batch, true);
		this.terminate.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());
		this.options.render(this.sprite_batch, true);

		// Render the music toggle button.
		StaticGUI.music_button.render(this.sprite_batch);

		//this.sprite_batch.end();
	}

	@Override
	public void stateChangedToThis() {
		AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.loop(1.0f);
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

	@Override
	public void stateFinishedFadingInToExit() {
		this.initiate_button.reset();
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
	}

	@Override
	public void stateFinishedFadingOut() {
		this.initiate_button.reset();
	}

}