package com.uladzislau.dairy_run.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Dice;
import com.uladzislau.dairy_run.math.geometry.Rectanglef;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.world.Map;

public class House extends Entity {

	// TODO: Move this to TextureManager.
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

	private Rectanglef houseRectanglef;
	private Rectanglef roofRectanglef;

	private boolean render_the_house;

	private Play play;

	private int start_gap;
	private int ending_gap;

	public House(Play play) {
		super(0, 0, 0, 0);
		this.houseRectanglef = new Rectanglef(-1, -1, -1, -1);
		this.roofRectanglef = new Rectanglef(-1, -1, -1, -1);
		this.brick_layers = new byte[2][6];
		this.milk_needed = new short[10];
		this.milk_delievered = new boolean[10];
		this.milkFader = new DeltaTimer[10];
		for (int i = 0; i < this.milkFader.length; i++) {
			this.milkFader[i] = new DeltaTimer(DeltaTimer.RUN_ONCE, 150);
		}
		this.render_the_house = true;

		this.play = play;
	}

	public void createHouse(int index) {
		randomize(true);
		int sum = 0;
		for (int i = 0; i < index; i++) {
			sum += this.play.getHouses()[i].getStartGap() + this.play.getHouses()[i].getWidth() + this.play.getHouses()[i].getEndingGap();
		}
		this.houseRectanglef.setX(sum + this.start_gap);
		this.roofRectanglef.setX(sum + this.start_gap);
		this.setX(sum + this.start_gap);
		this.houseRectanglef.setY(Map.getGroundLevel());
		this.setY(Map.getGroundLevel());
	}

	private void updatePosition() {
		int sum = 0;
		for (int i = 0; i < this.play.getHouses().length; i++) {
			sum += this.play.getHouses()[i].getStartGap() + this.play.getHouses()[i].getWidth() + this.play.getHouses()[i].getEndingGap();
		}
		this.setWidth(Dice.get_Random_Integer_From_Min_To_Max(5, 9) * Map.size);
		this.setStartGap(Dice.get_Random_Integer_From_Min_To_Max(1, 3) * Map.size);
		this.setEndingGap(Dice.get_Random_Integer_From_Min_To_Max(1, 3) * Map.size);
		setX(this.getX() + sum + this.start_gap);
	}

	public void randomize(boolean first_randomize) {
		if (Dice.get_Random_Integer_From_Min_To_Max(0, 100) < 10) {
			this.render_the_house = false;
		}

		if (first_randomize) {
			this.setWidth(Dice.get_Random_Integer_From_Min_To_Max(5, 6) * Map.size);
			this.setStartGap(Map.size * 6);
			this.setEndingGap(Dice.get_Random_Integer_From_Min_To_Max(1, 3) * Map.size);
		} else {
			updatePosition();
		}

		this.number_of_milks = Dice.get_Random_Integer_From_Min_To_Max((this.getWidth() / Map.size) / 3, (int) ((this.getWidth() / Map.size) / 1.5f));
		for (int i = 0; i < this.number_of_milks; i++) {
			this.milk_delievered[i] = false;
			int r;
			if (this.play.getLevel().isRegularMilkButtonEnabled() && this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				r = Dice.get_Random_Integer_From_Min_To_Max(0, 2);
				if (r == 0) {
					this.milk_needed[i] = TextureManager.REGULAR;
				} else if (r == 1) {
					this.milk_needed[i] = TextureManager.CHOCOLATE;
				} else if (r == 2) {
					this.milk_needed[i] = TextureManager.STRAWBERRY;
				}
			} else if (this.play.getLevel().isRegularMilkButtonEnabled() && this.play.getLevel().isChocolateMilkButtonEnabled() && !this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				if (Dice.nextBoolean()) {
					this.milk_needed[i] = TextureManager.REGULAR;
				} else {
					this.milk_needed[i] = TextureManager.CHOCOLATE;
				}
			} else if (this.play.getLevel().isRegularMilkButtonEnabled() && !this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				if (Dice.nextBoolean()) {
					this.milk_needed[i] = TextureManager.REGULAR;
				} else {
					this.milk_needed[i] = TextureManager.STRAWBERRY;
				}
			} else if (this.play.getLevel().isRegularMilkButtonEnabled() && !this.play.getLevel().isChocolateMilkButtonEnabled() && !this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				this.milk_needed[i] = TextureManager.REGULAR;
			} else if (!this.play.getLevel().isRegularMilkButtonEnabled() && this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				if (Dice.nextBoolean()) {
					this.milk_needed[i] = TextureManager.CHOCOLATE;
				} else {
					this.milk_needed[i] = TextureManager.STRAWBERRY;
				}
			} else if (!this.play.getLevel().isRegularMilkButtonEnabled() && !this.play.getLevel().isChocolateMilkButtonEnabled() && this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				this.milk_needed[i] = TextureManager.STRAWBERRY;
			} else if (!this.play.getLevel().isRegularMilkButtonEnabled() && this.play.getLevel().isChocolateMilkButtonEnabled() && !this.play.getLevel().isStrawberryMilkButtonEnabled()) {
				this.milk_needed[i] = TextureManager.CHOCOLATE;
			}
			this.milkFader[i].reset();
		}
		this.setHeight(Dice.get_Random_Integer_From_Min_To_Max(4, 4));
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
			this.window_location = (this.getWidth()) - Map.size * 2;
		} else {
			this.door_location = (this.getWidth()) - Map.size * 2;
			this.window_location = (Map.size * 1);
		}
		this.window = House.WINODW;
		this.houseRectanglef.setWidth(this.getWidth());
		this.houseRectanglef.setHeight((this.getHeight() / 2) * Map.size);
		this.roofRectanglef.setY(this.houseRectanglef.getTop());
		this.roofRectanglef.setWidth(this.houseRectanglef.getWidth());
		this.roofRectanglef.setHeight(this.houseRectanglef.getHeight());

		this.render_the_house = true;
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

	@Override
	public void update(float delta) {
		this.houseRectanglef.setX(this.getX() + Map.size + Map.getCurrentScrollAsInt());
		this.roofRectanglef.setX(this.getX() + Map.size + Map.getCurrentScrollAsInt());

		for (int i = 0; i < this.number_of_milks; i++) {
			if (this.milk_delievered[i]) {
				this.milkFader[i].update(delta);
			}
		}

		if (getX() + (getWidth() / Map.size + 1) * Map.size + Map.getCurrentScrollAsInt() < 0) {
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
			randomize(false);
		}
	}

	@Override
	public void render() {
		renderHouseLayer(ResourceManager.getSpriteBatch(), 25, (int) (this.getX() + Map.getCurrentScrollAsInt()), (int) this.getY(), this.getWidth() / Map.size, this.house);
		for (int i = 1; i < this.getHeight() / 2 - 1; i++) {
			renderHouseLayer(ResourceManager.getSpriteBatch(), 23, (int) (this.getX() + Map.getCurrentScrollAsInt()), (int) (this.getY() + Map.size * i), this.getWidth() / Map.size,
					this.house);
		}
		renderHouseLayer(ResourceManager.getSpriteBatch(), 24, (int) (this.getX() + Map.getCurrentScrollAsInt()), (int) (this.getY() + Map.size * (this.getHeight() / 2 - 1)),
				this.getWidth() / Map.size, this.house);

		renderDoorLayer(ResourceManager.getSpriteBatch(), 24, (int) (this.getX() + Map.getCurrentScrollAsInt()) + this.door_location, (int) this.getY(), this.door);
		renderDoorLayer(ResourceManager.getSpriteBatch(), 23, (int) (this.getX() + Map.getCurrentScrollAsInt()) + this.door_location, (int) (this.getY() + Map.size), this.door);

		renderWindowLayer(ResourceManager.getSpriteBatch(), (int) (this.getX() + Map.getCurrentScrollAsInt()) + this.window_location, (int) (this.getY() + Map.size - Map.size / 2));

		for (int i = this.getHeight() / 2; i < this.getHeight() - 1; i++) {
			renderRoofLayer(ResourceManager.getSpriteBatch(), 27, (int) (this.getX() + Map.getCurrentScrollAsInt()), (int) (this.getY() + Map.size * i), this.getWidth() / Map.size,
					this.roof);
		}
		renderRoofLayer(ResourceManager.getSpriteBatch(), 26, (int) (this.getX() + Map.getCurrentScrollAsInt()), (int) (this.getY() + Map.size * (this.getHeight() - 1)), this.getWidth()
				/ Map.size, this.roof);

		// Render the milk-needed.
		for (int i = 0; i < this.number_of_milks; i++) {
			ResourceManager.getSpriteBatch().setColor(ResourceManager.getSpriteBatch().getColor().r, ResourceManager.getSpriteBatch().getColor().g,
					ResourceManager.getSpriteBatch().getColor().b, 1.0f - this.milkFader[i].percentComplete());
			ResourceManager.getSpriteBatch().draw(
					TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(this.milk_needed[i]),
					(int) (this.getX() + Map.getCurrentScrollAsInt())
							+ (this.getWidth() - MathUtils.round(((Map.size * 0.1f)) * (this.number_of_milks - 1) + Map.size * this.number_of_milks)) / 2 + Map.size * i + (Map.size * 0.1f)
							* i - (Map.size * (1.0f + this.milkFader[i].percentComplete())) / 2 + Map.size / 2,
					Map.size * (this.getHeight() + 2) - Map.size / 2 - (Map.size * (1.0f + this.milkFader[i].percentComplete())) / 2 + Map.size / 2,
					Map.size * (1.0f + this.milkFader[i].percentComplete()), Map.size * (1.0f + this.milkFader[i].percentComplete()));
			ResourceManager.getSpriteBatch().setColor(ResourceManager.getSpriteBatch().getColor().r, ResourceManager.getSpriteBatch().getColor().g,
					ResourceManager.getSpriteBatch().getColor().b, 1.0f);
		}
	}

	public void renderWindowLayer(SpriteBatch sb, int x, int y) {
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(this.window + (this.house / 3) * 4), x, y, Map.size, Map.size);
	}

	public void renderDoorLayer(SpriteBatch sb, int layer, int x, int y, byte door) {
		if (this.house == TAN_HOUSE) {
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + door), x, y, Map.size, Map.size);
		} else if (this.house == House.DARK_BLUE_HOUSE) {
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + door + 8), x, y, Map.size, Map.size);
		} else {
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + door + 4), x, y, Map.size, Map.size);
		}
	}

	private void renderHouseLayer(SpriteBatch sb, int layer, int x, int y, int w, byte house) {
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + house), x, y, Map.size, Map.size);
		int current_height_layer = (y / Map.size) - 1;
		for (int i = 1; i < w - 1; i++) {
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + house), x + Map.size * i, y, Map.size, Map.size);
			for (int j = 0; j < this.brick_layers[current_height_layer].length; j++) {
				if (current_height_layer > 0 && j > 0 && j < w - 1 && this.brick_layers[current_height_layer][j] != -1) {
					sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * this.brick_layers[current_height_layer][j] + 9), x + Map.size * j, y, Map.size, Map.size);
				}
			}
		}
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + house), x + Map.size * (w - 1), y, Map.size, Map.size);
	}

	private void renderRoofLayer(SpriteBatch sb, int layer, int x, int y, int w, byte roof) {
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + roof), x - Map.size, y, Map.size, Map.size);
		for (int i = 0; i < w; i++) {
			sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + 1 + roof), x + Map.size * i, y, Map.size, Map.size);
		}
		sb.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * layer + 2 + roof), x + Map.size * w, y, Map.size, Map.size);
	}

	public boolean isMouseDownOnMe() {
		if (this.houseRectanglef.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
			return true;
		}
		if (this.roofRectanglef.isPointInside(InputManager.pointers[0].x, InputManager.pointers[0].y)) {
			return true;
		}
		return false;
	}

	public Rectanglef getHouseRect() {
		return this.houseRectanglef;
	}

	public Rectanglef getRoofRect() {
		return this.roofRectanglef;
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

	public void deliverMilk() {
		for (int i = 0; i < this.number_of_milks; i++) {
			if (!this.milk_delievered[i]) {
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

	public int getStartGap() {
		return start_gap;
	}

	public void setStartGap(int start_gap) {
		this.start_gap = start_gap;
	}

	public int getEndingGap() {
		return ending_gap;
	}

	public void setEndingGap(int ending_gap) {
		this.ending_gap = ending_gap;
	}

}