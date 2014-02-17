package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;

public class House {

	public static final byte TAN_HOUSE = 0;
	public static final byte DARK_BLUE_HOUSE = 3;
	public static final byte LIGHT_BLUE_HOUSE = 6;

	public static final byte BROWN_ROOF = 0;
	public static final byte YELLOW_ROOF = 3;
	public static final byte BLUE_ROOF = 6;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private byte house;
	private byte roof;
	
	private Rectanglei houseRectanglei;
	private Rectanglei roofRectanglei;

	public House(int x, int y) {
		this.x = x;
		this.y = y;
		this.houseRectanglei = new Rectanglei(x, y, -1, -1);
		this.roofRectanglei = new Rectanglei(x, -1, -1, -1);
		randomize();		
	}
	
	public void randomize() {
		this.width = Dice.get_Random_Integer_From_Min_To_Max(4, 5);
		this.height = Dice.get_Random_Integer_From_Min_To_Max(4, 4);
		int r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
		if (r == 0) {
			house = 0;
		} else if (r == 1) {
			house = 3;
		} else {
			house = 6;
		}
		r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
		if (r == 0) {
			roof = 0;
		} else if (r == 1) {
			roof = 3;
		} else {
			roof = 6;
		}
		if (Dice.nextBoolean()) {
			
		} else {
			
		}
		this.houseRectanglei.setWidth(this.width * Map.size);
		this.houseRectanglei.setHeight((this.height / 2) * Map.size);
		this.roofRectanglei.setY(this.houseRectanglei.getTop());
		this.roofRectanglei.setWidth(this.houseRectanglei.getWidth());
		this.roofRectanglei.setHeight(this.houseRectanglei.getHeight());
	}
	
	public void update(int offset) {
		this.houseRectanglei.setX(this.x + offset);
		this.roofRectanglei.setX(this.x + offset);
	}

	public void render(SpriteBatch sb, int x) {
		renderHouseLayer(sb, 25, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 0, width, house);
		for (int i = 1; i < height / 2 - 1; i++) {
			renderHouseLayer(sb, 23, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i, width, house);
		}
		renderHouseLayer(sb, 24, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * (height / 2 - 1), width, house);
		for (int i = height / 2; i < height - 1; i++) {
			renderRoofLayer(sb, 27, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i, width, roof);
		}
		renderRoofLayer(sb, 26, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * (height - 1), width, roof);
	}
	
	public static void render(SpriteBatch sb, int x, int y, int w, int h, byte house,
			byte roof) {
		renderHouseLayer(sb, 25, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * 0, w, house);
		for (int i = 1; i < h / 2 - 1; i++) {
			renderHouseLayer(sb, 23, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i, w, house);
		}
		renderHouseLayer(sb, 24, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * (h / 2 - 1), w, house);
		for (int i = h / 2; i < h - 1; i++) {
			renderRoofLayer(sb, 27, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i, w, roof);
		}
		renderRoofLayer(sb, 26, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * (h - 1), w, roof);
	}
	
	private static void renderHouseLayer(SpriteBatch sb, int layer, int x, int y, int w, byte house) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + house), x, y,
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight()); 
		
		for (int i = 1; i < w - 1; i++) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + house), x
					+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * i, y, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
					TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());
		}
		
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + house), x
				+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * (w - 1), y, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());
	}
	
	private static void renderRoofLayer(SpriteBatch sb, int layer, int x, int y, int w, byte roof) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + roof),
				x - TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(), y,
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());

		for (int i = 0; i < w; i++) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + roof), x
					+ TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i,
					y, TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
					TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());
		}

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + roof),
				x + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth() * w, y,
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getWidth(),
				TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight());
	}

	public boolean isMouseDownOnMe() {
		if (this.houseRectanglei.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
			return true;
		}
		if (this.roofRectanglei.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
			return true;
		}
		return false;
	}
	

	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectanglei getHouseRect() {
		return this.houseRectanglei;
	}
	
	public Rectanglei getRoofRect() {
		return this.roofRectanglei;
	}


}
