package com.uladzislau.dairy_run;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dairy Run";
		cfg.useGL20 = true;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = ge.getMaximumWindowBounds();

		cfg.width = (int) (bounds.getWidth() * .80);
		cfg.height = (int) (bounds.getHeight() * .70);
		cfg.samples = 0;
		cfg.resizable = false;

		new LwjglApplication(new DairyRun(), cfg);
	}
}
