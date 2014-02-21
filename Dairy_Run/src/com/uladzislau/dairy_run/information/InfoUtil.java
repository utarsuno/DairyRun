package com.uladzislau.dairy_run.information;

import com.badlogic.gdx.Gdx;

public class InfoUtil {

	public static final byte ANDRIOD = 1;
	public static final byte DESKTOP = 2;
	public static final byte WEBGL = 3;
	public static final byte APPLET = 4;
	public static final byte IOS = 5;

	public static byte CURRENT_PLATEFORM;

	public static void init() {
		switch (Gdx.app.getType()) {
		case Android:
			CURRENT_PLATEFORM = ANDRIOD;
			break;
		case Desktop:
			CURRENT_PLATEFORM = DESKTOP;
			break;
		case WebGL:
			CURRENT_PLATEFORM = WEBGL;
			break;
		case Applet:
			CURRENT_PLATEFORM = APPLET;
			break;
		case iOS:
			CURRENT_PLATEFORM = IOS;
			break;
		default:
			break;
		}
	}
}
