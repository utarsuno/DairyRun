package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.entity.Score;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;

public class MainMenu extends GameState {

	private ClickableText initiate_button;
	private ClickableText terminate;
	private ClickableText top_score;
	private ClickableText top_speed;

	public MainMenu(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		this.terminate = new ClickableText("Exit", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.PIXEL_REGULAR.getWidth("Exit"), Map.size * 1, Map.size * "Exit".length(), Map.size), new ColorXv(
				ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(),
				ColorXv.BLUE.getB()), 800);
		this.initiate_button = new ClickableText("Play", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.PIXEL_REGULAR.getWidth("Play"), Map.size * 3, Map.size * 4, Map.size), new ColorXv(ColorXv.TEAL.getR(),
				ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);
		this.top_score = new ClickableText("Top Score: " + Score.getMilkHighScore(), new Rectanglei(
				ScreenUtil.screen_width - FontManager.FONT.PIXEL_REGULAR.getWidth("Top Score: " + Score.getMilkHighScore()),
				ScreenUtil.screen_height - Map.size, FontManager.FONT.PIXEL_REGULAR.getWidth("Top Score: " + Score.getMilkHighScore()),
				Map.size), new ColorXv(ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(),
				ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);

	}

	private boolean song_started = false;
	
	@Override
	public void update(float delta) {
		if (!this.song_started && AudioManager.isMusicOn()) {
			if (this.dairy_run.getResourceManager().music_initialized) {
				AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.play(.15f);
				this.song_started = true;
			}
		}

		this.initiate_button.update(delta);
		this.terminate.update(delta);
		this.top_score.update(delta);
		
		if (this.initiate_button.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			//TODO: Have the button remove it's color after this function call.
			this.initiate_button.reset();
			this.dairy_run.getGameStateManager().changeState(GameStateManager.PLAY);
		}
		if (this.terminate.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.getGameStateManager().changeState(GameStateManager.TERMINATE);
		}

	}

	@Override
	public void render() {
		this.sprite_batch.begin();
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(19), 0, 0, Map.size * 7, Map.size * 7);
		this.initiate_button.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());
		this.terminate.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());
		this.top_score.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont());
//		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 13), ScreenUtil.screen_width-Map.size*2, 0, Map.size * 2, Map.size * 2);
		this.sprite_batch.end();
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