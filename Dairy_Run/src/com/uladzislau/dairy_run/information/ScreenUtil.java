package com.uladzislau.dairy_run.information;

import com.badlogic.gdx.Gdx;

import com.uladzislau.dairy_run.math.Vector2f;

public class ScreenUtil {

	public static int screen_width;
	public static int screen_height;
	public static int screen_diagonal;

	public static void init() {
		ScreenUtil.screen_width = (int) Gdx.graphics.getWidth();
		ScreenUtil.screen_height = (int) Gdx.graphics.getHeight();
		ScreenUtil.screen_diagonal = (int) (new Vector2f(ScreenUtil.screen_width, ScreenUtil.screen_height).magnitude);
	}

}
