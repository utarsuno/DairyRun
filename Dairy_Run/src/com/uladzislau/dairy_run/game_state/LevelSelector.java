package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class LevelSelector extends GameState {

	private Level levels[];

	private int current_level;

	private boolean transition_left;
	private boolean transition_right;
	private boolean button_pressed;

	private int left_text_x;
	private int right_text_x;

	private DeltaTimer transitionTimer;

	private Play play;

	public LevelSelector(DairyRun dairy_run, byte id, Play play) {
		super(dairy_run, id);
		this.play = play;
	}

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.shape_renderer = shapeRenderer;
		this.sprite_batch = batch;
		this.levels = new Level[30];
		for (int i = 0; i < this.levels.length; i++) {
			this.levels[i] = new Level(false);
		}
		this.transitionTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 750);
		this.button_pressed = false;
		createLevels();
	}

	private void createLevels() {
		// Level One.
		this.levels[0].setDescription("Deliver 10 milks.");
		this.levels[0].setVelocityMatters(false);
		this.levels[0].setVelocityNeededToWin(0);
		this.levels[0].setNumberOfMilksNeededToWin(10);
		this.levels[0].setUnlocked(true);
		this.levels[0].setRunButtonEnabled(false);
		this.levels[0].setCreateChasers(false);
		this.levels[0].setRegularMilkButtonEnabled(true);
		this.levels[0].setChocolateMilkButtonEnabled(false);
		this.levels[0].setStrawberryMilkButtonEnabled(false);
		// Level Two.
		this.levels[1].setDescription(":D");
		this.levels[1].setVelocityMatters(false);
		this.levels[1].setVelocityNeededToWin(0);
		this.levels[1].setNumberOfMilksNeededToWin(10);
		this.levels[1].setUnlocked(true);
		this.levels[1].setRunButtonEnabled(false);
		this.levels[1].setCreateChasers(false);
		this.levels[1].setRegularMilkButtonEnabled(true);
		this.levels[1].setChocolateMilkButtonEnabled(false);
		this.levels[1].setStrawberryMilkButtonEnabled(false);
		// TODO: TEMP!!!!!!!!!!!!!!!!!!!!!
		for (int i = 2; i < 30; i++) {
			this.levels[i].setDescription(":D");
			this.levels[i].setVelocityMatters(false);
			this.levels[i].setVelocityNeededToWin(0);
			this.levels[i].setNumberOfMilksNeededToWin(10);
			this.levels[i].setUnlocked(true);
			this.levels[i].setRunButtonEnabled(false);
			this.levels[i].setCreateChasers(false);
			this.levels[i].setRegularMilkButtonEnabled(true);
			this.levels[i].setChocolateMilkButtonEnabled(false);
			this.levels[i].setStrawberryMilkButtonEnabled(false);
		}
	}

	@Override
	public void update(float delta) {

		if (InputManager.pointersDown[0] && !InputManager.pointersDragging[0] && !this.button_pressed) {
			// Check to see if the first button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, 0, ScreenUtil.screen_height
					/ 2 - Map.size, Map.size * 2, Map.size * 2)) {
				if (this.current_level != 0) {
					this.current_level--;
					this.left_text_x = Map.size;
					this.right_text_x = Map.size + ScreenUtil.screen_width;
					this.button_pressed = true;
					this.transition_left = true;
				}
			}
			// Check to see if the second button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width
					- Map.size * 2, ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2)) {
				if (this.current_level != this.levels.length - 1) {
					this.current_level++;
					this.left_text_x = Map.size;
					this.right_text_x = Map.size + ScreenUtil.screen_width;
					this.button_pressed = true;
					this.transition_right = true;
				}
			}
			// Check to see if the play button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width / 2
					- Map.size, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2,
					Map.size * 2)) {
				if (this.levels[this.current_level].isUnlocked()) {
					this.play.setLevel(this.levels[this.current_level]);
					this.dairy_run.getGameStateManager().changeState(GameStateManager.PLAY);
				}
			}
		}
		if (!InputManager.pointersDown[0]) {
			this.button_pressed = false;
		}

		if (this.transition_left || this.transition_right) {
			this.transitionTimer.update(delta);
			if (this.transition_left) {
				this.left_text_x = (int) (Map.size - this.transitionTimer.percentComplete() * ScreenUtil.screen_width);
				this.right_text_x = (int) (Map.size + ScreenUtil.screen_width - this.transitionTimer.percentComplete()
						* ScreenUtil.screen_width);
			} else {
				this.right_text_x = (int) (Map.size - (this.transitionTimer.percentComplete() * ScreenUtil.screen_width));
				this.left_text_x = (int) (Map.size + ScreenUtil.screen_width - this.transitionTimer.percentComplete()
						* ScreenUtil.screen_width);
			}
			if (this.transitionTimer.isFinished()) {
				this.transition_left = false;
				this.transition_right = false;
				this.transitionTimer.reset();
			}
		}

		// Detect if the music toggle button has been pressed.
		StaticGUI.music_button.update(delta);
		// Detect if the back button has been pressed.
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();

		// Render the background and ground.
		((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).renderBackground();
		((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).renderGround();

		// TODO: Render the animated character.

		// Render the right button.
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 15 + 12), ScreenUtil.screen_width - Map.size * 2,
				ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2);

		if (this.levels[this.current_level].isUnlocked()) {
			// Render the play button.
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 14), ScreenUtil.screen_width / 2
					- Map.size, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2,
					Map.size * 2);
			// Render the play text.
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Play", Color.BLACK, ScreenUtil.screen_width / 2 - Map.size + Map.size
					* 0.25f, ScreenUtil.screen_width / 2 + Map.size - Map.size * 0.25f, ((Play) this.dairy_run.getGameStateManager()
					.getState(GameStateManager.PLAY)).ground_level + Map.size - Map.size * 0.15f, ((Play) this.dairy_run
					.getGameStateManager().getState(GameStateManager.PLAY)).ground_level + Map.size * 2 - Map.size * 0.35f);
		}

		// Render the left button.
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 15 + 11), 0, ScreenUtil.screen_height / 2
				- Map.size, Map.size * 2, Map.size * 2);

		// TODO: Level transition system is currently buggy and under construction.

		// TODO: Make this pretty.
		// Render the level text.
		FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Level: " + (this.current_level + 1), Color.BLACK, Map.size,
				ScreenUtil.screen_width - Map.size, ScreenUtil.screen_height - Map.size, ScreenUtil.screen_height);

		if (this.transition_left || this.transition_right) {
			if (this.transition_left) {
				// Render the level description.
				FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level - 1].getDescription(), Color.BLACK,
						Map.size, ScreenUtil.screen_width - Map.size, ScreenUtil.screen_height - Map.size * 3, ScreenUtil.screen_height
								- Map.size * 2);
				// Render the level description.
				FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level].getDescription(), Color.BLACK,
						this.right_text_x, this.right_text_x + ScreenUtil.screen_width - Map.size * 2, ScreenUtil.screen_height - Map.size
								* 3, ScreenUtil.screen_height - Map.size * 2);
			} else {
				// Render the level description.
				FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level - 1].getDescription(), Color.BLACK,
						Map.size, ScreenUtil.screen_width - Map.size, ScreenUtil.screen_height - Map.size * 3, ScreenUtil.screen_height
								- Map.size * 2);
				// Render the level description.
				FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level].getDescription(), Color.BLACK,
						this.right_text_x, this.right_text_x + ScreenUtil.screen_width - Map.size * 2, ScreenUtil.screen_height - Map.size
								* 3, ScreenUtil.screen_height - Map.size * 2);
			}
		} else {
			// Render the level description.
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level].getDescription(), Color.BLACK,
					Map.size, ScreenUtil.screen_width - Map.size, ScreenUtil.screen_height - Map.size * 3, ScreenUtil.screen_height
							- Map.size * 2);
		}

		// Render the toggle music button.
		StaticGUI.music_button.render(this.sprite_batch);
		// Render the back button.
		StaticGUI.back_button.render(this.sprite_batch);

		this.sprite_batch.end();
	}

	@Override
	public void stateChangedToThis() {
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