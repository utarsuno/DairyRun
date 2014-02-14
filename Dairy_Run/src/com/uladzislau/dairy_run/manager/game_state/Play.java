package com.uladzislau.dairy_run.manager.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.manager.entity.House;

public class Play extends GameState {

	private int scroll;
	private int current_scroll;
	private float velocity = 2;
	private float acceleration;

	private int number_of_vertical_blocks = 7;

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.state_id = DairyRun.PLAY;
		this.shapeRenderer = shapeRenderer;
		this.batch = batch;
	}

	boolean play_sound = true;

	@Override
	public void update(float delta) {
		if (this.first_update) {
			TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.init();
			TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.setHeight((int) (ScreenUtil.screen_height / number_of_vertical_blocks));
			TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.setWidth(TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getHeight());
			TextureManager.TEXTURE.COLOR_208_244_247.init();
			TextureManager.TEXTURE.COLOR_208_244_247.setWidth((int) ScreenUtil.screen_width);
			TextureManager.TEXTURE.COLOR_208_244_247.setHeight((int) ScreenUtil.screen_height);
			TextureManager.ANIMATION_SPRITESHEET.PLAYER_WALKING.init();
			TextureManager.ANIMATION_SPRITESHEET.PLAYER_WALKING.setHeight((int) (ScreenUtil.screen_height / number_of_vertical_blocks));
			TextureManager.ANIMATION_SPRITESHEET.PLAYER_WALKING.setWidth(TextureManager.ANIMATION_SPRITESHEET.PLAYER_WALKING.getHeight());
			TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.init();
			TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setHeight((int) (ScreenUtil.screen_height / number_of_vertical_blocks));
			TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.setWidth(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());
			TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.init();
			TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.setHeight((int) (ScreenUtil.screen_height / number_of_vertical_blocks));
			TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.setWidth(TextureManager.ANIMATION_SPRITESHEET.PLAYER_WALKING.getHeight());
			TextureManager.SPRITESHEET.BACKGROUNDS.init();
			TextureManager.SPRITESHEET.BACKGROUNDS.setHeight((int) (ScreenUtil.screen_height));
			TextureManager.SPRITESHEET.BACKGROUNDS.setWidth((int) (ScreenUtil.screen_height / 63.0f * 231.0f));
			AudioManager.MUSIC.TEMP_MUSIC.init();
			// AudioManager.MUSIC.TEMP_MUSIC.play(.5f);
			AudioManager.SOUND.COMPLETED.init();
			AudioManager.SOUND.COIN_ECHO.init();
			this.first_update = false;
		}
		TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.update(delta);
		if (InputManager.pointersDown[0]) {
			if (play_sound) {
				if (InputManager.pointers[0].x < 500) {
					AudioManager.SOUND.COMPLETED.playSound();
				} else {
					AudioManager.SOUND.COIN_ECHO.playSound();
				}
				this.play_sound = false;
			}
		} else {
			this.play_sound = true;
		}
		scroll = (int) (ScreenUtil.screen_width * 0.01f);
		acceleration = 0.05f * delta * 1;
		this.velocity += this.acceleration;
		current_scroll -= this.velocity;

		//TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.set
		//test
		//test2
		
	}

	private int ground_block_positions[];
	private House houses[];
	private House house = new House();

	@Override
	public void render() {
		this.batch.begin();

		TextureManager.SPRITESHEET.BACKGROUNDS.render(this.batch, 0, 0 + this.current_scroll / 10, 0);
		if (this.current_scroll / 25 + TextureManager.SPRITESHEET.BACKGROUNDS.getWidth() < 0) {
			
		}
		
		if (this.first_render) {
			ground_block_positions = new int[((int) (ScreenUtil.screen_width * 2.0f) / TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getWidth())];
			houses = new House[10];
			for (int i = 0; i < houses.length; i++) {
				// houses[i] = new House();
			}
			this.first_render = false;
		}

		for (int i = 0; i < this.ground_block_positions.length; i++) {
			this.ground_block_positions[i] = TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getWidth() * i;
		}

		for (int i = 0; i < this.ground_block_positions.length; i++) {
			while (this.ground_block_positions[i] + this.current_scroll < -TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getWidth()) {
				this.ground_block_positions[i] += this.ground_block_positions.length * TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getWidth();
			}
			this.batch.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 3), this.ground_block_positions[i]
					+ this.current_scroll, 0, TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getWidth(),
					TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getHeight());
		}

		this.batch.draw(TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getCurrentFrame(),
				TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getWidth() * .5f, TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getHeight(),
				TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getWidth(),
				TextureManager.ANIMATION_SPRITESHEET.PIXEL_WALKING.getHeight());
//		 this.house.render(this.batch,
//		 TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getHeight() * 2,
//		 TextureManager.TEXTURE.GRASS_BLOCK_SQUARE.getHeight(), 4, 2,
//		 House.DARK_BLUE_HOUSE, House.YELLOW_ROOF);

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
