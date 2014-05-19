package com.uladzislau.dairy_run.gui;

import com.badlogic.gdx.graphics.Color;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.entity.button.BackButton;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MusicButton;
import com.uladzislau.dairy_run.entity.button.PauseButton;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class StaticGUI {

	public static CircleButton music_button;
	public static CircleButton back_button;
	public static CircleButton pause_button;

	public static void inititialize(DairyRun dairyRun) {
		dairyRun.getResourceManager();
		StaticGUI.music_button = new MusicButton(Map.size / 2, Map.size / 2, Map.size / 2, dairyRun);
		dairyRun.getResourceManager();
		StaticGUI.back_button = new BackButton(ScreenUtil.screen_width - Map.size / 2, Map.size / 2, Map.size / 2, dairyRun);
		dairyRun.getResourceManager();
		StaticGUI.pause_button = new PauseButton(ScreenUtil.screen_width - Map.size * 4 + Map.size / 2, ScreenUtil.screen_height - Map.size / 2, Map.size / 2, dairyRun);
	}

	public static void fillScreenWithColor(float r, float g, float b, float a) {
		ResourceManager.getSpriteBatch().setColor(r, g, b, a);
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.WHITE), 0, 0, ScreenUtil.screen_width, ScreenUtil.screen_height);
		ResourceManager.getSpriteBatch().setColor(Color.WHITE);
	}

	public static void renderBlackMessage(String message) {

		fillScreenWithColor(0.0f, 0.0f, 0.0f, 0.33f);

		// Render a black rectangle behind text.
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.BLACK), 0,
				ScreenUtil.screen_height / 2 - Map.size + Map.size / 2 - Map.size / 8,
				ScreenUtil.screen_width, Map.size + Map.size / 2);

		// Render message.
		FontManager.Font.PIXEL_REGULAR.render(message, Color.WHITE, ScreenUtil.screen_width / 2, ScreenUtil.screen_height / 2 - (Map.size / 4) * 2, Map.size, true, -1);
	}

	public static void renderErrorMessage(String message, float percentage) {
		fillScreenWithColor(0.0f, 0.0f, 0.0f, 0.33f * percentage);

		ResourceManager.getSpriteBatch().setColor(0.0f, 0.0f, 0.0f, 1.0f * percentage);

		// Render a black rectangle behind text.
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.BLACK), 0,
				ScreenUtil.screen_height / 2 - FontManager.Font.PIXEL_REGULAR.getHeight(message) * 2.0f + FontManager.Font.PIXEL_REGULAR.getHeight(message) / 4 + Map.size,
				ScreenUtil.screen_width, FontManager.Font.PIXEL_REGULAR.getHeight(message) * 1.5f);

		// Render message.
		FontManager.Font.PIXEL_REGULAR.render(message, Color.WHITE, ScreenUtil.screen_width / 2 - FontManager.Font.PIXEL_REGULAR.getWidth(message) / 2, ScreenUtil.screen_height / 2
				- FontManager.Font.PIXEL_REGULAR.getHeight(message) / 2 + Map.size, Map.size, true, -1);

		ResourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f);

	}
}
