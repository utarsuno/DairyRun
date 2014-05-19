package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.Color;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.entity.Background;
import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.entity.House;
import com.uladzislau.dairy_run.game_state.GameStateManager.STATE;
import com.uladzislau.dairy_run.gui.Heart;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math.Vector2f;
import com.uladzislau.dairy_run.math_utility.MathUtil;
import com.uladzislau.dairy_run.world.Map;

public class Tutorial extends GameState {

	private int total_clicks;
	private GroundBlock ground_blocks[];
	private Background backgrounds[];
	private int house_x;

	public Tutorial(DairyRun dairy_run, STATE state) {
		super(dairy_run, state);
	}

	@Override
	public void initialize() {
		this.total_clicks = 0;

		// Create the background.
		this.backgrounds = new Background[2];
		this.backgrounds[0] = new Background(0, 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height, Background.BLUE);
		this.backgrounds[1] = new Background(TextureManager.Spritesheet.BACKGROUNDS.getWidth(), 0, TextureManager.Spritesheet.BACKGROUNDS.getWidth(), ScreenUtil.screen_height,
				Background.BLUE);
		// Create the ground blocks.
		this.ground_blocks = new GroundBlock[(ScreenUtil.screen_width / Map.size) + 2];
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i] = new GroundBlock(i * Map.size, Map.size * 1.5f, Map.size, Map.size, this.ground_blocks.length, true, GroundBlock.Theme.GRASS);
			this.ground_blocks[i].setSpawnDoodads(true);
		}
		this.house_x = ScreenUtil.screen_width;
	}

	private boolean reset = true;
	private float velocity;

	@Override
	public void update(float delta) {

		if (InputManager.pointersDown[0]) {
			if (!this.reset) {
				this.total_clicks++;
				this.reset = true;
			}
		} else {
			this.reset = false;
		}

		if (this.total_clicks > 1) {

			TextureManager.Animation_Spritesheet.WALKING.setFrameTime(50);
			TextureManager.Animation_Spritesheet.WALKING.update(delta);

			for (int i = 0; i < this.ground_blocks.length; i++) {
				this.ground_blocks[i].update(delta);
			}

			for (int i = 0; i < this.backgrounds.length; i++) {
				this.backgrounds[i].update(delta);
			}

			if (this.total_clicks > 12) {
				this.house_x -= this.velocity;
				if (this.house_x + Map.size * 4 < 0) {
					this.house_x = ScreenUtil.screen_width;
				}
			}

			this.velocity = 5;

			Map.setCurrentScroll(Map.getCurrentScrollAsInt() - this.velocity);
		}

		StaticGUI.music_button.update(delta);
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {

		for (int i = 0; i < this.backgrounds.length; i++) {
			this.backgrounds[i].render();
		}

		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].render();
		}

		if (this.total_clicks > 1) {

			if (this.total_clicks != 9) {
				// Render the character.
				TextureManager.render(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrameNumber() + 28, Map.size * 3, Map.size * 1.5f, Map.size, Map.size);
			} else {
				// Render the character.
				TextureManager.render(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrameNumber() + 28 + 31 * 6, Map.size * 3, Map.size * 1.5f, Map.size, Map.size);
				// Render the chasing character.
				TextureManager.render(TextureManager.Animation_Spritesheet.WALKING.getCurrentFrameNumber() + 28 + 31 * 3, (int) (Map.size * 1.5f), Map.size * 1.5f, Map.size, Map.size);
			}

			if (this.total_clicks > 2) {
				// Render three hearts.
				Heart.render(ScreenUtil.screen_width - Map.size * 3, (Map.number_of_vertical_blocks - 1) * Map.size, 5);
				if (this.total_clicks > 4) {
					if (this.total_clicks > 5) {
						// Render the current velocity.
						FontManager.Font.PIXEL_REGULAR.render("" + MathUtil.round(this.velocity, 2), Color.WHITE, //$NON-NLS-1$
								Map.size * 0.1f, ScreenUtil.screen_height - Map.size * 0.9f, (int) (Map.size * 0.8f), false, -1);
						if (this.total_clicks > 6) {
							// Render the current number of milks delivered.
							FontManager.Font.PIXEL_REGULAR.render("" + 0, Color.WHITE, //$NON-NLS-1$
									Map.size * 0.1f, ScreenUtil.screen_height - Map.size * 1.9f, (int) (Map.size * 0.8f), false, -1);
							if (this.total_clicks > 7) {
								// Render the current streak.
								FontManager.Font.PIXEL_REGULAR.render("" + 0, Color.WHITE, (int) (Map.size * .06f), //$NON-NLS-1$
										ScreenUtil.screen_height - (int) (Map.size * 2.4f), (int) (Map.size * 0.4f), false, -1);
								// Render the milk button.
								TextureManager.render(TextureManager.REGULAR, Map.size * 3, Map.size / 8, Map.size, Map.size);
								if (this.total_clicks > 8) {
									TextureManager.render(TextureManager.BOOT, (int) (Map.size * 1.5f), Map.size / 8, Map.size, Map.size);
									if (this.total_clicks > 10) {
										// Render the chocolate button.
										TextureManager.render(TextureManager.CHOCOLATE, ScreenUtil.screen_width - Map.size * 4, Map.size / 8, Map.size, Map.size);
										if (this.total_clicks > 11) {
											// Render the strawberry button.
											TextureManager.render(TextureManager.STRAWBERRY, (int) (ScreenUtil.screen_width - Map.size * 2.5f), Map.size / 8, Map.size, Map.size);
											if (this.total_clicks > 12) {

												TextureManager.render(31 * 25 + 0, this.house_x, Map.getGroundLevel(), Map.size, Map.size);
												TextureManager.render(31 * 25 + 1, this.house_x + Map.size * 1, Map.getGroundLevel(), Map.size, Map.size);
												TextureManager.render(31 * 25 + 1, this.house_x + Map.size * 2, Map.getGroundLevel(), Map.size, Map.size);
												TextureManager.render(31 * 25 + 2, this.house_x + Map.size * 3, Map.getGroundLevel(), Map.size, Map.size);

												TextureManager.render(31 * 23 + 0, this.house_x, Map.getGroundLevel() + Map.size, Map.size, Map.size);
												TextureManager.render(31 * 23 + 1, this.house_x + Map.size * 1, Map.getGroundLevel() + Map.size, Map.size, Map.size);
												TextureManager.render(31 * 23 + 1, this.house_x + Map.size * 2, Map.getGroundLevel() + Map.size, Map.size, Map.size);
												TextureManager.render(31 * 23 + 2, this.house_x + Map.size * 3, Map.getGroundLevel() + Map.size, Map.size, Map.size);

												TextureManager.render(31 * 24 + 0, this.house_x, Map.getGroundLevel() + Map.size * 2, Map.size, Map.size);
												TextureManager.render(31 * 24 + 1, this.house_x + Map.size * 1, Map.getGroundLevel() + Map.size + Map.size, Map.size, Map.size);
												TextureManager.render(31 * 24 + 1, this.house_x + Map.size * 2, Map.getGroundLevel() + Map.size + Map.size, Map.size, Map.size);
												TextureManager.render(31 * 24 + 2, this.house_x + Map.size * 3, Map.getGroundLevel() + Map.size * 2, Map.size, Map.size);
											}
										}
									}
								}
							} else {
								// Render the current streak as pink.
								FontManager.Font.PIXEL_REGULAR.render("" + 0, Color.PINK, (int) (Map.size * .06f), //$NON-NLS-1$
										ScreenUtil.screen_height - (int) (Map.size * 2.4f), (int) (Map.size * 0.4f), false, -1);
							}
						} else {
							// Render the current number of milks delivered as pink.
							FontManager.Font.PIXEL_REGULAR.render("" + 0, Color.PINK, //$NON-NLS-1$
									Map.size * 0.1f, ScreenUtil.screen_height - Map.size * 1.9f, (int) (Map.size * 0.8f), false, -1);
						}
					} else {
						// Render the current velocity as pink.
						FontManager.Font.PIXEL_REGULAR.render("" + MathUtil.round(this.velocity, 2), Color.PINK, //$NON-NLS-1$
								Map.size * 0.1f, ScreenUtil.screen_height - Map.size * 0.9f, (int) (Map.size * 0.8f), false, -1);
					}
				}
			}

		}

		StaticGUI.fillScreenWithColor(0.0f, 0.0f, 0.0f, 0.25f);

		renderLevel(this.total_clicks);

		if (this.total_clicks > 3) {
			if (this.total_clicks > 4) {
				StaticGUI.pause_button.render(ColorXv.WHITE);
			} else {
				StaticGUI.pause_button.render(ColorXv.PINK);
			}
		}
		StaticGUI.music_button.render(ColorXv.WHITE);
		StaticGUI.back_button.render(ColorXv.WHITE);
	}

	private static void renderLevel(int level) {
		switch (level) {
		case 0:
			StaticGUI.renderBlackMessage("Tap When Ready"); //$NON-NLS-1$
			break;
		case 1:
			TextureManager.render(19, Map.size * 3, Map.size * 1.5f, Map.size, Map.size);
			FontManager.Font.PIXEL_REGULAR.render("This is Darrean", ColorXv.WHITE, ScreenUtil.screen_width / 2, Map.size * 4, Map.size, true, -1); //$NON-NLS-1$
			break;
		case 2:
			FontManager.Font.PIXEL_REGULAR.render("He delievers milk", ColorXv.WHITE, ScreenUtil.screen_width / 2, Map.size * 4, Map.size, true, -1); //$NON-NLS-1$
			break;
		case 3:
			FontManager.Font.PIXEL_REGULAR.render("He has three lives and", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("gains lives for delivering consecutively", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 4:
			FontManager.Font.PIXEL_REGULAR.render("oh look a pause button", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("it's disabled right now", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 5:
			FontManager.Font.PIXEL_REGULAR.render("that's your velocity", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 6:
			FontManager.Font.PIXEL_REGULAR.render("that's how many milks", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("you have delivered", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 7:
			FontManager.Font.PIXEL_REGULAR.render("that's your current streak", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 8:
			FontManager.Font.PIXEL_REGULAR.render("a milk button?", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("click it to deliever milk", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("(not now please)", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 2.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 9:
			FontManager.Font.PIXEL_REGULAR.render("click the boot to run", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("from angry people", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("whose house you missed", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 2.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 10:
			FontManager.Font.PIXEL_REGULAR.render(":O oh wait", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4.5f), Map.size, true, -1); //$NON-NLS-1$
			FontManager.Font.PIXEL_REGULAR.render("political correctness", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 3.5f), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 11:
			FontManager.Font.PIXEL_REGULAR.render("there is chocolate milk", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4), Map.size, true, -1); //$NON-NLS-1$
			break;
		case 12:
			FontManager.Font.PIXEL_REGULAR.render("and strawberry!", ColorXv.WHITE, ScreenUtil.screen_width / 2, (int) (Map.size * 4), Map.size, true, -1); //$NON-NLS-1$
			break;
		default:
			break;
		}
	}

	private void renderArrowPointedAtFrom(int x2, int y2, int x1, int y1) {
		// 31 * 15 + 11
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(31 * 15 + 11), x1, y1, Map.size, Map.size, Map.size * 2, Map.size * 2, 1, 1,
				(int) (((MathUtil.getAngleFrom(new Vector2f(x2, y2), new Vector2f(x1, y1)))) * 180.0 / Math.PI) + 90 + 180, false);

	}

	@Override
	public void stateChangedToThis() {
		this.total_clicks = 0;
		Map.setCurrentScroll(0);
		for (int i = 0; i < this.ground_blocks.length; i++) {
			this.ground_blocks[i].setX(i * Map.size);
		}
	}

	@Override
	public void stateFinishedFadingInToExit() {
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
	}

	@Override
	public void stateFinishedFadingOut() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}