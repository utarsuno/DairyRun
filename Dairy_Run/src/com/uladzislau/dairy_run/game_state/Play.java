package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;

public class Play extends GameState {

	private Background[] backgrounds;
	private House[] houses;
	private GroundBlock[] ground_blocks;

	private int current_scroll;
	private float velocity = 5;
	private float acceleration;

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.state_id = DairyRun.PLAY;
		this.shapeRenderer = shapeRenderer;
		this.batch = batch;
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.SPRITESHEET.BACKGROUNDS.getWidth(), Background.BLUE);
		this.houses = new House[10];
		for (int i = 0; i < this.houses.length; i++) {
			this.houses[i] = new House(i * Map.size * 8 + Dice.get_Random_Integer_From_Min_To_Max(1, 4) * i * Map.size, Map.size);
		}
		this.ground_blocks = new GroundBlock[(int) (ScreenUtil.screen_width / Map.size) + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size);
		}
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
		TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.update(delta);
		TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.setFrameTime((int) (80 / (this.velocity / 5)));
		acceleration = 0.00002f * ScreenUtil.screen_width * delta * 1;
		this.velocity += this.acceleration;
		current_scroll -= this.velocity;

		// If the background has moved off the screen, shift it back into view.
		for (Background background : this.backgrounds) {
			if (background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() + this.current_scroll * Background.SCROLL_RATE < 0) {
				background.setX(background.getX() + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() * 2);
			}
		}

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
			if (gb.getX() + Map.size + this.current_scroll < 0) {
				gb.setX(gb.getX() + this.ground_blocks.length * Map.size);
			}
		}
		// System.out.println(Gdx.graphics.getFramesPerSecond());
		// System.out.println(this.velocity);
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

		// Render the houses.
		for (House house : this.houses) {
			// Make sure the house is on-screen before rendering.
			if (house.getX() + this.current_scroll < ScreenUtil.screen_width) {
				house.render(this.batch, house.getX() + this.current_scroll);
			}
		}

		// Render the player.
		this.batch.draw(TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrame(), Map.size * .5f, Map.size, Map.size, Map.size);

		if (ResourceManager.font_initialized) {
			if (FontManager.FONT.DEFAULT_FONT.isInitialized()) {
				FontManager.FONT.DEFAULT_FONT.setColor(0, 0, 0, 1);
				FontManager.FONT.DEFAULT_FONT.setXScale(1.25f);
				FontManager.FONT.DEFAULT_FONT.setYScale(3.0f);
				FontManager.FONT.DEFAULT_FONT.render(this.batch, "Sample text here " + this.velocity, 0,
						(int) (ScreenUtil.screen_height * .8));
			}
		}
		
		FontManager.FONT.TEST_FONT.get(50).draw(batch);

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