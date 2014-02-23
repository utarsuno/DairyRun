package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.entity.Player;
import com.uladzislau.dairy_run.entity.Tree;
import com.uladzislau.dairy_run.entity.button.Button;
import com.uladzislau.dairy_run.entity.button.JumpButton;
import com.uladzislau.dairy_run.entity.button.RunButton;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math_utility.MathUtil;

public class Play extends GameState {

	private Background[] backgrounds;
	private House[] houses;
	private GroundBlock[] ground_blocks;
	private Tree[] trees;
	private Player player;

	private Button[] buttons;

	private int current_scroll;
	private float velocity = 3;
	private float acceleration;

	public int ground_level;

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.state_id = DairyRun.PLAY;
		this.shapeRenderer = shapeRenderer;
		this.batch = batch;
		this.ground_level = (int) (Map.size * 1.5);
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), Background.BLUE);
		this.houses = new House[10];
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i] = new House(i * Map.size * 8 + Dice.get_Random_Integer_From_Min_To_Max(1, 4) * i * Map.size, ground_level);
		}
		this.ground_blocks = new GroundBlock[(int) (ScreenUtil.screen_width / Map.size) + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, ground_level, this.ground_blocks.length);
		}
		this.trees = new Tree[30];
		for (int i = 0; i < this.trees.length; i++) {
			this.trees[i] = new Tree(this.ground_level);
		}
		this.buttons = new Button[5];
		this.buttons[0] = new RunButton((ScreenUtil.screen_width / 20) + Map.size / 2, Map.size / 8 + Map.size / 2, Map.size * 0.6f, this);
		this.buttons[1] = new JumpButton((ScreenUtil.screen_width / 5) * 4 + (ScreenUtil.screen_width / 20) + Map.size / 2, Map.size / 8
				+ Map.size / 2, Map.size * 0.6f, this);
		this.player = new Player((int) (Map.size * 1.5f), this.ground_level, this);
	}

	boolean play_sound = true;
	private boolean sound_played = false;

	@Override
	public void update(float delta) {
		if (this.first_update) {
			this.first_update = false;
		}
		if (ResourceManager.audio_initialized && !sound_played) {
			AudioManager.MUSIC.TEMP_MUSIC.play(.5f);
			sound_played = true;
		}

		this.player.update(delta);

		acceleration = 0.00002f * ScreenUtil.screen_width * delta * 1;
		this.velocity += this.acceleration;
		current_scroll -= this.velocity;

		// If the background has moved off the screen, shift it back into view.
		for (Background background : this.backgrounds) {
			if (background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() + this.current_scroll * Background.SCROLL_RATE < 0) {
				background.setX(background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() * 2);
			}
		}

		// TODO: move all this to houses[i].update
		for (int i = 0; i < this.houses.length; i++) {
			if (houses[i].getX() + ((houses[i].getWidth() + 1) * Map.size) + this.current_scroll < 0) {
				this.houses[i].randomize();
				this.houses[i]
						.setX(this.houses[i].getX() + Dice.get_Random_Integer_From_Min_To_Max(10, 30) * Map.size + 10 * 20 * Map.size);
			}
			this.houses[i].update(this.current_scroll);
		}
		boolean mouseClickedOnHouse = false;
		if (InputManager.pointersDown[0]) {
			if (play_sound) {
				for (int i = 0; i < this.houses.length; i++) {
					if (this.houses[i].isMouseDownOnMe()) {
						mouseClickedOnHouse = true;
					}
				}
				if (!mouseClickedOnHouse) {
					AudioManager.SOUND.COIN_ECHO.playSound();
				} else {
					AudioManager.SOUND.COMPLETED.playSound();
				}
				this.play_sound = false;
			}
		} else {
			this.play_sound = true;
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
		this.buttons[0].update(delta);
		this.buttons[1].update(delta);
	}

	@Override
	public void render() {

		this.batch.begin();

		// Render the background.
		for (Background background : this.backgrounds) {
			// Make sure background is on-screen before rendering.
			if (background.getX() + this.current_scroll * Background.SCROLL_RATE < ScreenUtil.screen_width) {
				background.render(this.batch, (int) (background.getX() + this.current_scroll * Background.SCROLL_RATE));
			}
		}

		// Render the ground.
		for (GroundBlock gb : this.ground_blocks) {
			// Every block is visible majority of the time, thus there is no need to check if off-screen.
			gb.render(this.batch, gb.getX() + this.current_scroll);
		}

		// Render the trees.
		for (Tree tree : this.trees) {
			tree.render(this.batch, this.current_scroll);
		}

		// Render the houses.
		for (House house : this.houses) {
			// Make sure the house is on-screen before rendering it.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				house.render(this.batch, house.getX() + this.current_scroll);
			}
		}

		// Render the buttons.s
		this.buttons[0].render(this.batch);
		this.buttons[1].render(this.batch);
		this.batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 20), (ScreenUtil.screen_width / 5) * 1
				+ (ScreenUtil.screen_width / 20), Map.size / 8, Map.size, Map.size);
		this.batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 21), (ScreenUtil.screen_width / 5) * 2
				+ (ScreenUtil.screen_width / 20), Map.size / 8, Map.size, Map.size);
		this.batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 6 + 22), (ScreenUtil.screen_width / 5) * 3
				+ (ScreenUtil.screen_width / 20), Map.size / 8, Map.size, Map.size);

		FontManager.FONT.DEFAULT_FONT.render(this.batch, "" + MathUtil.round(this.velocity), 0, ScreenUtil.screen_height - Map.size,
				Map.size);

		// Render the player.
		this.player.render(this.batch, this.current_scroll);

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

	public void setVelocity(float v) {
		this.velocity = v;
	}

	public float getVelocity() {
		return this.velocity;
	}

	public Player getPlayer() {
		return this.player;
	}

}