package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.Color;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class LevelSelector extends GameState {

	private Level levels[];

	private Background[] backgrounds;
	private GroundBlock[] ground_blocks;

	private int current_level;

	private boolean transition_left;
	private boolean transition_right;
	private boolean button_pressed;

	private int offset;

	private DeltaTimer transitionTimer;

	private Play play;

	private int previous_offset;
	private int delta_offset;

	private DeltaTimer errorMessageTimer;

	public LevelSelector(DairyRun dairy_run, GameStateManager.STATE state, Play play) {
		super(dairy_run, state);
		this.play = play;
	}

	@Override
	public void initialize() {
		this.levels = new Level[20];
		for (int i = 0; i < this.levels.length; i++) {
			this.levels[i] = new Level(false);
		}
		this.transitionTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 750);
		this.button_pressed = false;

		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.Spritesheet.BACKGROUNDS.getWidth(), 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height,
				Background.BLUE);
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[(ScreenUtil.screen_width / Map.size) + 2];
		// this.ground_blocks = new GroundBlock[1];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, Map.size * 1.5f, Map.size, Map.size, this.ground_blocks.length, true, GroundBlock.Theme.GRASS);
			this.ground_blocks[i].setSpawnDoodads(true);
		}
		this.errorMessageTimer = new DeltaTimer(DeltaTimer.RUN_ONCE, 500);
		this.errorMessageTimer.finish();
		createLevels();
	}

	private void createLevels() {
		// Level One.
		this.levels[0].setDescription("Deliver 5 milks."); //$NON-NLS-1$
		this.levels[0].setPauseOnFirstHouseReached("To deliever milk"); //$NON-NLS-1$
		this.levels[0].setBeaten(false);
		this.levels[0].setInitialVelocity(5f);
		this.levels[0].setVelocityMatters(false);
		this.levels[0].setVelocityNeededToWin(0);
		this.levels[0].setNumberOfMilksNeededToWin(5);
		this.levels[0].setUnlocked(true);
		this.levels[0].setRunButtonEnabled(false);
		this.levels[0].setCreateChasers(false);
		this.levels[0].setRegularMilkButtonEnabled(true);
		this.levels[0].setChocolateMilkButtonEnabled(false);
		this.levels[0].setStrawberryMilkButtonEnabled(false);
		this.levels[0].setGroundTheme(GroundBlock.Theme.GRASS);
		this.levels[0].setScoresNeededToGainOneLife(25, 50, 100, 200, 400, 800, 1600, 3200, 6400, 12800);
		this.levels[0].setPowerUpsGainedAt(25, 50, 100);
		// Level Two.
		this.levels[1].setThisLevelEqualToLevel(this.levels[0]);
		this.levels[1].setDescription("Deliver 10 milks."); //$NON-NLS-1$
		this.levels[1].setInitialVelocity(7.5f);
		this.levels[1].setNumberOfMilksNeededToWin(10);
		this.levels[1].setUnlocked(false);
		// Level Three. This is the last level that is only regular milk.
		this.levels[2].setThisLevelEqualToLevel(this.levels[1]);
		this.levels[2].setDescription("Deliver 15 milks."); //$NON-NLS-1$
		this.levels[2].setInitialVelocity(10f);
		this.levels[2].setNumberOfMilksNeededToWin(15);
		// Level Four.
		this.levels[3].setThisLevelEqualToLevel(this.levels[2]);
		this.levels[3].setDescription("Deliver 6 milks."); //$NON-NLS-1$
		this.levels[3].setInitialVelocity(6f);
		this.levels[3].setNumberOfMilksNeededToWin(6);
		this.levels[3].setChocolateMilkButtonEnabled(true);
		// Level Five.
		this.levels[4].setThisLevelEqualToLevel(this.levels[3]);
		this.levels[4].setDescription("Deliver 12 milks."); //$NON-NLS-1$
		this.levels[4].setInitialVelocity(9f);
		this.levels[4].setNumberOfMilksNeededToWin(12);
		// Level Six. This is the last level that is only regular milk and chocolate milk.
		this.levels[5].setThisLevelEqualToLevel(this.levels[4]);
		this.levels[5].setDescription("Deliver 18 milks."); //$NON-NLS-1$
		this.levels[5].setInitialVelocity(12f);
		this.levels[5].setNumberOfMilksNeededToWin(18);
		// Level Seven.
		this.levels[6].setThisLevelEqualToLevel(this.levels[5]);
		this.levels[6].setDescription("Deliver 7 milks."); //$NON-NLS-1$
		this.levels[6].setInitialVelocity(8f);
		this.levels[6].setNumberOfMilksNeededToWin(7);
		this.levels[6].setStrawberryMilkButtonEnabled(true);
		// Level Eight.
		this.levels[7].setThisLevelEqualToLevel(this.levels[6]);
		this.levels[7].setDescription("Deliver 14 milks."); //$NON-NLS-1$
		this.levels[7].setInitialVelocity(12f);
		this.levels[7].setNumberOfMilksNeededToWin(14);
		// Level Nine.
		this.levels[8].setThisLevelEqualToLevel(this.levels[7]);
		this.levels[8].setDescription("Deliver 21 milks."); //$NON-NLS-1$
		this.levels[8].setInitialVelocity(16f);
		this.levels[8].setNumberOfMilksNeededToWin(21);
		// TODO: TEMP!!!!!!!!!!!!!!!!!!!!!
		for (int i = 9; i < 20; i++) {
			this.levels[i].setDescription(":D"); //$NON-NLS-1$
			this.levels[i].setBeaten(false);
			this.levels[i].setVelocityMatters(false);
			this.levels[i].setVelocityNeededToWin(0);
			this.levels[i].setNumberOfMilksNeededToWin(10);
			this.levels[i].setUnlocked(false);
			this.levels[i].setRunButtonEnabled(false);
			this.levels[i].setCreateChasers(false);
			this.levels[i].setRegularMilkButtonEnabled(true);
			this.levels[i].setChocolateMilkButtonEnabled(false);
			this.levels[i].setStrawberryMilkButtonEnabled(false);
		}
	}

	private boolean reverse = false;
	private boolean displayingErrorMessage = false;
	private int total_offset = 0;

	@Override
	public void update(float delta) {

		if (this.displayingErrorMessage) {
			this.errorMessageTimer.update(delta);
			if (this.errorMessageTimer.isFinished()) {
				if (!reverse) {
					this.errorMessageTimer.reset();
					this.reverse = true;
				} else {
					this.reverse = false;
					this.errorMessageTimer.end();
					this.displayingErrorMessage = false;
				}
			}
		}

		Map.setCurrentScroll(0);

		if (!InputManager.pointersDown[0]) {
			this.button_pressed = false;
		}

		if (this.transition_left || this.transition_right) {
			this.transitionTimer.update(delta);
			if (this.transition_left) {
				this.offset = (int) (ScreenUtil.screen_width * this.transitionTimer.percentComplete());
			} else {
				this.offset = -1 * ((int) (ScreenUtil.screen_width * this.transitionTimer.percentComplete()));
			}
			if (this.transitionTimer.isFinished()) {
				this.transition_left = false;
				this.transition_right = false;
				this.transitionTimer.reset();
				total_offset += this.offset;
				this.offset = 0;
			}
		}

		this.delta_offset = this.offset - this.previous_offset;
		this.previous_offset = this.offset;

		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].update(delta);
		}

		if (this.transition_left) {
			TextureManager.Animation_Spritesheet.WALKING.update(delta);
			this.backgrounds[0]
					.setX((int) (-1 * (int) ((this.current_level + 1) * (TextureManager.Spritesheet.BACKGROUNDS.getWidth() / (float) this.levels.length)) + ((float) this.offset / ScreenUtil.screen_width)
							* (TextureManager.Spritesheet.BACKGROUNDS.getWidth() / this.levels.length)));
			for (int i = 0; i < this.ground_blocks.length; i++) {
				this.ground_blocks[i].setX(this.ground_blocks[i].getX() + this.delta_offset);
				if (this.ground_blocks[i].getX() > ScreenUtil.screen_width) {
					this.ground_blocks[i].randomize();
					this.ground_blocks[i].setX(this.ground_blocks[i].getX() - (Map.size * (this.ground_blocks.length)));
				}
			}
		} else if (this.transition_right) {
			TextureManager.Animation_Spritesheet.WALKING.update(delta);
			this.backgrounds[0]
					.setX((int) (-1 * (int) ((this.current_level - 1) * (TextureManager.Spritesheet.BACKGROUNDS.getWidth() / (float) this.levels.length)) + ((float) this.offset / ScreenUtil.screen_width)
							* (TextureManager.Spritesheet.BACKGROUNDS.getWidth() / this.levels.length)));
			for (int i = 0; i < this.ground_blocks.length; i++) {
				this.ground_blocks[i].setX(this.ground_blocks[i].getX() + this.delta_offset);
				if (this.ground_blocks[i].getX() + Map.size < 0) {
					this.ground_blocks[i].randomize();
					this.ground_blocks[i].setX(this.ground_blocks[i].getX() + Map.size * (this.ground_blocks.length));
				}
			}
		} else {
			this.backgrounds[0].setX(-1 * (int) ((this.current_level) * (TextureManager.Spritesheet.BACKGROUNDS.getWidth() / (float) this.levels.length)));

			if (InputManager.pointersDown[0] && !InputManager.pointersDragging[0] && !this.button_pressed) {
				// Check to see if the first button has been pressed.
				if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, 0, ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2)) {
					if (this.current_level != 0) {
						this.current_level--;
						this.button_pressed = true;
						this.transition_left = true;
					}
				}
				// Check to see if the second button has been pressed.
				if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width - Map.size * 2, ScreenUtil.screen_height / 2
						- Map.size, Map.size * 2, Map.size * 2)) {
					if (this.current_level != this.levels.length - 1) {
						this.current_level++;
						this.button_pressed = true;
						this.transition_right = true;
					}
				}
				// Check to see if the play button has been pressed.
				if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width / 2 - Map.size, ((Play) this.dairy_run
						.getGameStateManager().getState(GameStateManager.STATE.PLAY)).ground_level, Map.size * 2, Map.size * 2)) {
					if (this.levels[this.current_level].isUnlocked()) {
						this.play.setLevel(this.levels[this.current_level]);
						this.play.initialize();
						this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.PLAY);
					} else {
						this.displayingErrorMessage = true;
						this.errorMessageTimer.reset();
					}
				}
			}
		}

		this.backgrounds[1].setX(this.backgrounds[0].getX() + TextureManager.Spritesheet.BACKGROUNDS.getWidth());

		// Detect if the music toggle button has been pressed.
		StaticGUI.music_button.update(delta);
		// Detect if the back button has been pressed.
		StaticGUI.back_button.update(delta);
	}

	private void renderLevelDoodads(int level) {
		switch (level) {
		case 0:
			for (int i = 0; i < 5; i++) {
				ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.REGULAR),
						ScreenUtil.screen_width / 4 * 3 - Map.size / 2 + this.total_offset + this.offset - Map.size + (Map.size * i), Map.getGroundLevel(), Map.size, Map.size);
			}
			break;
		case 1:
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 2; j++) {
					ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.REGULAR),
							ScreenUtil.screen_width / 4 * 3 + ScreenUtil.screen_width - Map.size / 2 + this.total_offset + this.offset - Map.size + (Map.size * i),
							Map.getGroundLevel() + Map.size * j, Map.size, Map.size);
				}
			}
			break;
		case 2:
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.REGULAR),
							ScreenUtil.screen_width / 4 * 3 + ScreenUtil.screen_width * 2 - Map.size / 2 + this.total_offset + this.offset - Map.size + (Map.size * i),
							Map.getGroundLevel() + Map.size * j, Map.size, Map.size);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void render() {

		// Render the background and ground.
		this.backgrounds[0].render();
		if (this.backgrounds[1].getX() < ScreenUtil.screen_width) {
			this.backgrounds[1].render();
		}

		if (this.current_level != 0) {
			renderLevelDoodads(this.current_level - 1);
		}
		renderLevelDoodads(this.current_level);
		if (this.current_level != 19) {
			renderLevelDoodads(this.current_level + 1);
		}

		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].render();
		}

		// Render the left button.
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 15 + 11), 0, ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2);

		// Render the right button.
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 15 + 12), ScreenUtil.screen_width - Map.size * 2,
				ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2);

		if (this.transition_left) {
			renderLevelTitle(0, this.current_level + 1);
			renderLevelDescription(0, this.current_level + 1);

			renderLevelTitle(-ScreenUtil.screen_width, this.current_level);
			renderLevelDescription(-ScreenUtil.screen_width, this.current_level);

			renderLevelSign(0, this.current_level + 1);
			renderLevelSign(-ScreenUtil.screen_width, this.current_level);

			Player.render(ResourceManager.getSpriteBatch(), Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.SPRINTING, true);
		} else if (this.transition_right) {

			renderLevelTitle(0, this.current_level - 1);
			renderLevelDescription(0, this.current_level - 1);

			renderLevelTitle(ScreenUtil.screen_width, this.current_level);
			renderLevelDescription(ScreenUtil.screen_width, this.current_level);

			renderLevelSign(0, this.current_level - 1);
			renderLevelSign(ScreenUtil.screen_width, this.current_level);

			Player.render(ResourceManager.getSpriteBatch(), Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.SPRINTING);
		} else {
			// Render the current level title.
			FontManager.Font.PIXEL_REGULAR.render("Level: " + (this.current_level + 1), Color.BLACK, //$NON-NLS-1$
					ScreenUtil.screen_width / 2, (int) (ScreenUtil.screen_height - Map.size * 1.1f), Map.size, true, -1);
			// Render the current level description.
			FontManager.Font.PIXEL_REGULAR.render(this.levels[this.current_level].getDescription(), Color.BLACK, ScreenUtil.screen_width / 2,
					(int) (ScreenUtil.screen_height - Map.size * 2.6f), Map.size, true, -2);
			renderLevelSign(0, this.current_level);
			// Render the player ready to sprint.
			Player.render(ResourceManager.getSpriteBatch(), Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.READY_TO_SPRINT);
		}

		if (this.displayingErrorMessage) {
			if (this.reverse) {
				StaticGUI.renderErrorMessage("Locked", 1.0f - this.errorMessageTimer.percentComplete()); //$NON-NLS-1$
			} else {
				StaticGUI.renderErrorMessage("Locked", this.errorMessageTimer.percentComplete()); //$NON-NLS-1$
			}
		}

		// Render the toggle music button.
		StaticGUI.music_button.render(ColorXv.BROWN);
		// Render the back button.
		StaticGUI.back_button.render(ColorXv.BROWN);

	}

	private void renderLevelTitle(int x_offset, int level) {
		FontManager.Font.PIXEL_REGULAR.render("Level: " + (level + 1), Color.BLACK, ScreenUtil.screen_width / 2 + this.offset //$NON-NLS-1$
				+ x_offset, (int) (ScreenUtil.screen_height - Map.size * 1.1f), Map.size, true, -2);
	}

	private void renderLevelDescription(int x_offset, int level) {
		FontManager.Font.PIXEL_REGULAR.render(this.levels[level].getDescription(), Color.BLACK, ScreenUtil.screen_width / 2 + this.offset + x_offset,
				(int) (ScreenUtil.screen_height - Map.size * 2.6f), Map.size, true, -2);
	}

	private void renderLevelSign(int x_offset, int level) {
		if (this.levels[level].isUnlocked()) {
			// Render the play button.
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 8 + 14), ScreenUtil.screen_width / 2 - Map.size + x_offset + this.offset,
					Map.getGroundLevel(), Map.size * 2, Map.size * 2);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 8 + 13),
					ScreenUtil.screen_width / 2 - Map.size - Map.size / 2 + x_offset + this.offset, Map.getGroundLevel(), Map.size * 2, Map.size * 2);
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 8 + 13),
					ScreenUtil.screen_width / 2 - Map.size + Map.size / 2 + x_offset + this.offset, Map.getGroundLevel(), Map.size * 1.9f, Map.size * 2);
			// Render the play text.
			FontManager.Font.PIXEL_REGULAR.render("Play", Color.BLACK, ScreenUtil.screen_width / 2 + x_offset + this.offset, //$NON-NLS-1$
					(int) (Map.getGroundLevel() + Map.size - Map.size * 0.15f), (int) (Map.size * .7f), true, -1);
		} else {
			// Render the play button.
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 8 + 14), ScreenUtil.screen_width / 2 - Map.size + x_offset + this.offset,
					Map.getGroundLevel(), Map.size * 2, Map.size * 2);
			// Render the play button.
			ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 7 + 13),
					ScreenUtil.screen_width / 2 - Map.size * 1.05f + x_offset + this.offset, Map.getGroundLevel() + Map.size * 0.30f, Map.size * 2, Map.size * 2);
		}
	}

	@Override
	public void stateChangedToThis() {
		for (int i = 0; i < this.levels.length; i++) {
			if (this.levels[i].isBeaten() && i != 29 && !this.levels[i + 1].isUnlocked()) {
				this.levels[i + 1].setUnlocked(true);
				this.current_level = i + 1;
			}
		}
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