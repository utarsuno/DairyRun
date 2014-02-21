package com.uladzislau.dairy_run.entity;

import com.uladzislau.dairy_run.information.ScreenUtil;

public class Map {

	public static int number_of_vertical_blocks;
	public static int size;

	public static void init() {
		number_of_vertical_blocks = 8;
		size = (int) (ScreenUtil.screen_height / number_of_vertical_blocks);
	}

}
