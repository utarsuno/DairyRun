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

	/* All bricks are located 9 blocks into the layer. */
	public static final byte TAN_BRICK_ONE = 23;
	public static final byte TAN_BRICK_TWO = 26;
	public static final byte BLUE_BRICK_ONE = 24;
	public static final byte BLUE_BRICK_TWO = 27;
	public static final byte LIGHT_BLUE_BRICK_ONE = 25;
	public static final byte LIGHT_BLUE_BRICK_TWO = 28;

	public static final byte BROWN_ROOF = 0;
	public static final byte YELLOW_ROOF = 3;
	public static final byte BLUE_ROOF = 6;

	public static final byte DOOR_ONE = 10;
	public static final byte DOOR_TWO = 12;
	public static final byte DOOR_THREE = 13;

	private int x;
	private int y;
	private int width;
	private int height;
	private byte house;
	private byte roof;
	private byte door;

	private int door_location;

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
			house = TAN_HOUSE;
			roof = BROWN_ROOF;
		} else if (r == 1) {
			house = DARK_BLUE_HOUSE;
			roof = YELLOW_ROOF;
		} else {
			house = LIGHT_BLUE_HOUSE;
			roof = BLUE_ROOF;
		}
		r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
		if (r == 0) {
			door = DOOR_ONE;
		} else if (r == 1) {
			door = DOOR_TWO;
		} else {
			door = DOOR_THREE;
		}
		if (Dice.nextBoolean()) {
			this.door_location = (Map.size * 1);
		} else {
			this.door_location = (this.width * Map.size) - Map.size * 2;
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

		renderDoorLayer(sb, 24, x + this.door_location, y, door);
		renderDoorLayer(sb, 23, x + this.door_location, y + Map.size, door);
		
		for (int i = height / 2; i < height - 1; i++) {
			renderRoofLayer(sb, 27, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * i, width, roof);
		}
		renderRoofLayer(sb, 26, x, y + TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getHeight() * (height - 1), width, roof);
	}

	public void renderDoorLayer(SpriteBatch sb, int layer, int x, int y, byte door) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + door), x, y, Map.size, Map.size);
	}

	private void renderHouseLayer(SpriteBatch sb, int layer, int x, int y, int w, byte house) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + house), x, y, Map.size, Map.size);

		for (int i = 1; i < w - 1; i++) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + house), x + Map.size * i, y, Map.size, Map.size);
		}

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + house), x + Map.size * (w - 1), y, Map.size,
				Map.size);
	}

	private void renderRoofLayer(SpriteBatch sb, int layer, int x, int y, int w, byte roof) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + roof), x - Map.size, y, Map.size, Map.size);

		for (int i = 0; i < w; i++) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + roof), x + Map.size * i, y, Map.size, Map.size);
		}

		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + roof), x + Map.size * w, y, Map.size, Map.size);
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
