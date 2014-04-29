package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class AudioManager {

	/* Sound Files Supported: WAV, MP3, OGG */

	// Sound to add ----- http://opengameart.org/content/bubbles-pop

	private static boolean audio_on = true;
	protected static boolean music_on = true;
	protected static boolean sound_on = true;

	private static float audio_level;
	private static float music_level;
	private static float sound_level;

	public static boolean music_fading_in = false;
	public static boolean music_fading_out = false;

	public enum SoundXv implements Resource {
		INTERFACE_00("beep" + java.io.File.separator + "interface_00", "Created with BFxr."), INTERFACE_01("beep" + java.io.File.separator + "interface_01",
				"http://www.freesound.org/people/LloydEvans09/sounds/185828/"), COMPLETED("completed", "http://opengameart.org/content/completion-sound"), COIN_ECHO(
				"coin_echo", "http://opengameart.org/content/picked-coin-echo"), PAIN_ONE("pain" + java.io.File.separator + "pain_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), PAIN_TWO("pain" + java.io.File.separator + "pain_jack_02",
				"http://opengameart.org/content/fps-placeholder-sounds"), PAIN_THREE("pain" + java.io.File.separator + "pain_jack_03",
				"http://opengameart.org/content/fps-placeholder-sounds"), DEATH_ONE("death" + java.io.File.separator + "death_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), DEATH_TWO("death" + java.io.File.separator + "death_jack_02",
				"http://opengameart.org/content/fps-placeholder-sounds"), JUMP("jumping" + java.io.File.separator + "boing_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), LAND("jumping" + java.io.File.separator + "land",
				"http://opengameart.org/content/fps-placeholder-sounds"), POP("pop", "http://opengameart.org/content/fps-placeholder-sounds"), MILK("milk"
				+ java.io.File.separator + "milk", "darreon"), MILK2("milk" + java.io.File.separator + "milk2", "darreon"), MILK3("milk"
				+ java.io.File.separator + "milk3", "darreon"), TRANSITION_00("transition" + java.io.File.separator + "transition_00",
				"https://www.freesound.org/people/Halgrimm/sounds/195463/"), VICTORY("victory" + java.io.File.separator + "Lively Meadow Victory Fanfare",
				"http://www.matthewpablo.com/archives/many-new-tracks-1-29-14"), DOORBELL("doorbell",
				"https://www.freesound.org/people/Corsica_S/sounds/163730/");

		private final String name;
		private final String source;
		private Sound sound;
		private boolean initialized;
		private boolean muted;

		SoundXv(String name, String source) {
			this.name = name;
			this.source = source;
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.sound == null) {
				this.sound = Gdx.audio.newSound(Gdx.files.internal("data" + java.io.File.separator + "audio" + java.io.File.separator + "sound"
						+ java.io.File.separator + this.name + ".mp3"));
				this.initialized = true;
			} else {
				StaticUtil.error("Audio Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public void playSound() {
			if (audio_on && sound_on && !this.muted) {
				if (this.initialized) {
					this.sound.play(AudioManager.sound_level);
				} else {
					StaticUtil.error("Audio", this.name + " was told to be played but has not yet been initialized.");
				}
			}
		}

		@Override
		public void dispose() {
			if (this.sound != null) {
				this.initialized = false;
				this.sound.dispose();
				this.sound = null;
			} else {
				StaticUtil.error("Sound Error: ", this.name + " has already been disposed.");
			}
		}

		@Override
		public String getSource() {
			return this.source;
		}

		public boolean isInitialized() {
			return this.initialized;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String currentStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isMuted() {
			return this.muted;
		}

		public void setMuted(boolean muted) {
			this.muted = muted;
		}

	}

	public enum MusicXv implements Resource {
		TEMP_MUSIC("HolFix - Pixel Parade", "get it later lol"), TEMP_MAIN_MENU_MUSIC("HolFix - Happy Moment Remix", "Holflix, get it later though"), LEVEL_SELECTOR_MUSIC(
				"Lively Meadow", "http://www.matthewpablo.com/archives/many-new-tracks-1-29-14"), TEMP_OPTIONS("HolFix - Jeremy the Different Giraffe Theme",
				"http://www.youtube.com/watch?v=5Alrqovf9E8&index=9&list=PLyBvLDmLwbZsHRnniCV5cJ9FM2WLR7a7I"), CREDITS_MUSIC("HolFix - Finally Home",
				"https://www.youtube.com/watch?v=AE3lJjlXS94&list=UU2_gl7WoSGsg7rLvBPTqtEw");
		private final String name;
		private final String source;
		private boolean paused;
		private Music music;
		private static DeltaTimer fade_timer;
		private boolean initialized;
		private boolean muted;

		MusicXv(String name, String source) {
			this.name = name;
			this.source = source;
			this.paused = false;
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.music == null) {
				this.music = Gdx.audio.newMusic(Gdx.files.internal("data" + java.io.File.separator + "audio" + java.io.File.separator + "music"
						+ java.io.File.separator + this.name + ".mp3"));
				this.initialized = true;
			} else {
				StaticUtil.error("Audio Error", "You are trying to init + " + this.name + " twice.");
			}
		}

		public boolean isPlaying() {
			return this.music.isPlaying();
		}

		public boolean isPaused() {
			return this.paused;
		}

		public void setPaused(boolean b) {
			this.paused = b;
		}

		public void setVolume(float volume) {
			this.music.setVolume(volume);
		}

		public void play() {
			this.paused = false;
			this.music.setVolume(music_level);
			this.music.play();
		}

		public void pause() {
			this.music.pause();
			this.paused = true;
		}

		public void stop() {
			this.music.stop();
		}

		@Override
		public void dispose() {
			if (this.music != null) {
				this.initialized = false;
				this.music.dispose();
				this.music = null;
			} else {
				StaticUtil.error("Music Error: ", this.name + " has already been disposed.");
			}
		}

		public boolean isInitialized() {
			return this.initialized;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getSource() {
			return this.source;
		}

		@Override
		public String currentStatus() {
			return "Initialized: " + this.initialized + "\tMuted: " + this.muted + "\tisPlaying: " + this.isPlaying() + "\tisPaused: " + this.isPaused();
		}

		public boolean isMuted() {
			return this.muted;
		}

		public void setMuted(boolean muted) {
			this.muted = muted;
		}

		public void loop(boolean b) {
			this.music.setLooping(b);
		}

	}

	public static void update(int delta) {
	}

	public static float getAudioLevel() {
		return audio_level;
	}

	public static void setAudioLevel(float audio_level) {
		AudioManager.audio_level = audio_level;
	}

	public static float getMusicLevel() {
		return music_level;
	}

	public static void setMusicLevel(float music_level) {
		AudioManager.music_level = music_level;
		for (int i = 0; i < MusicXv.values().length; i++) {
			if (MusicXv.values()[i].isInitialized() && MusicXv.values()[i].isPlaying()) {
				MusicXv.values()[i].setVolume(music_level);
			}
		}
	}

	public static float getSoundLevel() {
		return sound_level;
	}

	public static void setSoundLevel(float sound_level) {
		AudioManager.sound_level = sound_level;
	}

	public static boolean isAudioOn() {
		return audio_on;
	}

	public static boolean isMusicOn() {
		if (!audio_on) {
			return false;
		}
		return music_on;
	}

	public static void setMusicOn(boolean b) {
		music_on = b;
		if (!music_on) {
			for (MusicXv music : MusicXv.values()) {
				if (music.isInitialized() && music.isPlaying()) {
					music.pause();
				}
			}
		} else {
			for (MusicXv music : MusicXv.values()) {
				if (music.isPaused()) {
					music.play();
					music.setPaused(true);
				}
			}
		}
	}

	public static void dispose() {
		for (SoundXv sound : SoundXv.values()) {
			sound.dispose();
		}
		for (MusicXv music : MusicXv.values()) {
			music.dispose();
		}
	}

	public static void toggleAllAudio() {
		audio_on ^= true;
		if (audio_on) {
			music_on = true;
			sound_on = true;
		} else {
			music_on = false;
			sound_on = false;
		}
	}

}