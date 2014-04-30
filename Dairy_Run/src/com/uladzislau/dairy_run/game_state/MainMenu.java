package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Circlef;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class MainMenu extends GameState {

	private ClickableText endless;
	private ClickableText levels;
	private ClickableText options;
	private ClickableText tutorial;
	private ClickableText credits;
	private ClickableText exit;

	private Circlef sun;
	private DeltaTimer sun_timer;

	private ColorXv initial_colorXv;
	private float house_corner_x = (1400.0f / 2048.0f);
	private float house_corner_y = (324.0f / 1024.0f);
	private float slope;
	private float b;
	private float house_end = (1900.0f / 2048.0f);
	private DeltaTimer house_timer;

	public MainMenu(DairyRun dairy_run, GameStateManager.STATE state) {
		super(dairy_run, state);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		this.sun = new Circlef(ScreenUtil.screen_width * (811.0f / 2048.0f), ScreenUtil.screen_height * (894.0f / 1024.0f), ScreenUtil.screen_diagonal * .05f);
		this.sun_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 600);
		this.house_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 6000);
		this.initial_colorXv = new ColorXv(ColorXv.YELLOW);
		this.initial_colorXv.setA(0.6f);
		this.exit = new ClickableText("Exit", new Rectanglei(ScreenUtil.screen_width - FontManager.Font.PIXEL_REGULAR.getWidth("Exit") * 0.8f, 0, Map.size
				* "Exit".length(), Map.size * 0.8f), this.initial_colorXv, ColorXv.RED, 800);

		this.credits = new ClickableText("Credits", new Rectanglei(ScreenUtil.screen_width - FontManager.Font.PIXEL_REGULAR.getWidth("Credits") * 0.8f,
				Map.size * 1.0f, Map.size * "Credits".length() * 0.8f, Map.size * 0.8f), this.initial_colorXv, ColorXv.RED, 800);

		this.tutorial = new ClickableText("Tutorial", new Rectanglei(ScreenUtil.screen_width - FontManager.Font.PIXEL_REGULAR.getWidth("Tutorial") * 0.8f,
				Map.size * 2.0f, Map.size * "Tutorial".length() * 0.8f, Map.size * 0.8f), this.initial_colorXv, ColorXv.RED, 800);

		this.endless = new ClickableText("Endless", new Rectanglei(Map.size / 10.0f, Map.size * 1.0f, Map.size * "Endless".length() * 0.8f, Map.size * 0.8f),
				this.initial_colorXv, ColorXv.RED, 800);

		this.levels = new ClickableText("Levels", new Rectanglei(Map.size / 10.0f, Map.size * 2.0f, FontManager.Font.PIXEL_REGULAR.getWidth("Levels") * 0.8f,
				Map.size * 0.8f), this.initial_colorXv, ColorXv.RED, 800);

		this.options = new ClickableText("Options", new Rectanglei(Map.size + Map.size / 2, 0, FontManager.Font.PIXEL_REGULAR.getWidth("Options") * 0.8f,
				Map.size * 0.8f), this.initial_colorXv, ColorXv.RED, 800);

		this.house_corner_x *= ScreenUtil.screen_width;
		this.house_corner_y *= ScreenUtil.screen_height;
		this.house_end *= ScreenUtil.screen_width;

		this.slope = (this.house_corner_y) / (this.house_corner_x - this.house_end);
		this.b = -1.0f * (this.slope * this.house_corner_x - this.house_corner_y);

		this.getMusic().play();
		this.getMusic().loop(true);

	}

	private boolean update_sun = false;
	private boolean sun_fading_out;
	private boolean doorbell_just_played = false;

	@Override
	public void update(float delta) {
		this.levels.update(delta);
		this.exit.update(delta);
		this.endless.update(delta);
		this.options.update(delta);
		this.credits.update(delta);
		this.tutorial.update(delta);

		if (this.sun.isPointInsideOrOnMe(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
			this.update_sun = true;
		}
		if (this.update_sun) {
			this.sun_timer.update(delta);
			if (this.sun_timer.isFinished()) {
				this.sun_timer.reset();
				if (!this.sun_fading_out) {
					this.sun_fading_out = true;
				} else {
					this.sun_fading_out = false;
					this.update_sun = false;
				}
			}
		}

		if (InputManager.pointersDown[0]) {
			if (InputManager.pointers[0].x >= this.house_corner_x) {
				if (InputManager.pointers[0].y >= (this.slope * InputManager.pointers[0].x + this.b)) {
					if (!this.doorbell_just_played) {
						//TODO: Make sure a button hasn't been pressed first.
						AudioManager.SoundXv.DOORBELL.playSound();
						this.doorbell_just_played = true;
					}
				}
			}
		} else {
			this.house_timer.reset();
			this.doorbell_just_played = false;
		}

		if (this.doorbell_just_played) {
			this.house_timer.update(delta);
			if (this.house_timer.isFinished()) {
				this.house_timer.reset();
				this.doorbell_just_played = false;
			}
		}

		if (!InputManager.pointersDragging[0]) {
			if (this.levels.isMouseDownOnMe()) {
				this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.LEVEL_SELECTOR);
			}
			if (this.options.isMouseDownOnMe()) {
				this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.OPTIONS);
			}
			if (this.exit.isMouseDownOnMe()) {
				this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.TERMINATE);
			}
			if (this.endless.isMouseDownOnMe()) {
				this.dairy_run.getGameStateManager().resetEndless();
				this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.PLAY);
			}
			if (this.credits.isMouseDownOnMe()) {
				this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.CREDITS);
			}
		}
		// Detect if the music toggle button is pressed.
		StaticGUI.music_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();

		this.sprite_batch.draw(TextureManager.TextureXv.BACKGROUND.getTexture(), 0, 0, ScreenUtil.screen_width, ScreenUtil.screen_height);
		FontManager.Font.PIXEL_REGULAR.getFont().setScale(FontManager.Font.PIXEL_REGULAR.getFont().getScaleX() * 0.8f,
				FontManager.Font.PIXEL_REGULAR.getFont().getScaleY() * 0.8f);
		FontManager.Font.PIXEL_REGULAR.setColor(ColorXv.YELLOW);
		this.endless.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		this.credits.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		this.levels.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		this.exit.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		this.options.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		this.tutorial.render(this.sprite_batch, FontManager.Font.PIXEL_REGULAR.getFont());
		FontManager.Font.PIXEL_REGULAR.getFont().setScale(FontManager.Font.PIXEL_REGULAR.getFont().getScaleX() / 0.8f,
				FontManager.Font.PIXEL_REGULAR.getFont().getScaleY() / 0.8f);

		// Render the music toggle button.
		StaticGUI.music_button.render(this.sprite_batch, ColorXv.YELLOW);

		if (this.sun_fading_out) {
			this.sprite_batch.setColor(ColorXv.YELLOW.getR(), ColorXv.YELLOW.getG(), ColorXv.YELLOW.getB(), (1.0f / 4.9f)
					- (this.sun_timer.percentComplete() / 4.9f));
		} else {
			this.sprite_batch.setColor(ColorXv.YELLOW.getR(), ColorXv.YELLOW.getG(), ColorXv.YELLOW.getB(), this.sun_timer.percentComplete() / 4.9f);
		}
		this.sprite_batch.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.WHITE), 0, 0, ScreenUtil.screen_width,
				ScreenUtil.screen_height);
		this.sprite_batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void stateChangedToThis() {
		// Intentionally left blank.
	}

	@Override
	public void pause() {
		// Intentionally left blank.
	}

	@Override
	public void resume() {
		// Intentionally left blank.
	}

	@Override
	public void dispose() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingInToExit() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingOut() {
		// Intentionally left blank.
	}

}