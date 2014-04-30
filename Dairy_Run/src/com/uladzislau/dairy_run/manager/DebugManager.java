package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugManager {

	public static final void renderFrameRate(SpriteBatch sb, int x, int y) {
		FontManager.Font.PIXEL_REGULAR.render(sb, Color.GREEN, "" + Gdx.graphics.getFramesPerSecond(), x, y);
	}

}
