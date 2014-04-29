package com.uladzislau.dairy_run.game_state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.colorxv.ColorXv;
import com.uladzislau.dairy_run.game_state.GameStateManager.STATE;
import com.uladzislau.dairy_run.gui.StaticGUI;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.FontManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.world.Map;

public class Credits extends GameState {

	private String credits[];
	private float credits_y[];

	private float layer_y[];

	public Credits(DairyRun dairy_run, STATE state) {
		super(dairy_run, state);
	}

	private float y_offset;

	@Override
	public void initialize(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		this.shape_renderer = shapeRenderer;
		this.sprite_batch = batch;

		this.layer_y = new float[Map.number_of_vertical_blocks / 2 + 1];
		for (int i = 0; i < this.layer_y.length; i++) {
			this.layer_y[i] = Map.size * 2 * i;
		}

		this.credits_y = new float[27];
		for (int i = 0; i < this.credits_y.length; i++) {
			this.credits_y[i] = ScreenUtil.screen_height + Map.size * i;
		}

		this.credits = new String[27];
		this.credits[0] = "Thanks for playing";
		this.credits[1] = "";
		this.credits[2] = "Uladzislau Tarsunou";
		this.credits[3] = "Developer:";
		this.credits[4] = "Lead Programmer &";
		this.credits[5] = "";
		this.credits[6] = "Alfonso Figueroa";
		this.credits[7] = "Co-Founder:";
		this.credits[8] = "";
		this.credits[9] = "OpenGameArt.com";
		this.credits[10] = "Kenney from";
		this.credits[11] = "&";
		this.credits[12] = "Chris Ambroziak";
		this.credits[13] = "Art";
		this.credits[14] = "";
		this.credits[15] = "YouTube.com";
		this.credits[16] = "Holfix from";
		this.credits[17] = "Audio";
		this.credits[18] = "";
		this.credits[19] = "@ GitHub";
		this.credits[20] = "Source Code";
		this.credits[21] = "";
		this.credits[22] = "Anthony Nedumgottil";
		this.credits[23] = "Idea Consultant:";
		this.credits[24] = "";
		this.credits[25] = "libGDX";
		this.credits[26] = "Development Framework:";

	}

	@Override
	public void update(float delta) {

		this.y_offset = delta * 80;

		for (int i = 0; i < this.credits_y.length; i++) {
			this.credits_y[i] -= this.y_offset;
		}

		if (this.credits_y[this.credits_y.length - 1] < 0 - Map.size) {
			for (int i = 0; i < this.credits_y.length; i++) {
				this.credits_y[i] += this.credits_y.length * Map.size + ScreenUtil.screen_height;
			}
		}

		for (int i = 0; i < this.layer_y.length; i++) {
			this.layer_y[i] -= this.y_offset;
			if (this.layer_y[i] + Map.size * 2 < 0) {
				this.layer_y[i] += this.layer_y.length * Map.size * 2;
			}
		}

		TextureManager.Animation_Spritesheet.CLIMBING_STAIRS.update(delta);
		StaticGUI.music_button.update(delta);
		StaticGUI.back_button.update(delta);
	}

	@Override
	public void render() {
		this.sprite_batch.begin();

		for (int i = 0; i < this.layer_y.length; i++) {
			for (int j = 0; j < Map.number_of_horizontal_blocks_off_edge_included; j++) {
				this.sprite_batch.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.CREDITS_TILE), Map.size * 2 * j,
						(int) (this.layer_y[i]), Map.size * 2, Map.size * 2);
			}
			this.sprite_batch.draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.LADDER), Map.size, (int) (this.layer_y[i]),
					Map.size * 2, Map.size * 2);
		}

		for (int i = 0; i < this.credits.length; i++) {

			// if (Math.round((-this.total_y_offset + this.credits.length * Map.size * .75f + Map.size * 1.0f * i)) <
			// ScreenUtil.screen_height
			// && Math.round((-this.total_y_offset + this.credits.length * Map.size * .75f + Map.size * 1.0f * i)) > 0 - Map.size) {
			// FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.credits[i], ColorXv.WHITE, (int) (Map.size * 3.5f),
			// Math.round((-this.total_y_offset + this.credits.length * Map.size * .75f + Map.size * 1.0f * i)), (int) (Map.size * .75f),
			// false,
			// ScreenUtil.screen_width - Map.size);
			// }

			FontManager.FONT.PIXEL_REGULAR.render(this.sprite_batch, this.credits[i], ColorXv.WHITE, (int) (Map.size * 3.5f), (int) this.credits_y[i],
					(int) (Map.size * .75f), false, ScreenUtil.screen_width - Map.size);

		}

		this.sprite_batch.draw(TextureManager.Animation_Spritesheet.CLIMBING_STAIRS.getCurrentFrame(), Map.size, Map.size, Map.size * 2, Map.size * 2);

		StaticGUI.music_button.render(this.sprite_batch, ColorXv.ORANGE);
		StaticGUI.back_button.render(this.sprite_batch, ColorXv.ORANGE);
	}

	@Override
	public void stateChangedToThis() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingInToExit() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingInToEntrance() {
		// Intentionally left blank.
	}

	@Override
	public void stateFinishedFadingOut() {
		// Intentionally left blank.
	}

	@Override
	public void pause() {
		// Intentionally left blank.
	}

	@Override
	public void resume() {
		// Intentionally left blank.
	}

	@Override
	public void dispose() {
		// Intentionally left blank.
	}

}