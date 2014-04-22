package com.uladzislau.dairy_run.gui;

import com.uladzislau.dairy_run.DairyRun;

import com.uladzislau.dairy_run.entity.button.BackButton;
import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MusicButton;
import com.uladzislau.dairy_run.entity.button.PauseButton;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.world.Map;

public class StaticGUI {

	public static CircleButton music_button;

	public static CircleButton back_button;

	public static CircleButton pause_button;

	public static void inititialize(DairyRun dairyRun) {
		StaticGUI.music_button = new MusicButton(Map.size / 2, Map.size / 2, Map.size / 2, dairyRun);
		StaticGUI.back_button = new BackButton(ScreenUtil.screen_width - Map.size / 2, Map.size / 2, Map.size / 2, dairyRun);
		StaticGUI.pause_button = new PauseButton(ScreenUtil.screen_width - Map.size * 4 + Map.size / 2, ScreenUtil.screen_height - Map.size / 2, Map.size / 2, dairyRun);
	}

}
