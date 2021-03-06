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

	public enum STATE {
		PREVIOUS_STATE, TERMINATE, MAIN_MENU, LEVEL_SELECTOR, PLAY, OPTIONS, TUTORIAL, CREDITS;
	}

	public static boolean transitioning_states;
	public static boolean fading_out;

	public GameState current_state;
	private GameState main_menu;
	private GameState level_selector;
	private GameState play;
	private GameState options;
	private GameState tutorial;
	private GameState credits;

	private Stack<STATE> state_history;

	private DeltaTimer transitioning_states_timer;

	public GameStateManager(DairyRun dr, AudioManager audioManager) {
		this.transitioning_states_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 200);
		this.main_menu = new MainMenu(dr, STATE.MAIN_MENU);
		this.play = new Play(dr, STATE.PLAY);
		this.level_selector = new LevelSelector(dr, STATE.LEVEL_SELECTOR, (Play) this.play);
		this.level_selector.setMusic(AudioManager.MusicXv.LEVEL_SELECTOR);
		this.options = new Options(dr, STATE.OPTIONS);
		this.tutorial = new Tutorial(dr, STATE.TUTORIAL);
		this.credits = new Credits(dr, STATE.CREDITS);
		
		this.credits.setMusic(AudioManager.MusicXv.CREDITS_MUSIC);
		this.options.setMusic(AudioManager.MusicXv.TEMP_OPTIONS);
		this.options.initialize();
		this.main_menu.setMusic(AudioManager.MusicXv.MAIN_MENU);
		this.main_menu.initialize();
		this.level_selector.initialize();
		this.tutorial.setMusic(AudioManager.MusicXv.TUTORIAL_MUISC);
		this.tutorial.initialize();
		this.credits.initialize();
		resetEndless();
		this.play.setMusic(AudioManager.MusicXv.TEMP_MUSIC);
		this.play.initialize();
		this.options.initialize();
		this.current_state = this.main_menu;
		this.current_state.stateChangedToThis();
		this.state_history = new Stack<STATE>();
		GameStateManager.transitioning_states = false;
		GameStateManager.fading_out = false;
	}

	private float music_level;
	private boolean music_level_grabbed = false;

	public void update(float delta) {
		if (GameStateManager.transitioning_states) {
			if (!this.music_level_grabbed) {
				this.music_level = AudioManager.getMusicLevel();
				this.music_level_grabbed = true;
			}
			this.transitioning_states_timer.update(delta);
			if (this.transitioning_states_timer.isFinished()) {
				if (GameStateManager.fading_out) {
					InputManager.setIgnoreInput(false);
					GameStateManager.transitioning_states = false;
					this.current_state.stateFinishedFadingOut();
				} else {
					this.current_state.stateFinishedFadingInToExit();
					this.actuallyChangeState();
					this.transitioning_states_timer.reset();
					GameStateManager.fading_out = true;
				}
			} else {
				if (GameStateManager.fading_out) {
					AudioManager.setMusicLevel(this.transitioning_states_timer.percentComplete() * this.music_level);
				} else {
					// Fading in.
					AudioManager.setMusicLevel((1.0f - this.transitioning_states_timer.percentComplete()) * this.music_level);
				}
			}
		} else {
			this.music_level_grabbed = false;
		}

		this.current_state.update(delta);
	}

	public void render() {

		ResourceManager.getSpriteBatch().begin();

		this.current_state.render();

		if (GameStateManager.fading_out) {
			ResourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f - this.transitioning_states_timer.percentComplete());
		} else {
			ResourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, this.transitioning_states_timer.percentComplete());
		}
		ResourceManager.getSpriteBatch().draw(TextureManager.Spritesheet.PIXEL_SPRITESHEET.getFrame(TextureManager.BLACK), 0, 0, ScreenUtil.screen_width,
				ScreenUtil.screen_height);

		ResourceManager.getSpriteBatch().end();
		ResourceManager.getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void changeState(GameStateManager.STATE state) {
		AudioManager.SoundXv.TRANSITION_00.playSound();
		transitioning_states = true;
		if (this.current_state.getMusic().isPlaying()) {
			this.current_state.getMusic().pause();
		}
		this.transitioning_states_timer.reset();
		InputManager.setIgnoreInput(true);
		this.state_to_change_to = state;
		if (this.state_to_change_to != STATE.PREVIOUS_STATE) {
			this.state_history.push(this.current_state.getState());
		}
		GameStateManager.fading_out = false;
	}

	private GameStateManager.STATE state_to_change_to;

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
			case TUTORIAL:
				this.current_state = this.tutorial;
				break;
			case CREDITS:
				this.current_state = this.credits;
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
		case TUTORIAL:
			this.current_state = this.tutorial;
			break;
		case CREDITS:
			this.current_state = this.credits;
			break;
		default:
			break;
		}
		this.current_state.stateChangedToThis();
		if (!AudioManager.isAudioOn() && !AudioManager.isMusicOn()) {
			this.current_state.getMusic().play();
			this.current_state.getMusic().pause();
		} else {
			this.current_state.getMusic().play();
		}
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

	public GameState getState(GameStateManager.STATE state) {
		switch (state) {
		case PLAY:
			return this.play;
		default:
			break;
		}
		return this.current_state;
	}

	public void inStatePause() {
		if (this.current_state == this.play) {
			this.play.inStatePause();
		}
	}

	public void resetEndless() {
		((Play) this.play).setLevel(Level.ENDLESS);
	}

}