package com.uladzislau.dairy_run.game_state;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.Chaser;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.entity.Tree;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MilkButton;
import com.uladzislau.dairy_run.entity.button.PowerUpButton;
import com.uladzislau.dairy_run.entity.button.PowerUpButton.Power;
import com.uladzislau.dairy_run.entity.button.RunButton;
import com.uladzislau.dairy_run.gui.AnimatedText;
import com.uladzislau.dairy_run.gui.ClickableText;
import com.uladzislau.dairy_run.gui.Heart;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.gui.AnimatedText.AnimatedTextType;
import com.uladzislau.dairy_run.information.Score;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.math_utility.MathUtil;
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
	private PowerUpButton[] power_up_buttons;

	private float current_scroll;
	private float velocity;
	private float acceleration;

	public int ground_level;

	private boolean lost = false;
	private boolean allow_tap_to_start = true;

	private DeltaTimer resumeTimer;

	private boolean game_in_session = false;

	private boolean pause_menu_open = false;

	private ClickableText paused;
	private ClickableText game_over;
	private ClickableText retry;
	private ClickableText options;
	private ClickableText main_menu;

	private Level level;

	private int current_streak;
	private int max_streak;
	private int power_up_one_counter;
	private int power_up_two_counter;
	private int power_up_three_counter;

	private boolean just_resumed = false;

	private boolean mouse_down_on_first_resume_update = false;
	private boolean allow_resume = true;
	private boolean reset = false;

	private boolean tapped_to_start = false;

	private boolean state_is_transitioning = false;

	private boolean ignoreMilkButtonPresses = false;

	private DeltaTimer play_timer;

	private AnimatedText plus_one_life_animated_text;

	public Play(DairyRun dairy_run, GameStateManager.STATE state) {
		super(dairy_run, state);
	}

	@Override
	public void initialize() {
		// Set the ground level.
		this.ground_level = (int) (Map.size * 1.5);
		Map.setGroundLevel(this.ground_level);
		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.Spritesheet.BACKGROUNDS.getWidth(), 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height,
				Background.BLUE);
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[ScreenUtil.screen_width / Map.size + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, this.ground_level, Map.size, Map.size, this.ground_blocks.length, true, this.level.getGroundBlockTheme());
		}
		// Create the houses.
		this.houses = new House[15];
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i] = new House(this);
			this.houses[i].createHouse(i);
		}
		this.velocity = this.level.getInitialVelocity();

		// Create the trees.
		this.trees = new Tree[30];
		for (int i = 0; i < this.trees.length; i++) {
			this.trees[i] = new Tree(this.ground_level, i, this.trees.length);
			this.trees[i].resetPosition((int) this.current_scroll);
		}

		// TODO: Create 11 buttons and simply update / display the ones needed.
		// Create the buttons.
		this.buttons = new CircleButton[4];
		this.buttons[0] = new RunButton((ScreenUtil.screen_width / 20) + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, this);
		this.buttons[1] = new MilkButton((ScreenUtil.screen_width / 20) * 3 + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, TextureManager.REGULAR, this);
		this.buttons[2] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) * 3 - Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f,
				TextureManager.CHOCOLATE, this);
		this.buttons[3] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) - Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, TextureManager.STRAWBERRY,
				this);

		this.power_up_buttons = new PowerUpButton[3];
		this.power_up_buttons[0] = new PowerUpButton(ScreenUtil.screen_width / 2 - Map.size, Map.size / 8 + Map.size / 2, Map.size * 0.6f, Power.TIME_SLOW, ResourceManager.getSpriteBatch(),
				this);
		this.power_up_buttons[1] = new PowerUpButton(ScreenUtil.screen_width / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, Power.SCREEN_CLEAR, ResourceManager.getSpriteBatch(), this);
		this.power_up_buttons[2] = new PowerUpButton(ScreenUtil.screen_width / 2 + Map.size, Map.size / 8 + Map.size / 2, Map.size * 0.6f, Power.NUCLEAR, ResourceManager.getSpriteBatch(),
				this);
		// Create the player.
		this.player = new Player((int) (Map.size * 2.5f), this.ground_level, this);
		this.resumeTimer = new DeltaTimer();
		this.chasers = new ArrayList<Chaser>();

		this.paused = new ClickableText("Resume", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "Resume".length()) / 2, Map.size * 5.4f, //$NON-NLS-1$ //$NON-NLS-2$
				"Resume".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800); //$NON-NLS-1$

		this.game_over = new ClickableText("Game Over", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "Game Over".length()) / 2, Map.size * 5.4f, //$NON-NLS-1$ //$NON-NLS-2$
				"Game Over".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800); //$NON-NLS-1$

		this.retry = new ClickableText("retry", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "retry".length()) / 2, Map.size * 4.2f, //$NON-NLS-1$ //$NON-NLS-2$
				"retry".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800); //$NON-NLS-1$

		this.options = new ClickableText("options", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "options".length()) / 2, Map.size * 3, //$NON-NLS-1$ //$NON-NLS-2$
				"options".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800); //$NON-NLS-1$

		this.main_menu = new ClickableText("main menu", new Rectanglei(ScreenUtil.screen_width / 2 - (Map.size * "main_menu".length()) / 2, Map.size * 1.8f, //$NON-NLS-1$ //$NON-NLS-2$
				"main_menu".length() * Map.size, Map.size), ColorXv.BLACK, ColorXv.WHITE, 800); //$NON-NLS-1$

		this.play_timer = new DeltaTimer();

		this.plus_one_life_animated_text = new AnimatedText(ScreenUtil.screen_width / 2, ScreenUtil.screen_height / 2 - Map.size / 2, ScreenUtil.screen_height / 2 + Map.size / 2,
				"+1 life".length() * Map.size, Map.size, "+1 life", new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 0.0f), //$NON-NLS-1$ //$NON-NLS-2$
				2000, AnimatedTextType.FADE_UP);
		this.plus_one_life_animated_text.end();
	}

	@Override
	public void update(float delta) {
		if (this.just_resumed) {
			if (this.resumeTimer.getTotalDelta() == 0) {
				if (InputManager.pointersDown[0]) {
					this.mouse_down_on_first_resume_update = true;
					this.allow_resume = false;
				}
			}
			if (this.mouse_down_on_first_resume_update) {
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
		} else {
			if (this.reset) {
				this.mouse_down_on_first_resume_update = false;
				this.allow_resume = true;
				this.resumeTimer.reset();
				this.reset = false;
			}

			if (this.tapped_to_start) {
				if (!this.pause_menu_open) {

					/* Game is fully playing here. */

					if (!GameStateManager.transitioning_states && !this.lost) {
						this.play_timer.update(delta);
					}

					this.player.update(delta);

					if (!this.lost) {
						this.acceleration = 0.00006f * ScreenUtil.screen_width * delta * 1;
						this.velocity += this.acceleration;
					}
					this.current_scroll -= this.velocity;
					Map.setCurrentScroll(this.current_scroll);

					// If the background has moved off the screen, shift it back into view.
					for (Background background : this.backgrounds) {
						background.update((int) this.current_scroll);
					}

					// If the house has moved off the screen, randomize it and shift it back into view.
					for (House house : this.houses) {
						house.update(delta);
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

					this.plus_one_life_animated_text.update(delta);

					for (int i = 0; i < this.power_up_buttons.length; i++) {
						this.power_up_buttons[i].update(delta);
					}

				} else {
					this.paused.update(delta);
					this.retry.update(delta);
					this.main_menu.update(delta);
					this.options.update(delta);

					if (!InputManager.pointersDragging[0]) {
						if (this.paused.isMouseDownOnMe()) {
							this.pause_menu_open = false;
							this.just_resumed = true;
						}
						if (this.main_menu.isMouseDownOnMe()) {
							this.just_resumed = true;
							this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.MAIN_MENU);
							this.dairy_run.getGameStateManager().clearHistoryStates();
							setLost(false);
							this.game_in_session = true;
							this.pause_menu_open = false;
						}
						if (this.retry.isMouseDownOnMe()) {
							retry();
							this.pause_menu_open = false;
						}
						if (this.options.isMouseDownOnMe()) {
							this.just_resumed = true;
							this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.OPTIONS);
							this.game_in_session = true;
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
							this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.MAIN_MENU);
							this.dairy_run.getGameStateManager().clearHistoryStates();
							setLost(false);
						}
						if (this.retry.isMouseDownOnMe()) {
							retry();
						}
						if (this.options.isMouseDownOnMe()) {
							this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.OPTIONS);
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
							AudioManager.SoundXv.VICTORY.playSound();
							this.dairy_run.getGameStateManager().changeState(GameStateManager.STATE.PREVIOUS_STATE);
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
			gb.render();
		}
	}

	public void renderBackground() {
		for (Background background : this.backgrounds) {
			background.render();
		}
	}

	private boolean render_specific_time = false;
	private int time_to_render;
	private Color color_to_render_time_in;

	@Override
	public void render() {

		// Background does not need to be transparent so blending is disabled for performance.
		ResourceManager.getSpriteBatch().disableBlending();
		renderBackground();
		ResourceManager.getSpriteBatch().enableBlending();
		// Render the trees.
		for (Tree tree : this.trees) {
			tree.render(ResourceManager.getSpriteBatch(), (int) this.current_scroll);
		}
		// Render the houses.
		for (House house : this.houses) {
			// Make sure the house is on-screen before rendering it.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				house.render();
			}
		}

		renderGround();

		// Render the buttons.
		if (this.level.isRunButtonEnabled()) {
			this.buttons[0].render();
		}
		if (this.level.isRegularMilkButtonEnabled()) {
			if (!this.level.isRunButtonEnabled() && !this.level.isChocolateMilkButtonEnabled() && !this.level.isStrawberryMilkButtonEnabled()) {
				this.buttons[1].render();
			} else {
				this.buttons[1].render();
			}
		}
		if (this.level.isChocolateMilkButtonEnabled()) {
			this.buttons[2].render();
		}
		if (this.level.isStrawberryMilkButtonEnabled()) {
			this.buttons[3].render();
		}

		for (int i = 0; i < this.power_up_buttons.length; i++) {
			this.power_up_buttons[i].render();
		}

		this.plus_one_life_animated_text.render();

		for (Chaser chaser : this.chasers) {
			chaser.render(ResourceManager.getSpriteBatch(), (int) this.current_scroll);
		}

		if (this.just_resumed) {
			StaticGUI.renderBlackMessage("TAP TO RESUME"); //$NON-NLS-1$
		}

		// Render the time playing the current level here.
		if (!this.render_specific_time) {
			FontManager.Font.PIXEL_REGULAR.render(this.level.getScore().convertTimeInSecondsToClockTime((int) ((this.play_timer.getTotalDelta() / 1000.0f))), Color.BLACK,
					ScreenUtil.screen_width / 2, ScreenUtil.screen_height - (int) (Map.size * 0.9f), (int) (Map.size * 0.8f), true, -1);
		} else {
			FontManager.Font.PIXEL_REGULAR.render("" + this.time_to_render, this.color_to_render_time_in, //$NON-NLS-1$
					ScreenUtil.screen_width / 2, ScreenUtil.screen_height - (int) (Map.size * 0.9f), (int) (Map.size * 0.8f), true, -1);
		}

		// Render the current velocity.
		if (this.velocity > this.level.getScore().getVelocityHighScore()) {
			FontManager.Font.PIXEL_REGULAR.render("" + MathUtil.round(this.velocity, 2), ColorXv.NEW_HIGH_SCORE_COLOR, //$NON-NLS-1$
					(int) (Map.size * 0.1f), (int) (ScreenUtil.screen_height - Map.size * 0.9f), Map.size * 0.8f, false, -1);
		} else {
			FontManager.Font.PIXEL_REGULAR.render("" + MathUtil.round(this.velocity, 2), ColorXv.BLACK, //$NON-NLS-1$
					(int) (Map.size * 0.1f), (int) (ScreenUtil.screen_height - Map.size * 0.9f), Map.size * 0.8f, false, -1);
		}

		// Render the current number of milks delivered.
		if (this.player.getNumberOfMilksDelivered() > this.level.getScore().getMilkHighScore()) {
			FontManager.Font.PIXEL_REGULAR.render("" + this.player.getNumberOfMilksDelivered(), ColorXv.NEW_HIGH_SCORE_COLOR, //$NON-NLS-1$
					(int) (Map.size * 0.1f), (int) (ScreenUtil.screen_height - Map.size * 1.9f), Map.size * 0.8f, false, -1);
		} else {
			FontManager.Font.PIXEL_REGULAR.render("" + this.player.getNumberOfMilksDelivered(), ColorXv.BLACK, //$NON-NLS-1$
					(int) (Map.size * 0.1f), (int) (ScreenUtil.screen_height - Map.size * 1.9f), Map.size * 0.8f, false, -1);
		}

		// Render the current streak.
		if (this.current_streak > this.level.getScore().getHighestStreak()) {
			FontManager.Font.PIXEL_REGULAR.render("" + this.player.getNumberOfMilksDelivered(), ColorXv.NEW_HIGH_SCORE_COLOR, //$NON-NLS-1$
					(int) (Map.size * 0.06f), (int) (ScreenUtil.screen_height - Map.size * 2.4f), (int) (Map.size * 0.4f), false, -1);
		} else {
			FontManager.Font.PIXEL_REGULAR.render("" + this.player.getNumberOfMilksDelivered(), ColorXv.NEW_HIGH_SCORE_COLOR, //$NON-NLS-1$
					(int) (Map.size * 0.06f), (int) (ScreenUtil.screen_height - Map.size * 2.4f), (int) (Map.size * 0.4f), false, -1);
		}

		// Render the player's health at the top right of the screen.
		Heart.render(ScreenUtil.screen_width - Map.size * 3, ScreenUtil.screen_height - Map.size, this.player.getLife());

		StaticGUI.pause_button.render(this.PAUSE_COLOR);

		if (!this.tapped_to_start) {

			StaticGUI.renderBlackMessage("TAP TO BEGIN"); //$NON-NLS-1$

			// Render the player ready to sprint.
			Player.render(ResourceManager.getSpriteBatch(), this.player.getX(), this.ground_level, Map.size, Map.size, Player.READY_TO_SPRINT);
		} else {
			// Render the player.
			this.player.render(ResourceManager.getSpriteBatch());

			if (this.lost) {

				StaticGUI.fillScreenWithColor(0.0f, 0.0f, 0.0f, 0.3333333333f);

				ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 12), Map.size * 1, Map.size * 1, ScreenUtil.screen_width - Map.size * 2,
						ScreenUtil.screen_height - Map.size * 2);

				this.game_over.render(ResourceManager.getSpriteBatch(), false);
				this.retry.render(ResourceManager.getSpriteBatch(), false);
				this.main_menu.render(ResourceManager.getSpriteBatch(), false);
				this.options.render(ResourceManager.getSpriteBatch(), FontManager.Font.PIXEL_REGULAR.getFont(), false);
			} else if (this.pause_menu_open) {

				StaticGUI.fillScreenWithColor(0.0f, 0.0f, 0.0f, 0.3333333333f);

				ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 6 + 12), Map.size * 1, Map.size * 1, ScreenUtil.screen_width - Map.size * 2,
						ScreenUtil.screen_height - Map.size * 2);
				if (this.pause_menu_open) {
					this.paused.render(ResourceManager.getSpriteBatch(), ResourceManager.getSpriteBatch().getColor());
					this.retry.render(ResourceManager.getSpriteBatch(), ResourceManager.getSpriteBatch().getColor());
					this.main_menu.render(ResourceManager.getSpriteBatch(), ResourceManager.getSpriteBatch().getColor());
					this.options.render(ResourceManager.getSpriteBatch(), ResourceManager.getSpriteBatch().getColor());
				} else {
					this.paused.render(ResourceManager.getSpriteBatch(), false);
					this.retry.render(ResourceManager.getSpriteBatch(), false);
					this.main_menu.render(ResourceManager.getSpriteBatch(), false);
					this.options.render(ResourceManager.getSpriteBatch(), false);
				}
			}
		}
	}

	private void setLost(boolean b) {
		this.lost = b;
		// TODO: Change this.
		AudioManager.SoundXv.DEATH_ONE.setMuted(false);
		AudioManager.SoundXv.DEATH_TWO.setMuted(false);
	}

	public void resetPositionsForAllEntities() {
		this.backgrounds[0].setX(0);
		this.backgrounds[1].setX(TextureManager.Spritesheet.BACKGROUNDS.getWidth());
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

	private boolean life_already_gained[] = new boolean[10];

	public void attemptToDeliver(short milk_type) {
		boolean milk_delivered = false;
		for (House house : this.houses) {
			// Only check the houses that are currently on screen.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				if (this.player.getX() + Map.size > house.getX() + this.current_scroll && this.player.getX() < house.getX() + house.getWidth() * Map.size + this.current_scroll) {
					if (house.isMilkNeeded(milk_type) && house.needsMoreMilk()) {
						AudioManager.SoundXv.COMPLETED.playSound();
						this.player.incrementNumberOfMilksDelivered();
						this.current_streak++;
						if (this.current_streak > this.max_streak) {
							this.max_streak = this.current_streak;
						}
						this.power_up_one_counter++;
						if (this.level.getNumberOfConsecutiveMilksNeededToGainFirstPowerUp() == this.power_up_one_counter) {
							this.power_up_buttons[0].incrementStock();
							this.power_up_one_counter = 0;
						}
						this.power_up_two_counter++;
						if (this.level.getNumberOfConsecutiveMilksNeededToGainSecondPowerUp() == this.power_up_two_counter) {
							this.power_up_buttons[1].incrementStock();
							this.power_up_two_counter = 0;
						}
						this.power_up_three_counter++;
						if (this.level.getNumberOfConsecutiveMilksNeededToGainThirdPowerUp() == this.power_up_three_counter) {
							this.power_up_buttons[2].incrementStock();
							this.power_up_three_counter = 0;
						}
						for (int i = 0; i < this.life_already_gained.length; i++) {
							if (this.player.getNumberOfMilksDelivered() == this.level.getScoresNeededToGainOneLife()[i]) {
								if (!this.life_already_gained[i]) {
									this.player.setLife((byte) (this.player.getLife() + 1));
									this.plus_one_life_animated_text.play();
									this.life_already_gained[i] = true;
								}
							}
						}
						house.deliverMilk(milk_type);
						milk_delivered = true;
					}
				}
			}
		}
		if (!milk_delivered) {
			AudioManager.SoundXv.COIN_ECHO.playSound();
			this.player.loseOneLife();
			if (this.current_streak > this.max_streak) {
				this.max_streak = this.current_streak;
			}
			this.power_up_one_counter = 0;
			this.power_up_two_counter = 0;
			this.power_up_three_counter = 0;
			this.current_streak = 0;
		}
	}

	public void lose() {
		this.lost = true;
		// TODO: Don't toggle mute/un-mute, just don't play the sound...lol
		AudioManager.SoundXv.DEATH_ONE.setMuted(true);
		AudioManager.SoundXv.DEATH_TWO.setMuted(true);

		this.level.getScore().setCurrentMilkScore(this.player.getNumberOfMilksDelivered());
		this.level.getScore().setCurrentVelocityScore(this.velocity);
		this.level.getScore().setCurrentStreak(this.current_streak);
	}

	public void retry() {
		setLost(false);
		this.tapped_to_start = false;
		this.play_timer.reset();
		if (InputManager.pointersDown[0]) {
			this.allow_tap_to_start = false;
		}
		this.level.getScore().setCurrentMilkScore(this.player.getNumberOfMilksDelivered());
		this.level.getScore().setCurrentVelocityScore(this.velocity);
		this.level.getScore().setCurrentStreak(this.current_streak);

		System.out.println(this.velocity);

		// rfhgfh

		reset();
		Map.setCurrentScroll(0);
	}

	public void reset() {
		this.current_scroll = 0;
		resetPositionsForAllEntities();
		this.tapped_to_start = false;
		this.chasers.clear();
		Chaser.number_of_chasers_created = 0;
		this.player.reset();
		this.velocity = this.level.getInitialVelocity();
		for (int i = 0; i < this.life_already_gained.length; i++) {
			this.life_already_gained[i] = false;
		}
		this.power_up_one_counter = 0;
		this.power_up_two_counter = 0;
		this.power_up_three_counter = 0;
		this.power_up_buttons[0].reset();
		this.power_up_buttons[1].reset();
		this.power_up_buttons[2].reset();
		this.current_streak = 0;
	}

	public void createChaser(short[] milks_not_delievered) {
		this.chasers.add(new Chaser(milks_not_delievered, this.current_scroll, (this.velocity + (this.velocity * 0.1f)), 60000, this));
	}

	@Override
	public void stateChangedToThis() {
		Map.setCurrentScroll(this.current_scroll);
		this.state_is_transitioning = false; // TODO: Rename this lol
		if (this.game_in_session) {
			resume();
		} else {
			this.play_timer.reset();
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

	public void setTimeToRender(int t) {
		this.time_to_render = t;
	}

	public void setColorToRenderTimeIn(Color color) {
		this.color_to_render_time_in = color;
	}

	@Override
	public void stateFinishedFadingInToExit() {
		if (!this.game_in_session) {
			reset();
		}
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingOut() {
		// Intentionally left blank.
	}

	public void setCustomTimer(boolean b) {
		this.render_specific_time = b;
	}

	public void clearNextHouses(int number_of_houses_to_clear) {
		int cleared = 0;
		dance: for (int j = 0; j < this.houses.length; j++) {
			if (this.player.getX() + Map.size < this.houses[j].getX() + this.current_scroll) {
				while (this.houses[j].needsMoreMilk()) {
					this.houses[j].deliverMilk();
					this.player.incrementNumberOfMilksDelivered();
					this.current_streak++;
				}
				cleared++;
				if (cleared == number_of_houses_to_clear) {
					break dance;
				}
			}
		}
	}

	public void setIgnoreMilkButtonPresses(boolean b) {
		this.ignoreMilkButtonPresses = b;
	}

}