package com.uladzislau.dairy_run.game_state;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.Chaser;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.entity.Score;
import com.uladzislau.dairy_run.entity.Tree;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MilkButton;
import com.uladzislau.dairy_run.entity.button.RunButton;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class Play extends GameState {

	private final ColorXv PAUSE_COLOR = new ColorXv(ColorXv.RED.getR(), ColorXv.RED.getG(), ColorXv.RED.getB(), 0.60f);

	private Background[] backgrounds;
	private GroundBlock[] ground_blocks;
	private House[] houses;
	private Tree[] trees;
	private Player player;

	// TOOD: Change it in the future to only create new ones when needed.
	private ArrayList<Chaser> chasers;

	private CircleButton[] buttons;

	private float current_scroll;
	private float velocity;
	private float acceleration;

	public int ground_level;

	private boolean lost = false;
	private boolean allow_tap_to_start = true;

	private Score score;

	private DeltaTimer resumeTimer;

	private boolean game_in_session = false;

	private boolean pause_menu_open = false;

	private ClickableText paused;
	private ClickableText game_over;
	private ClickableText retry;
	private ClickableText options;
	private ClickableText main_menu;

	private Level level;

	public Play(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {
		// Used for rendering.
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		initialize();
	}

	public void initialize() {
		// Set the ground level.
		this.ground_level = (int) (Map.size * 1.5);
		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, 0, TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), ScreenUtil.screen_height, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), 0, TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(),
				ScreenUtil.screen_height, Background.BLUE);
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[ScreenUtil.screen_width / Map.size + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, this.ground_level, Map.size, Map.size, this.ground_blocks.length,
					this.level.getGroundBlockTheme());
		}
		// Create the houses.
		this.houses = new House[5];
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i] = new House(this);
			this.houses[i].createHouse((i + 1) * 10 * Map.size, this.ground_level);
		}
		this.velocity = this.level.getInitialVelocity();

		// Create the trees.
		this.trees = new Tree[30];
		for (int i = 0; i < this.trees.length; i++) {
			this.trees[i] = new Tree(this.ground_level, i, this.trees.length);
			this.trees[i].resetPosition((int) this.current_scroll);
		}
		// Create the buttons.
		this.buttons = new CircleButton[4];
		this.buttons[0] = new RunButton((ScreenUtil.screen_width / 20) + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, this);
		this.buttons[1] = new MilkButton((ScreenUtil.screen_width / 20) * 3 + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f,
				TextureManager.REGULAR, this);
		this.buttons[2] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) * 3 - Map.size / 2, Map.size / 8 + Map.size / 2,
				Map.size * 0.6f, TextureManager.CHOCOLATE, this);
		this.buttons[3] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) - Map.size / 2, Map.size / 8 + Map.size / 2,
				Map.size * 0.6f, TextureManager.STRAWBERRY, this);
		// Create the player.
		this.player = new Player((int) (Map.size * 2.5f), this.ground_level, this);
		this.score = new Score();
		this.resumeTimer = new DeltaTimer();
		this.chasers = new ArrayList<Chaser>();

		this.paused = new ClickableText("Resume", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "Resume".length()) / 2, Map.size * 5.4f,
				"Resume".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800);

		this.game_over = new ClickableText("Game Over", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "Game Over".length()) / 2, Map.size * 5.4f,
				"Game Over".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800);

		this.retry = new ClickableText("retry", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "retry".length()) / 2, Map.size * 4.2f,
				"retry".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800);

		this.options = new ClickableText("options", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "options".length()) / 2, Map.size * 3,
				"options".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800);

		this.main_menu = new ClickableText("main menu", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "main_menu".length()) / 2, Map.size * 1.8f,
				"main_menu".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800);
	}

	private boolean just_resumed = false;

	private boolean pc_mouse_down_on_first_resume_update = false;
	private boolean allow_resume = true;
	private boolean reset = false;

	private boolean tapped_to_start = false;

	private boolean state_is_transitioning = false;

	@Override
	public void update(float delta) {
		if (this.just_resumed) {
			if (InfoUtil.CURRENT_PLATEFORM == InfoUtil.DESKTOP) {
				if (this.resumeTimer.getTotalDelta() == 0) {
					if (InputManager.pointersDown[0]) {
						this.pc_mouse_down_on_first_resume_update = true;
						this.allow_resume = false;
					}
				}
				if (this.pc_mouse_down_on_first_resume_update) {
					if (!InputManager.pointersDown[0]) {
						this.allow_resume = true;
					}
				}
				this.resumeTimer.update(delta);
				if (this.resumeTimer.getTotalDelta() > 50) {
					if (InputManager.pointersDown[0]) {
						if (this.allow_resume) {
							this.just_resumed = false;
						}
					}
				}
				this.allow_tap_to_start = false;
				this.reset = true;
			}
		} else {
			if (this.reset) {
				this.pc_mouse_down_on_first_resume_update = false;
				this.allow_resume = true;
				this.resumeTimer.reset();
				this.reset = false;
			}

			if (this.tapped_to_start) {
				if (!this.pause_menu_open) {
					this.player.update(delta, (int) this.current_scroll);

					if (!this.lost) {
						this.acceleration = 0.00002f * ScreenUtil.screen_width * delta * 1;
						this.velocity += this.acceleration;
					}
					this.current_scroll -= this.velocity;

					// If the background has moved off the screen, shift it back into view.
					for (Background background : this.backgrounds) {
						background.update((int) this.current_scroll);
					}

					// If the house has moved off the screen, randomize it and shift it back into view.
					for (House house : this.houses) {
						house.update(delta, (int) this.current_scroll);
					}

					// If the ground block has moved off the screen, shift it back into view.
					for (GroundBlock gb : this.ground_blocks) {
						gb.update((int) this.current_scroll);
					}

					for (Tree tree : this.trees) {
						tree.update((int) this.current_scroll);
					}

					// Check for button press. If pressed they will do their respective actions.
					if (this.level.isRunButtonEnabled()) {
						this.buttons[0].update(delta);
					}
					if (this.level.isRegularMilkButtonEnabled()) {
						this.buttons[1].update(delta);
					}
					if (this.level.isChocolateMilkButtonEnabled()) {
						this.buttons[2].update(delta);
					}
					if (this.level.isStrawberryMilkButtonEnabled()) {
						this.buttons[3].update(delta);
					}
				} else {
					this.paused.update(delta);
					this.retry.update(delta);
					this.main_menu.update(delta);
					this.options.update(delta);

					if (!InputManager.pointersDragging[0]) {
						if (this.paused.isMouseDownOnMe()) {
							this.pause_menu_open = false;
						}
						if (this.main_menu.isMouseDownOnMe()) {
							this.dairy_run.getGameStateManager().changeState(GameStateManager.MAIN_MENU);
							this.dairy_run.getGameStateManager().clearHistoryStates();
							setLost(false);
							this.pause_menu_open = false;
						}
						if (this.retry.isMouseDownOnMe()) {
							retry();
							this.pause_menu_open = false;
						}
						if (this.options.isMouseDownOnMe()) {
							this.dairy_run.getGameStateManager().changeState(GameStateManager.OPTIONS);
							this.pause_menu_open = false;
						}
					}

				}

				if (this.lost) {
					this.game_over.update(delta);
					this.retry.update(delta);
					this.main_menu.update(delta);
					this.options.update(delta);

					if (!InputManager.pointersDragging[0]) {
						if (this.main_menu.isMouseDownOnMe()) {
							this.dairy_run.getGameStateManager().changeState(GameStateManager.MAIN_MENU);
							this.dairy_run.getGameStateManager().clearHistoryStates();
							setLost(false);
						}
						if (this.retry.isMouseDownOnMe()) {
							retry();
						}
						if (this.options.isMouseDownOnMe()) {
							this.dairy_run.getGameStateManager().changeState(GameStateManager.OPTIONS);
						}
					}

				} else {
					StaticGUI.pause_button.update(delta);
				}

				// TODO: Make a more elegant system.
				for (int i = 0; i < this.chasers.size(); i++) {
					if (this.chasers.get(i).isRemovable()) {
						this.chasers.remove(i);
					}
					if (i != 0 && !(i >= this.chasers.size())) {
						this.chasers.get(i).update(delta);
					}
				}

				if (!this.state_is_transitioning) {
					if (this.level.getNumberOfMilksNeededToWin() != -1) {
						if (this.player.getNumberOfMilksDelivered() >= this.level.getNumberOfMilksNeededToWin()) {
							this.level.setBeaten(true);
							AudioManager.SOUND.VICTORY.playSound();
							this.dairy_run.getGameStateManager().changeState(GameStateManager.PREVIOUS_STATE);
							this.state_is_transitioning = true;
						}
					}
				}

			} else {
				if (InputManager.pointersDown[0]) {
					if (this.allow_tap_to_start) {
						this.tapped_to_start = true;
					}
				} else {
					if (!this.allow_tap_to_start) {
						this.allow_tap_to_start = true;
					}
				}
			}
		}
	}

	public void renderGround() {
		for (GroundBlock gb : this.ground_blocks) {
			gb.render(this.sprite_batch, (int) this.current_scroll);
		}
	}

	public void renderBackground() {
		for (Background background : this.backgrounds) {
			background.render(this.sprite_batch, (int) this.current_scroll);
		}
	}

	@Override
	public void render() {

		// Background does not need to be transparent so blending is disabled for performance.
		this.sprite_batch.begin();
		this.sprite_batch.disableBlending();
		renderBackground();
		this.sprite_batch.enableBlending();
		// Render the trees.
		for (Tree tree : this.trees) {
			tree.render(this.sprite_batch, (int) this.current_scroll);
		}
		// Render the houses.
		for (House house : this.houses) {
			// Make sure the house is on-screen before rendering it.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				house.render(this.sprite_batch, house.getX() + (int) this.current_scroll);
			}
		}

		renderGround();

		// Render the buttons.
		if (this.level.isRunButtonEnabled()) {
			this.buttons[0].render(this.sprite_batch);
		}
		if (this.level.isRegularMilkButtonEnabled()) {
			if (!this.level.isRunButtonEnabled() && !this.level.isChocolateMilkButtonEnabled() && !this.level.isStrawberryMilkButtonEnabled()) {
				this.buttons[1].render(this.sprite_batch);
			} else {
				this.buttons[1].render(this.sprite_batch);
			}
		}
		if (this.level.isChocolateMilkButtonEnabled()) {
			this.buttons[2].render(this.sprite_batch);
		}
		if (this.level.isStrawberryMilkButtonEnabled()) {
			this.buttons[3].render(this.sprite_batch);
		}

		for (Chaser chaser : this.chasers) {
			chaser.render(this.sprite_batch, (int) this.current_scroll);
		}

		if (this.just_resumed) {
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, Color.RED, "TAP TO RESUME",
					ScreenUtil.screen_width / 2 - FontManager.FONT.PIXEL_REGULAR.getWidth("TAP TO RESUME") / 2, ScreenUtil.screen_height / 2
							- FontManager.FONT.PIXEL_REGULAR.getHeight("TAP TO RESUME") / 2);
		}

		StaticGUI.pause_button.render(this.sprite_batch, this.PAUSE_COLOR);

		if (!this.tapped_to_start) {
			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, Color.RED, "Tap To Begin",
					ScreenUtil.screen_width / 2 - FontManager.FONT.PIXEL_REGULAR.getWidth("Tap To Begin") / 2, ScreenUtil.screen_height / 2
							- FontManager.FONT.PIXEL_REGULAR.getHeight("Tap To Begin") / 2);
			// Render the player ready to sprint.
			Player.render(this.sprite_batch, this.player.getX(), this.ground_level, Map.size, Map.size, Player.READY_TO_SPRINT);
			this.player.renderPlayerStats(this.sprite_batch, (int) this.current_scroll);
		} else {
			// Render the player.
			this.player.render(this.sprite_batch, (int) this.current_scroll);
			this.player.renderPlayerStats(this.sprite_batch, (int) this.current_scroll);

			if (this.player.isScared()) {
				FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, "RUN!", Color.RED, ScreenUtil.screen_width / 2 - ScreenUtil.screen_width / 4,
						ScreenUtil.screen_width / 2 + ScreenUtil.screen_width / 4, ScreenUtil.screen_height / 2 - ScreenUtil.screen_height / 8,
						ScreenUtil.screen_height / 2 + ScreenUtil.screen_height / 8);
			}

			if (this.lost) {
				this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 12), Map.size * 1, Map.size * 1, ScreenUtil.screen_width
						- Map.size * 2, ScreenUtil.screen_height - Map.size * 2);

				this.game_over.render(this.sprite_batch, false);
				this.retry.render(this.sprite_batch, false);
				this.main_menu.render(this.sprite_batch, false);
				this.options.render(this.sprite_batch, FontManager.FONT.PIXEL_REGULAR.getFont(), false);
			} else if (this.pause_menu_open) {
				if (this.paused.isMouseOverMe()) {
					this.sprite_batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
				}
				this.sprite_batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 12), Map.size * 1, Map.size * 1, ScreenUtil.screen_width
						- Map.size * 2, ScreenUtil.screen_height - Map.size * 2);
				if (this.pause_menu_open) {
					this.paused.render(this.sprite_batch, this.sprite_batch.getColor());
					this.retry.render(this.sprite_batch, this.sprite_batch.getColor());
					this.main_menu.render(this.sprite_batch, this.sprite_batch.getColor());
					this.options.render(this.sprite_batch, this.sprite_batch.getColor());
				} else {
					this.paused.render(this.sprite_batch, false);
					this.retry.render(this.sprite_batch, false);
					this.main_menu.render(this.sprite_batch, false);
					this.options.render(this.sprite_batch, false);
				}
			}
		}
	}

	private void setLost(boolean b) {
		this.lost = false;
		AudioManager.SOUND.DEATH_ONE.setMuted(false);
		AudioManager.SOUND.DEATH_TWO.setMuted(false);
	}

	public void resetPositionsForAllEntities() {
		this.backgrounds[0].setX(0);
		this.backgrounds[1].setX(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth());
		this.buttons[0].reset();
		this.player.reset();
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i].setX((i + 1) * 10 * Map.size);
		}
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].setX(i * Map.size);
		}
		for (int i = 0; i < this.trees.length; i++) {
			this.trees[i].resetPosition((int) this.current_scroll);
		}
	}

	public void attemptToDeliver(short milk_type) {
		boolean milk_delivered = false;
		for (House house : this.houses) {
			// Only check the houses that are currently on screen.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				if (this.player.getX() + Map.size > house.getX() + this.current_scroll
						&& this.player.getX() < house.getX() + house.getWidth() * Map.size + this.current_scroll) {
					if (house.isMilkNeeded(milk_type) && house.needsMoreMilk()) {
						AudioManager.SOUND.COMPLETED.playSound();
						this.player.incrementNumberOfMilksDelivered();
						house.deliverMilk(milk_type);
						milk_delivered = true;
					}
				}
			}
		}
		if (!milk_delivered) {
			AudioManager.SOUND.COIN_ECHO.playSound();
			this.player.loseOneLife();
		}
	}

	public void reset() {
		this.current_scroll = 0;
		resetPositionsForAllEntities();
		this.tapped_to_start = false;
		this.chasers.clear();
		Chaser.number_of_chasers_created = 0;
		this.player.reset();
		this.velocity = this.level.getInitialVelocity();
	}

	public void lose() {
		this.lost = true;
		AudioManager.SOUND.DEATH_ONE.setMuted(true);
		AudioManager.SOUND.DEATH_TWO.setMuted(true);
	}

	public void retry() {
		setLost(false);
		this.tapped_to_start = false;
		if (InputManager.pointersDown[0]) {
			this.allow_tap_to_start = false;
		}
		Score.setCurrentMilkScore(this.player.getNumberOfMilksDelivered());
		Score.setCurrentVelocityScore(this.velocity);
		reset();
	}

	public void createChaser(short[] milks_not_delievered) {
		this.chasers.add(new Chaser(milks_not_delievered, this.current_scroll, (this.velocity + (this.velocity * 0.1f)), 60000, this));
	}

	@Override
	public void stateChangedToThis() {
		if (this.level == null) {
			this.level = Level.ENDLESS;
		}
		AudioManager.MUSIC.TEMP_MUSIC.loop(1.0f);
		this.state_is_transitioning = false; // TODO: Rename this lol
		if (this.game_in_session) {
			resume();
		}
	}

	@Override
	public void inStatePause() {
		this.pause_menu_open = true;
	}

	@Override
	public void pause() {
		this.just_resumed = false;
	}

	@Override
	public void resume() {
		this.just_resumed = true;
	}

	@Override
	public void dispose() {
		// Intentionally left blank.
	}

	public void setVelocity(float v) {
		this.velocity = v;
	}

	public float getVelocity() {
		return this.velocity;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Score getScore() {
		return this.score;
	}

	public ArrayList<Chaser> getChasers() {
		return this.chasers;
	}

	public Level getLevel() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public House[] getHouses() {
		return this.houses;
	}

	@Override
	public void stateFinishedFadingInToExit() {
		if (AudioManager.MUSIC.TEMP_MUSIC.isInitialized()) {
			if (AudioManager.MUSIC.TEMP_MUSIC.isPlaying()) {
				AudioManager.MUSIC.TEMP_MUSIC.stop();
			}
		}
		reset();
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