package com.uladzislau.dairy_run.entity;

import com.uladzislau.dairy_run.information.ScreenUtil;

public class Map {

	public static int number_of_vertical_blocks;
	public static int number_of_horizontal_blocks_off_edge_included;
	public static int size;

	private int ground_level;

	public static void init() {
		Map.number_of_vertical_blocks = 8;
		Map.size = ScreenUtil.screen_height / Map.number_of_vertical_blocks;
		Map.number_of_horizontal_blocks_off_edge_included = ScreenUtil.screen_width / Map.size + 1;
	}

	public int getGroundLevel() {
		return ground_level;
	}

	public void setGroundLevel(int ground_level) {
		this.ground_level = ground_level;
	}

}