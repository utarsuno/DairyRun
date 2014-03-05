package com.uladzislau.dairy_run.game_state;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.Chaser;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.entity.Score;
import com.uladzislau.dairy_run.entity.Tree;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MilkButton;
import com.uladzislau.dairy_run.entity.button.RunButton;
import com.uladzislau.dairy_run.information.InfoUtil;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.math_utility.MathUtil;

public class Play extends GameState {

	private Background[] backgrounds;
	private House[] houses;
	private GroundBlock[] ground_blocks;
	private Tree[] trees;
	private Player player;

	// TOOD: Change it in the future to only create new ones when needed.
	private ArrayList<Chaser> chasers;

	private CircleButton[] buttons;

	private int current_scroll;
	private float velocity = 8; // 8
	private float acceleration;

	public int ground_level;

	private Score score;

	private DeltaTimer resumeTimer;

	private boolean game_in_session = false;

	public Play(DairyRun dairy_run, byte id) {
		super(dairy_run, id);
	}

	@Override
	public void initialize(ShapeRenderer sr, SpriteBatch sb) {

		// Used for rendering.
		this.shape_renderer = sr;
		this.sprite_batch = sb;
		// Set the ground level.
		this.ground_level = (int) (Map.size * 1.5);
		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), Background.BLUE);
		// Create the houses.
		this.houses = new House[10];
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i] = new House(i * 10 * Map.size, this.ground_level, this.houses.length, this);
		}
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[ScreenUtil.screen_width / Map.size + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, this.ground_level, this.ground_blocks.length);
		}
		// Create the trees.
		this.trees = new Tree[30];
		for (int i = 0; i < this.trees.length; i++) {
			this.trees[i] = new Tree(this.ground_level, 30);
		}
		// Create the buttons.
		this.buttons = new CircleButton[4];
		this.buttons[0] = new RunButton((ScreenUtil.screen_width / 20) + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, this);
		this.buttons[1] = new MilkButton((ScreenUtil.screen_width / 20) * 3 + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f,
				MilkButton.REGULAR, this);
		this.buttons[2] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) * 3 - Map.size / 2, Map.size / 8
				+ Map.size / 2, Map.size * 0.6f, MilkButton.CHOCOLATE, this);
		this.buttons[3] = new MilkButton((ScreenUtil.screen_width) - (ScreenUtil.screen_width / 20) - Map.size / 2, Map.size / 8 + Map.size
				/ 2, Map.size * 0.6f, MilkButton.STRAWBERRY, this);
		// Create the player.
		this.player = new Player((int) (Map.size * 1.5f), this.ground_level, this);
		this.score = new Score();
		this.resumeTimer = new DeltaTimer();
		this.chasers = new ArrayList<Chaser>();
	}

	boolean play_sound = true;
	private boolean song_started = false;

	private boolean just_resumed = false;

	private boolean pc_mouse_down_on_first_resume_update = false;
	private boolean allow_resume = true;
	private boolean reset = false;

	@Override
	public void update(float delta) {
		if (!this.song_started) {
			if (this.dairy_run.getResourceManager().music_initialized) {
				if (AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.isPlaying()) {
					AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.stop();
				}
				AudioManager.MUSIC.TEMP_MUSIC.play(.20f);
				this.song_started = true;
			}
		}
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
				this.reset = true;
			}
		} else {
			if (this.reset) {
				this.pc_mouse_down_on_first_resume_update = false;
				this.allow_resume = true;
				this.resumeTimer.reset();
				this.reset = false;
			}

			this.player.update(delta, this.current_scroll);

			this.acceleration = 0.00002f * ScreenUtil.screen_width * delta * 1;
			this.velocity += this.acceleration;
			this.current_scroll -= this.velocity;

			// If the background has moved off the screen, shift it back into view.
			for (Background background : this.backgrounds) {
				if (background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() + this.current_scroll * Background.SCROLL_RATE < 0) {
					background.setX(background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() * 2);
				}
			}

			for (House house : this.houses) {
				house.update(delta, this.current_scroll);
			}

			// If the ground block has moved off the screen, shift it back into view.
			for (GroundBlock gb : this.ground_blocks) {
				gb.update(this.current_scroll);
			}

			for (Tree tree : this.trees) {
				tree.update(this.current_scroll);
			}
			// System.out.println(Gdx.graphics.getFramesPerSecond());
			// System.out.println(this.velocity);

			// Check for button press. If pressed they will do their respective actions.
			for (CircleButton button : this.buttons) {
				button.update(delta);
			}

			for (Chaser chaser : this.chasers) {
				chaser.update(delta);
			}

		}

	}

	@Override
	public void render() {

		this.sprite_batch.disableBlending();
		this.sprite_batch.begin();
		// Render the background.
		for (Background background : this.backgrounds) {
			// Make sure background is on-screen before rendering.
			if (background.getX() + this.current_scroll * Background.SCROLL_RATE < ScreenUtil.screen_width) {
				background.render(this.sprite_batch, (int) (background.getX() + this.current_scroll * Background.SCROLL_RATE));
			}
		}
		// Render the ground.
		for (GroundBlock gb : this.ground_blocks) {
			// Every block is visible majority of the time, thus there is no need to check if it is off-screen.
			gb.render(this.sprite_batch, gb.getX() + this.current_scroll);
		}
		this.sprite_batch.end();

		this.sprite_batch.enableBlending();
		this.sprite_batch.begin();

		// Render the trees.
		for (Tree tree : this.trees) {
			tree.render(this.sprite_batch, this.current_scroll);
		}
		// Render the houses.
		for (House house : this.houses) {
			// Make sure the house is on-screen before rendering it.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				house.render(this.sprite_batch, house.getX() + this.current_scroll);
			}
		}

		// Render the buttons.
		for (CircleButton button : this.buttons) {
			button.render(this.sprite_batch);
		}

		// TODO: move the velocity and milk rendering.
		// Render the player's velocity.
		FontManager.FONT.BLOCK_FONT.render(this.sprite_batch, Color.BLACK, "" + MathUtil.round(this.velocity), 0, ScreenUtil.screen_height);

		FontManager.FONT.BLOCK_FONT.render(this.sprite_batch, "" + this.player.getNumberOfMilksDelivered(), 0, ScreenUtil.screen_height
				- Map.size);

		// Render the player.
		this.player.render(this.sprite_batch, this.current_scroll);

		for (Chaser chaser : this.chasers) {
			chaser.render(this.sprite_batch, this.current_scroll);
		}

		if (this.just_resumed) {
			FontManager.FONT.BLOCK_FONT.render(this.sprite_batch, Color.RED, "TAP TO RESUME", ScreenUtil.screen_width / 2
					- FontManager.FONT.BLOCK_FONT.getWidth("TAP TO RESUME") / 2,
					ScreenUtil.screen_height / 2 - FontManager.FONT.BLOCK_FONT.getHeight("TAP TO RESUME") / 2);
		}

		this.sprite_batch.end();
	}

	public void resetPositionsForAllEntities() {
		this.backgrounds[0].setX(0);
		this.backgrounds[1].setX(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth());
		this.buttons[0].reset();
		this.player.reset();
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i].setX(i * 10 * Map.size);
		}
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].setX(i * Map.size);
		}
		for (int i = 0; i < this.trees.length; i++) {
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
		this.player.reset();
		this.velocity = 8;
	}

	public void lose() {
		Score.setCurrentMilkScore(this.player.getNumberOfMilksDelivered());
		Score.setCurrentVelocityScore(this.velocity);
		reset();
		this.dairy_run.changeState(DairyRun.MAIN_MENU);
	}

	public void createChaser(short[] milks_not_delievered) {
		this.chasers.add(new Chaser(milks_not_delievered, this.current_scroll, (this.velocity + (this.velocity * 0.225f)), 60000, this));
	}

	@Override
	public void stateChangedToThis() {
		if (this.game_in_session) {
			resume();
		}
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

}