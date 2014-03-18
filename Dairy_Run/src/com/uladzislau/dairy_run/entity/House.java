package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.uladzislau.dairy_run.entity.button.MilkButton;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglei;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

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

	public static final byte WINDOW_ONE = 10;
	public static final byte WINDOW_TWO = 12;

	public static final short WINODW = 31 * 25 + 11;

	private int x;
	private int y;
	private int width;
	private int height;
	private byte house;
	private byte roof;
	private byte door;
	private short window;
	private byte[][] brick_layers;

	private short milk_needed[];
	private boolean milk_delievered[];
	private DeltaTimer milkFader[];
	private int number_of_milks;

	private int door_location;
	private int window_location;

	private Rectanglei houseRectanglei;
	private Rectanglei roofRectanglei;

	private boolean render_the_house;

	private Play play;

	public House(Play play) {
		this.houseRectanglei = new Rectanglei(-1, -1, -1, -1);
		this.roofRectanglei = new Rectanglei(-1, -1, -1, -1);
		this.brick_layers = new byte[2][6];
		this.milk_needed = new short[10];
		this.milk_delievered = new boolean[10];
		this.milkFader = new DeltaTimer[10];
		for (int i = 0; i < this.milkFader.length; i++) {
			this.milkFader[i] = new DeltaTimer(DeltaTimer.RUN_ONCE, 125);
		}
		this.render_the_house = true;

		this.play = play;
	}

	public void createHouse(int xp, int yp) {
		this.houseRectanglei.setX(xp);
		this.houseRectanglei.setY(yp);
		this.roofRectanglei.setX(xp);
		this.x = xp;
		this.y = yp;
		randomize();
	}

	public void randomize() {
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 100) < 10) {
			this.render_the_house = false;
		}
		this.width = Dice.get_Random_Integer_From_Min_To_Max(5, 6);
		this.number_of_milks = Dice.get_Random_Integer_From_Min_To_Max(1, this.width / 2);
		for (int i = 0; i < this.number_of_milks; i++) {
			this.milk_delievered[i] = false;
			int r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
			if (r == 0) {
				if (this.play.getLevel().isRegularMilkButtonEnabled()) {
					this.milk_needed[i] = MilkButton.REGULAR;
				} else {
					if (this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
						if (Dice.nextBoolean()) {
							this.milk_needed[i] = MilkButton.CHOCOLATE;
						} else {
							this.milk_needed[i] = MilkButton.STRAWBERRY;
						}
					} else if (this.play.getLevel().isChocolateMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.CHOCOLATE;
					} else if (this.play.getLevel().isStrawberryMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.STRAWBERRY;
					}
				}
			} else if (r == 1) {
				if (this.play.getLevel().isChocolateMilkButtonEnabled()) {
					this.milk_needed[i] = MilkButton.CHOCOLATE;
				} else {
					if (this.play.getLevel().isRegularMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
						if (Dice.nextBoolean()) {
							this.milk_needed[i] = MilkButton.REGULAR;
						} else {
							this.milk_needed[i] = MilkButton.STRAWBERRY;
						}
					} else if (this.play.getLevel().isRegularMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.REGULAR;
					} else if (this.play.getLevel().isStrawberryMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.STRAWBERRY;
					}
				}
			} else if (r == 2) {
				if (this.play.getLevel().isStrawberryMilkButtonEnabled()) {
					this.milk_needed[i] = MilkButton.STRAWBERRY;
				} else {
					if (this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isRegularMilkButtonEnabled()) {
						if (Dice.nextBoolean()) {
							this.milk_needed[i] = MilkButton.REGULAR;
						} else {
							this.milk_needed[i] = MilkButton.STRAWBERRY;
						}
					} else if (this.play.getLevel().isChocolateMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.CHOCOLATE;
					} else if (this.play.getLevel().isRegularMilkButtonEnabled()) {
						this.milk_needed[i] = MilkButton.REGULAR;
					}
				}
			}
			this.milkFader[i].reset();
		}
		this.height = Dice.get_Random_Integer_From_Min_To_Max(4, 4);
		int r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
		if (r == 0) {
			this.house = TAN_HOUSE;
			this.roof = BROWN_ROOF;
			initBrickLayerWith(TAN_BRICK_ONE);
		} else if (r == 1) {
			this.house = DARK_BLUE_HOUSE;
			this.roof = YELLOW_ROOF;
			initBrickLayerWith(BLUE_BRICK_ONE);
		} else {
			this.house = LIGHT_BLUE_HOUSE;
			this.roof = BLUE_ROOF;
			initBrickLayerWith(LIGHT_BLUE_BRICK_ONE);
		}
		r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
		if (r == 0) {
			this.door = DOOR_ONE;
		} else if (r == 1) {
			this.door = DOOR_TWO;
		} else {
			this.door = DOOR_THREE;
		}

		if (Dice.nextBoolean()) {
			this.door_location = (Map.size * 1);
			this.window_location = (this.width * Map.size) - Map.size * 2;
		} else {
			this.door_location = (this.width * Map.size) - Map.size * 2;
			this.window_location = (Map.size * 1);
		}
		this.window = House.WINODW;
		this.houseRectanglei.setWidth(this.width * Map.size);
		this.houseRectanglei.setHeight((this.height / 2) * Map.size);
		this.roofRectanglei.setY(this.houseRectanglei.getTop());
		this.roofRectanglei.setWidth(this.houseRectanglei.getWidth());
		this.roofRectanglei.setHeight(this.houseRectanglei.getHeight());

		this.render_the_house = true;
	}

	private void updatePosition() {
		setX(getX() + this.play.getHouses().length * Map.size * 10);
	}

	private void initBrickLayerWith(byte layer) {
		for (int y = 0; y < this.brick_layers.length; y++) {
			for (int x = 0; x < this.brick_layers[y].length; x++) {
				if (Dice.nextFloat() < 0.2) {
					if (Dice.nextBoolean()) {
						this.brick_layers[y][x] = (byte) (0 + layer);
					} else {
						this.brick_layers[y][x] = (byte) (3 + layer);
					}
				} else {
					this.brick_layers[y][x] = -1;
				}
			}
		}
	}

	public void update(float delta, int current_scroll) {
		this.houseRectanglei.setX(this.x + current_scroll);
		this.roofRectanglei.setX(this.x + current_scroll);

		for (int i = 0; i < this.number_of_milks; i++) {
			if (this.milk_delievered[i]) {
				this.milkFader[i].update(delta);
			}
		}

		if (getX() + (getWidth() + 1) * Map.size + current_scroll < 0) {
			if (this.render_the_house) {
				short milks_not_delievered[] = new short[this.number_of_milks];
				boolean create_chaser = false;
				for (int i = 0; i < this.number_of_milks; i++) {
					if (!this.milk_delievered[i]) {
						milks_not_delievered[i] = this.milk_needed[i];
						create_chaser = true;
					}
				}
				if (create_chaser) {
					if (this.play.getLevel().isCreateChasers()) {
						this.play.createChaser(milks_not_delievered);
					}
				}
			}
			randomize();
			updatePosition();
		}
	}

	public void render(SpriteBatch sb, int x) {
		// if (this.render_the_house) {
		renderHouseLayer(sb, 25, x, this.y, this.width, this.house);
		for (int i = 1; i < this.height / 2 - 1; i++) {
			renderHouseLayer(sb, 23, x, this.y + Map.size * i, this.width, this.house);
		}
		renderHouseLayer(sb, 24, x, this.y + Map.size * (this.height / 2 - 1), this.width, this.house);

		renderDoorLayer(sb, 24, x + this.door_location, this.y, this.door);
		renderDoorLayer(sb, 23, x + this.door_location, this.y + Map.size, this.door);

		renderWindowLayer(sb, x + this.window_location, this.y + Map.size - Map.size / 2, this.window);

		for (int i = this.height / 2; i < this.height - 1; i++) {
			renderRoofLayer(sb, 27, x, this.y + Map.size * i, this.width, this.roof);
		}
		renderRoofLayer(sb, 26, x, this.y + Map.size * (this.height - 1), this.width, this.roof);

		// Render the milk-needed.
		for (int i = 0; i < this.number_of_milks; i++) {
			sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 1.0f - this.milkFader[i].percentComplete());
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.milk_needed[i]),
					x
							+ ((Map.size * this.width) - MathUtils.round(((Map.size * 0.1f)) * (this.number_of_milks - 1) + Map.size
									* this.number_of_milks)) / 2 + Map.size * i + (Map.size * 0.1f) * i, Map.size * (this.height + 2)
							- Map.size / 2, Map.size, Map.size);
			sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 1.0f);
		}
	}

	public void renderWindowLayer(SpriteBatch sb, int x, int y, short window) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(this.window), x, y, Map.size, Map.size);
	}

	public void renderDoorLayer(SpriteBatch sb, int layer, int x, int y, byte door) {
		if (this.house == TAN_HOUSE) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + door), x, y, Map.size, Map.size);
		} else if (this.house == House.DARK_BLUE_HOUSE) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + door + 8), x, y, Map.size, Map.size);
		} else {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + door + 4), x, y, Map.size, Map.size);
		}
	}

	private void renderHouseLayer(SpriteBatch sb, int layer, int x, int y, int w, byte house) {
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + house), x, y, Map.size, Map.size);
		int current_height_layer = (y / Map.size) - 1;
		for (int i = 1; i < w - 1; i++) {
			sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + house), x + Map.size * i, y, Map.size, Map.size);
			for (int j = 0; j < this.brick_layers[current_height_layer].length; j++) {
				if (current_height_layer > 0 && j > 0 && j < w - 1 && this.brick_layers[current_height_layer][j] != -1) {
					sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * this.brick_layers[current_height_layer][j] + 9), x
							+ Map.size * j, y, Map.size, Map.size);
				}
			}
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
		return this.height;
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

	public void debugRender(ShapeRenderer sr) {
		sr.begin(ShapeType.Line);
		sr.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		sr.rect(this.getHouseRect().getX(), this.getHouseRect().getY(), this.getHouseRect().getWidth(), this.getHouseRect().getHeight());
		sr.rect(this.getRoofRect().getX(), this.getRoofRect().getY(), this.getRoofRect().getWidth(), this.getRoofRect().getHeight());
		sr.end();
	}

	public void deliverMilk(short milk_type) {
		for (int i = 0; i < this.number_of_milks; i++) {
			if (milk_type == this.milk_needed[i] && !this.milk_delievered[i]) {
				this.milk_delievered[i] = true;
				return;
			}
		}
	}

	public boolean isMilkNeeded(short milk_type) {
		for (int i = 0; i < this.number_of_milks; i++) {
			if (milk_type == this.milk_needed[i] && !this.milk_delievered[i]) {
				return true;
			}
		}
		return false;
	}

	public boolean needsMoreMilk() {
		for (int i = 0; i < this.number_of_milks; i++) {
			if (!this.milk_delievered[i]) {
				return true;
			}
		}
		return false;
	}

}