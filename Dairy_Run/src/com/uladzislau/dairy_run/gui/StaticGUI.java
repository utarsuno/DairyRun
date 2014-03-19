package com.uladzislau.dairy_run.gui;

import com.uladzislau.dairy_run.entity.button.CircleButton;
import com.uladzislau.dairy_run.entity.button.MusicButton;
import com.uladzislau.dairy_run.world.Map;

public class StaticGUI {
	
	public static CircleButton music_button;
	
	public static void inititialize() {
		StaticGUI.music_button = new MusicButton(Map.size / 2, Map.size / 2, Map.size / 2);
	}
	
}
