package com.uladzislau.dairy_run.game_state;

import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.game_state.GameStateManager.STATE;
import com.uladzislau.dairy_run.manager.AudioManager;

public abstract class GameState {

	private final GameStateManager.STATE state;

	private AudioManager.MusicXv music;

	protected boolean first_update = true;
	protected boolean first_render = true;

	protected DairyRun dairy_run;

	public GameState(DairyRun dairy_run, GameStateManager.STATE state) {
		this.dairy_run = dairy_run;
		this.state = state;
	}

	public abstract void initialize();

	public abstract void update(float delta);

	public abstract void render();

	public abstract void stateChangedToThis();

	public abstract void stateFinishedFadingInToExit();

	public abstract void stateFinishedFadingInToEntrance();

	public abstract void stateFinishedFadingOut();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public STATE getState() {
		return this.state;
	}

	public void inStatePause() {
		// This method was created for the play state.
	}

	public AudioManager.MusicXv getMusic() {
		return this.music;
	}

	public void setMusic(AudioManager.MusicXv music) {
		this.music = music;
	}

}
