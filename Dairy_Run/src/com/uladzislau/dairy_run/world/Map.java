package com.uladzislau.dairy_run.world;

import com.uladzislau.dairy_run.information.ScreenUtil;

public class Map {

	public static int number_of_vertical_blocks;
	public static int number_of_horizontal_blocks_off_edge_included;
	public static int size;
	private static float current_scroll;

	private static int ground_level;

	public static void init() {
		Map.number_of_vertical_blocks = 8;
		Map.size = ScreenUtil.screen_height / Map.number_of_vertical_blocks;
		Map.number_of_horizontal_blocks_off_edge_included = ScreenUtil.screen_width / Map.size + 1;
	}

	public static int getGroundLevel() {
		return ground_level;
	}

	public static void setGroundLevel(int gl) {
		ground_level = gl;
	}

	public static void setCurrentScroll(float f) {
		Map.current_scroll = f;
	}

	public static int getCurrentScrollAsInt() {
		return ((int) current_scroll);
	}

}