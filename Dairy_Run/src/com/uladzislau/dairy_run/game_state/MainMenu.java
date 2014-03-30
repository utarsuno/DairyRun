package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
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

	private ClickableText endless;
	private ClickableText levels;
	private ClickableText options;
	private ClickableText credits;
	private ClickableText exit;

	public MainMenu(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		this.exit = new ClickableText("Exit", new Rectanglei(ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Exit"), 0,
				Map.size * "Exit".length(), Map.size), ColorXv.BLACK, ColorXv.PURPLE, 800);
		
		this.exit = new ClickableText("Exit", new Rectanglei(ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Exit"), 0,
				Map.size * "Exit".length(), Map.size), ColorXv.BLACK, ColorXv.PURPLE, 800);

		this.levels = new ClickableText("Levels", new Rectanglei(ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Levels"),
				Map.size * "Levels".length(), FontManager.FONT.PIXEL_REGULAR.getWidth("Levels"), Map.size), ColorXv.BLACK, ColorXv.PURPLE, 800);

		this.options = new ClickableText("Options", new Rectanglei(ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Options"), Map.size * 3,
				FontManager.FONT.PIXEL_REGULAR.getWidth("Options"), Map.size), ColorXv.BLACK, ColorXv.PURPLE, 800);
	}

	private boolean song_started = false;

	@Override
	public void update(float delta) {
		this.levels.update(delta);
		this.exit.update(delta);
		this.options.update(delta);
		if (this.levels.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.LEVEL_SELECTOR);
		}
		if (this.options.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.OPTIONS);
		}
		if (this.exit.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
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

		this.sprite_batch.draw(TextureManager.TEXTURE.BACKGROUND.getTexture(), 0, 0, ScreenUtil.screen_width, ScreenUtil.screen_height);
		this.levels.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont(), true);
		this.exit.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());
		this.options.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());

		// Render the music toggle button.
		StaticGUI.music_button.render(this.sprite_batch);

		// this.sprite_batch.end();
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
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
	}

	@Override
	public void stateFinishedFadingOut() {
	}

}