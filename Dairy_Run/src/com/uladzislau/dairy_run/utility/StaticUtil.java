package com.uladzislau.dairy_run.utility;

import com.badlogic.gdx.Gdx;

public class StaticUtil {

	public static void setCursorVisible(boolean visible) {
		Gdx.input.setCursorCatched(!visible);
	}

	public static void log(String title, String message) {
		System.out.println(title + "\t" + message);
		Gdx.app.log(title, message);
	}

	public static void error(String title, String message) {
		System.out.println(title + "\t" + message);
		Gdx.app.error(title, message);
	}

}
