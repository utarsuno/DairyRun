package com.uladzislau.dairy_run;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dairy Run";
		cfg.useGL20 = false;
		cfg.width = 1400;
		cfg.height = 800;
		cfg.samples = 2;

		new LwjglApplication(new DairyRun(), cfg);
	}
}
