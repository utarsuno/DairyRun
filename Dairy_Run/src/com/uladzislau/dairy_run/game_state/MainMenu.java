package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Map;
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

	public MainMenu(DairyRun dairy_run) {
		super(dairy_run);
	}

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.state_id = DairyRun.MAIN_MENU;
		this.shapeRenderer = shapeRenderer;
		this.batch = batch;
		this.terminate = new ClickableText((CharSequence) "Exit", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.BLOCK_FONT.getWidth("Exit"), Map.size * 1, Map.size * "Exit".length(), Map.size), new ColorXv(
				ColorXv.TEAL.getR(), ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(),
				ColorXv.BLUE.getB()), 800);
		this.initiate_button = new ClickableText((CharSequence) "Play", new Rectanglei(ScreenUtil.screen_width
				- FontManager.FONT.BLOCK_FONT.getWidth("Play"), Map.size * 3, Map.size * 4, Map.size), new ColorXv(ColorXv.TEAL.getR(),
				ColorXv.TEAL.getG(), ColorXv.TEAL.getB()), new ColorXv(ColorXv.BLUE.getR(), ColorXv.BLUE.getG(), ColorXv.BLUE.getB()), 800);

	}
	
	private boolean song_started = false;

	@Override
	public void update(float delta) {
		if (!song_started) {
			//TODO: Make this work.
			if (this.dairy_run.getResourceManager().music_initialized) {
				AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.play(.15f);
				song_started = true;
			}
		}
		
		
		this.initiate_button.update(delta);
		this.terminate.update(delta);

		if (this.initiate_button.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.changeState(DairyRun.PLAY);
		}
		if (this.terminate.isMouseDownOnMe() && !InputManager.pointersDragging[0]) {
			this.dairy_run.changeState(DairyRun.TERMINATE);
		}

	}

	@Override
	public void render() {
		this.batch.begin();
		this.batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(19), 0, 0, Map.size * 7, Map.size * 7);
		this.initiate_button.render(this.batch, FontManager.FONT.BLOCK_FONT.getFont());
		this.terminate.render(this.batch, FontManager.FONT.BLOCK_FONT.getFont());
		this.batch.end();
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