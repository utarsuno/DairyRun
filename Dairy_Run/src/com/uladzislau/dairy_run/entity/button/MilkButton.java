package com.uladzislau.dairy_run.entity.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uladzislau.dairy_run.entity.Map;
import com.uladzislau.dairy_run.game_state.Play;
import com.uladzislau.dairy_run.manager.TextureManager;

public class MilkButton extends CircleButton {

	public static final short REGULAR = 31 * 6 + 20;
	public static final short CHOCOLATE = 31 * 6 + 21;
	public static final short STRAWBERRY = 31 * 6 + 22;

	private short milk_type;

	private Play play;

	public MilkButton(float x, float y, float radius, short type, Play play) {
		super(x, y, radius);
		this.play = play;
		this.milk_type = type;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		for (int i = 0; i < this.increments.length; i++) {
			if (this.increments[i]) {
				deliverMilk();
				this.increments[i] = false;
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		// Render the button.
		sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 0.9f);
		sb.draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(milk_type), this.getX() - Map.size / 2, this.getY() - Map.size / 2,
				Map.size, Map.size);
		sb.setColor(sb.getColor().r, sb.getColor().g, sb.getColor().b, 1.0f);
	}

	private void deliverMilk() {
		this.play.attemptToDeliver(milk_type);
	}

}
