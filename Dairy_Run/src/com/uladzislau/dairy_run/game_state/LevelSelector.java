package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
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

		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, 0, TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), ScreenUtil.screen_height, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), 0, TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(),
				ScreenUtil.screen_height, Background.BLUE);
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[(ScreenUtil.screen_width / Map.size) + 2];
		// this.ground_blocks = new GroundBlock[1];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, (int) (Map.size * 1.5f), Map.size, Map.size, this.ground_blocks.length);
		}

		createLevels();
	}

	private void createLevels() {
		for (int i = 0; i < this.levels.length; i++) {
			this.levels[i].setBeaten(false);
		}
		// Level One.
		this.levels[0].setDescription("Deliver 10 milks.");
		this.levels[0].setInitialVelocity(5f);
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
		this.levels[1].setDescription("Deliver 20 milks.");
		this.levels[1].setInitialVelocity(10f);
		this.levels[1].setVelocityMatters(false);
		this.levels[1].setVelocityNeededToWin(0);
		this.levels[1].setNumberOfMilksNeededToWin(20);
		this.levels[1].setUnlocked(false);
		this.levels[1].setRunButtonEnabled(false);
		this.levels[1].setCreateChasers(false);
		this.levels[1].setRegularMilkButtonEnabled(true);
		this.levels[1].setChocolateMilkButtonEnabled(false);
		this.levels[1].setStrawberryMilkButtonEnabled(false);
		// Level Three.
		this.levels[2].setDescription("Deliver 30 milks.");
		this.levels[2].setInitialVelocity(15f);
		this.levels[2].setVelocityMatters(false);
		this.levels[2].setVelocityNeededToWin(0);
		this.levels[2].setNumberOfMilksNeededToWin(30);
		this.levels[2].setUnlocked(false);
		this.levels[2].setRunButtonEnabled(false);
		this.levels[2].setCreateChasers(false);
		this.levels[2].setRegularMilkButtonEnabled(true);
		this.levels[2].setChocolateMilkButtonEnabled(false);
		this.levels[2].setStrawberryMilkButtonEnabled(false);
		// TODO: TEMP!!!!!!!!!!!!!!!!!!!!!
		for (int i = 3; i < 30; i++) {
			this.levels[i].setDescription(":D");
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

	private int previous_offset;
	private int delta_offset;

	@Override
	public void update(float delta) {

		if (InputManager.pointersDown[0] && !InputManager.pointersDragging[0] && !this.button_pressed) {
			// Check to see if the first button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, 0, ScreenUtil.screen_height / 2 - Map.size,
					Map.size * 2, Map.size * 2)) {
				if (this.current_level != 0) {
					this.current_level--;
					this.button_pressed = true;
					this.transition_left = true;
				}
			}
			// Check to see if the second button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width - Map.size * 2,
					ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2)) {
				if (this.current_level != this.levels.length - 1) {
					this.current_level++;
					this.button_pressed = true;
					this.transition_right = true;
				}
			}
			// Check to see if the play button has been pressed.
			if (Rectanglei.isPointerInsideARectanglei(InputManager.pointers[0].x, InputManager.pointers[0].y, ScreenUtil.screen_width / 2 - Map.size,
					((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2, Map.size * 2)) {
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
				this.offset = (int) (ScreenUtil.screen_width * this.transitionTimer.percentComplete());
			} else {
				this.offset = -1 * ((int) (ScreenUtil.screen_width * this.transitionTimer.percentComplete()));
			}
			if (this.transitionTimer.isFinished()) {
				this.transition_left = false;
				this.transition_right = false;
				this.transitionTimer.reset();
				this.offset = 0;
			}
		}

		this.delta_offset = this.offset - this.previous_offset;
		this.previous_offset = this.offset;

		if (this.transition_left) {
			TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.update(delta);
			this.backgrounds[0]
					.setX((int) (-1 * (int) ((this.current_level + 1) * (TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() / 30.0f)) + ((float) this.offset / ScreenUtil.screen_width)
							* (TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() / 30.0f)));
			for (int i = 0; i < this.ground_blocks.length; i++) {
				this.ground_blocks[i].setX(this.ground_blocks[i].getX() + this.delta_offset);
				if (this.ground_blocks[i].getX() > ScreenUtil.screen_width) {
					this.ground_blocks[i].randomize();
					this.ground_blocks[i].setX(this.ground_blocks[i].getX() - (Map.size * (this.ground_blocks.length)));
				}
			}
		} else if (this.transition_right) {
			TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.update(delta);
			this.backgrounds[0]
					.setX((int) (-1 * (int) ((this.current_level - 1) * (TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() / 30.0f)) + ((float) this.offset / ScreenUtil.screen_width)
							* (TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() / 30.0f)));
			for (int i = 0; i < this.ground_blocks.length; i++) {
				this.ground_blocks[i].setX(this.ground_blocks[i].getX() + delta_offset);
				if (this.ground_blocks[i].getX() + Map.size < 0) {
					this.ground_blocks[i].randomize();
					this.ground_blocks[i].setX(this.ground_blocks[i].getX() + Map.size * (this.ground_blocks.length));
				}
			}

		} else {
			this.backgrounds[0].setX(-1 * (int) ((this.current_level) * (TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() / 30.0f)));
		}

		this.backgrounds[1].setX(this.backgrounds[0].getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth());

		// Detect if the music toggle button has been pressed.
		StaticGUI.music_button.update(delta);
		// Detect if the back button has been pressed.
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();

		// Render the background and ground.
		this.backgrounds[0].render(this.sprite_batch, 0);
		if (this.backgrounds[1].getX() < ScreenUtil.screen_width) {
			this.backgrounds[1].render(this.sprite_batch, 0);
		}

		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].render(this.sprite_batch, 0);
		}

		// Render the left button.
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 15 + 11), 0, ScreenUtil.screen_height / 2 - Map.size, Map.size * 2,
				Map.size * 2);

		// Render the right button.
		this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 15 + 12), ScreenUtil.screen_width - Map.size * 2,
				ScreenUtil.screen_height / 2 - Map.size, Map.size * 2, Map.size * 2);

		if (this.transition_left) {
			renderLevelTitle(0, this.current_level + 1);
			renderLevelDescription(0, this.current_level + 1);

			renderLevelTitle(-ScreenUtil.screen_width, this.current_level);
			renderLevelDescription(-ScreenUtil.screen_width, this.current_level);

			renderLevelSign(0, this.current_level + 1);
			renderLevelSign(-ScreenUtil.screen_width, this.current_level);

			Player.render(this.sprite_batch, Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.SPRINTING, true);
		} else if (this.transition_right) {

			renderLevelTitle(0, this.current_level - 1);
			renderLevelDescription(0, this.current_level - 1);

			renderLevelTitle(ScreenUtil.screen_width, this.current_level);
			renderLevelDescription(ScreenUtil.screen_width, this.current_level);

			renderLevelSign(0, this.current_level - 1);
			renderLevelSign(ScreenUtil.screen_width, this.current_level);

			Player.render(this.sprite_batch, Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.SPRINTING);
		} else {
			// Render the current level title.
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Level: " + (this.current_level + 1), Color.BLACK, ScreenUtil.screen_width / 2,
					(int) (ScreenUtil.screen_height - Map.size * 1.1f), Map.size);
			// Render the current level description.
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[this.current_level].getDescription(), Color.BLACK,
					ScreenUtil.screen_width / 2, (int) (ScreenUtil.screen_height - Map.size * 2.6f), Map.size);
			renderLevelSign(0, this.current_level);
			// Render the player ready to sprint.
			Player.render(this.sprite_batch, Map.size * 2, (int) (Map.size * 1.5f), Map.size, Map.size, Player.READY_TO_SPRINT);
		}

		// Render the toggle music button.
		StaticGUI.music_button.render(this.sprite_batch);
		// Render the back button.
		StaticGUI.back_button.render(this.sprite_batch);

	}

	private void renderLevelTitle(int x_offset, int level) {
		FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Level: " + (level + 1), Color.BLACK, ScreenUtil.screen_width / 2 + this.offset + x_offset,
				(int) (ScreenUtil.screen_height - Map.size * 1.1f), Map.size);
	}

	private void renderLevelDescription(int x_offset, int level) {
		FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.levels[level].getDescription(), Color.BLACK, ScreenUtil.screen_width / 2 + this.offset
				+ x_offset, (int) (ScreenUtil.screen_height - Map.size * 2.6f), Map.size);
	}

	private void renderLevelSign(int x_offset, int level) {
		if (this.levels[level].isUnlocked()) {
			// Render the play button.
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 14), ScreenUtil.screen_width / 2 - Map.size + x_offset
					+ this.offset, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2, Map.size * 2);
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 13), ScreenUtil.screen_width / 2 - Map.size - Map.size / 2
					+ x_offset + this.offset, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2,
					Map.size * 2);
			// TODO: Either fix texture or create constant scale.
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 13), ScreenUtil.screen_width / 2 - Map.size + Map.size / 2
					+ x_offset + this.offset, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 1.9f,
					Map.size * 2);
			// Render the play text.
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "Play", Color.BLACK, ScreenUtil.screen_width / 2 + x_offset + this.offset,
					(int) (((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level + Map.size - Map.size * 0.15f),
					(int) (Map.size * .7f));
		} else {
			// Render the play button.
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 8 + 14), ScreenUtil.screen_width / 2 - Map.size + x_offset
					+ this.offset, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level, Map.size * 2, Map.size * 2);
			// Render the play button.
			this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 7 + 13), ScreenUtil.screen_width / 2 - Map.size * 1.05f
					+ x_offset + this.offset, ((Play) this.dairy_run.getGameStateManager().getState(GameStateManager.PLAY)).ground_level + Map.size * 0.30f,
					Map.size * 2, Map.size * 2);
		}
	}

	@Override
	public void stateChangedToThis() {
		AudioManager.MUSIC.LEVEL_SELECTOR_MUSIC.loop(1.0f);
		for (int i = 0; i < this.levels.length; i++) {
			if (this.levels[i].isBeaten() && i != 29 && !this.levels[i + 1].isUnlocked()) {
				this.levels[i + 1].setUnlocked(true);
				this.current_level = i + 1;
			}
		}
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