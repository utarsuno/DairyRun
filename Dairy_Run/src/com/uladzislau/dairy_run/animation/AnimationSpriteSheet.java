package com.uladzislau.dairy_run.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationSpriteSheet {
	
	private static final int FRAME_COLS = 6; // #1
	private static final int FRAME_ROWS = 5; // #2

	Animation walkAnimation; // #3
	Texture walkSheet; // #4
	TextureRegion[] walkFrames; // #5
	SpriteBatch spriteBatch; // #6
	TextureRegion currentFrame; // #7

	float stateTime; // #8

	public void create() {
		walkSheet = new Texture(Gdx.files.internal("animation_sheet.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS); // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.025f, walkFrames); // #11
		spriteBatch = new SpriteBatch(); // #12
		stateTime = 0f; // #13
	}

	public void render() {
		stateTime += Gdx.graphics.getDeltaTime(); // #15
		currentFrame = walkAnimation.getKeyFrame(stateTime, true); // #16
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50); // #17
		spriteBatch.end();
	}
	
}
