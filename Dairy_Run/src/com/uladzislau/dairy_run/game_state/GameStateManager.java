package com.uladzislau.dairy_run.game_state;

import java.util.Stack;

import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.manager.AudioManager;
import com.uladzislau.dairy_run.manager.InputManager;
import com.uladzislau.dairy_run.manager.ResourceManager;
import com.uladzislau.dairy_run.manager.TextureManager;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class GameStateManager {

	public static final byte PREVIOUS_STATE = -2;
	public static final byte TERMINATE = -1;
	public static final byte MAIN_MENU = 0;
	public static final byte LEVEL_SELECTOR = 1;
	public static final byte PLAY = 2;
	public static final byte OPTIONS = 3;

	public static boolean transitioning_states;
	public static boolean fading_out;

	public GameState current_state;
	private GameState main_menu;
	private GameState level_selector;
	private GameState play;
	private GameState options;

	private Stack<Byte> state_history;

	private DeltaTimer transitioning_states_timer;

	private ResourceManager resourceManager;
	
	private AudioManager audioManager;

	public GameStateManager(DairyRun dr, ResourceManager rm, AudioManager audioManager) {
		this.resourceManager = rm;
		this.transitioning_states_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 250);
		this.main_menu = new MainMenu(dr, GameStateManager.MAIN_MENU);
		this.play = new Play(dr, GameStateManager.PLAY);
		this.level_selector = new LevelSelector(dr, GameStateManager.LEVEL_SELECTOR, (Play) this.play);
		this.options = new Options(dr, GameStateManager.OPTIONS);
		this.options.initialize(rm.getShapeRenderer(), rm.getSpriteBatch());
		this.main_menu.initialize(rm.getShapeRenderer(), rm.getSpriteBatch());
		this.level_selector.initialize(rm.getShapeRenderer(), rm.getSpriteBatch());
		this.play.initialize(rm.getShapeRenderer(), rm.getSpriteBatch());
		this.options.initialize(rm.getShapeRenderer(), rm.getSpriteBatch());
		this.current_state = this.main_menu;
		this.current_state.stateChangedToThis();
		this.state_history = new Stack<Byte>();
		this.audioManager = audioManager;
		this.audioManager.sendGameStateManager(this);
		GameStateManager.transitioning_states = false;
		GameStateManager.fading_out = false;
	}

	public void update(float delta) {
		if (GameStateManager.transitioning_states) {
			this.transitioning_states_timer.update(delta);
			if (this.transitioning_states_timer.isFinished()) {
				if (GameStateManager.fading_out) {
					InputManager.setIgnoreInput(false);
					GameStateManager.transitioning_states = false;
					this.current_state.stateFinishedFadingOut();
					AudioManager.setMusicLevel(1.0f);
				} else {
					this.current_state.stateFinishedFadingInToExit();
					this.actuallyChangeState();
					this.transitioning_states_timer.reset();
					GameStateManager.fading_out = true;
				}
			} else {
				if (GameStateManager.fading_out) {
					AudioManager.setMusicLevel(this.transitioning_states_timer.percentComplete());
				} else {
					AudioManager.setMusicLevel(1.0f - this.transitioning_states_timer.percentComplete());
				}
			}
		}

		this.current_state.update(delta);
	}

	public void render() {
		this.current_state.render();

		if (GameStateManager.fading_out) {
			this.resourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f - this.transitioning_states_timer.percentComplete());
		} else {
			this.resourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, this.transitioning_states_timer.percentComplete());
		}
		this.resourceManager.getSpriteBatch().draw(TextureManager.SPRITESHEET.PIXEL_SPRITESHEET.getFrame(31 * 4 + 2), 0, 0,
				ScreenUtil.screen_width, ScreenUtil.screen_height);

		this.resourceManager.getSpriteBatch().end();
		this.resourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@SuppressWarnings("boxing")
	public void changeState(byte state_id) {
		AudioManager.SOUND.TRANSITION_00.playSound();
		transitioning_states = true;
		this.transitioning_states_timer.reset();
		InputManager.setIgnoreInput(true);
		this.state_to_change_to = state_id;
		if (this.state_to_change_to != GameStateManager.PREVIOUS_STATE) {
			this.state_history.push(this.current_state.getID());
		}
		GameStateManager.fading_out = false;
	}

	private byte state_to_change_to;

	@SuppressWarnings("boxing")
	private void actuallyChangeState() {
		switch (this.state_to_change_to) {
		case PREVIOUS_STATE:
			if (this.state_history.isEmpty()) {
				DairyRun.exit();
				break;
			}
			switch (this.state_history.pop()) {
			case MAIN_MENU:
				this.current_state = this.main_menu;
				break;
			case LEVEL_SELECTOR:
				this.current_state = this.level_selector;
				break;
			case PLAY:
				this.current_state = this.play;
				break;
			case OPTIONS:
				this.current_state = this.options;
				break;
			default:
				break;
			}
			break;
		case TERMINATE:
			DairyRun.exit();
			break;
		case MAIN_MENU:
			this.current_state = this.main_menu;
			break;
		case LEVEL_SELECTOR:
			this.current_state = this.level_selector;
			break;
		case PLAY:
			this.current_state = this.play;
			break;
		case OPTIONS:
			this.current_state = this.options;
			break;
		default:
			break;
		}
		AudioManager.pauseAllMusic();
		this.current_state.stateChangedToThis();
	}

	public void pauseCurrentState() {
		this.current_state.pause();
	}

	public void resumeCurrentState() {
		this.current_state.resume();
	}

	public void clearHistoryStates() {
		this.state_history.clear();
	}

	public GameState getState(byte state_id) {
		switch (state_id) {
		case PLAY:
			return this.play;
		default:
			break;
		}
		return this.current_state;
	}

	public void resumeMusicForCurrentState() {
		System.out.println("resume");
		if (this.current_state == this.main_menu) {
			if (AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.isPaused()) {
				AudioManager.MUSIC.TEMP_MAIN_MENU_MUSIC.play();
			}
		} else if (this.current_state == this.level_selector) {
			if (AudioManager.MUSIC.LEVEL_SELECTOR_MUSIC.isPaused()) {
				AudioManager.MUSIC.LEVEL_SELECTOR_MUSIC.play();
			}
		} else if (this.current_state == this.options) {
			if (AudioManager.MUSIC.TEMP_OPTIONS.isPaused()) {
				AudioManager.MUSIC.TEMP_OPTIONS.play();
			}
		} else if (this.current_state == this.play) {
			if (AudioManager.MUSIC.TEMP_MUSIC.isPaused()) {
				AudioManager.MUSIC.TEMP_MUSIC.play();
			}
		}
	}

}