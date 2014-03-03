package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.uladzislau.dairy_run.math_utility.DeltaTimer;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class AudioManager {

	/* Sound Files Supported: WAV, MP3, OGG */

	private static boolean audio_on = true;
	protected static boolean music_on = true;
	protected static boolean sound_on = true;

	private static boolean AUDIO_ON = true;
	private static boolean MUSIC_ON = true;
	private static boolean SOUND_ON = true;

	private static float audio_level;
	private static float music_level;
	private static float sound_level;

	public static boolean music_fading_in = false;
	public static boolean music_fading_out = false;
	private static DeltaTimer fade_timer;

	public enum SOUND implements Resource {
		COMPLETED("completed", "http://opengameart.org/content/completion-sound"), COIN_ECHO("coin_echo",
				"http://opengameart.org/content/picked-coin-echo"), PAIN_ONE("pain" + java.io.File.separator + "pain_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), PAIN_TWO("pain" + java.io.File.separator + "pain_jack_02",
				"http://opengameart.org/content/fps-placeholder-sounds"), PAIN_THREE("pain" + java.io.File.separator + "pain_jack_03",
				"http://opengameart.org/content/fps-placeholder-sounds"), DEATH_ONE("death" + java.io.File.separator + "death_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), DEATH_TWO("death" + java.io.File.separator + "death_jack_02",
				"http://opengameart.org/content/fps-placeholder-sounds"), JUMP("jumping" + java.io.File.separator + "boing_jack_01",
				"http://opengameart.org/content/fps-placeholder-sounds"), LAND("jumping" + java.io.File.separator + "land",
				"http://opengameart.org/content/fps-placeholder-sounds"), POP("pop",
				"http://opengameart.org/content/fps-placeholder-sounds"), INTERFACE_00("beep" + java.io.File.separator + "interface_00",
				"Created with BFxr."), INTERFACE_01("beep" + java.io.File.separator + "interface_01",
				"http://www.freesound.org/people/LloydEvans09/sounds/185828/");

		private final String name;
		private final String source;
		private Sound sound;
		private boolean initialized;

		SOUND(String name, String source) {
			this.name = name;
			this.source = source;
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.sound == null) {
				this.sound = Gdx.audio.newSound(Gdx.files.internal("data" + java.io.File.separator + "audio" + java.io.File.separator
						+ "sound" + java.io.File.separator + this.name + ".mp3"));
				this.initialized = true;
			} else {
				StaticUtil.error("Audio Error", "You are trying to init " + this.name + " twice.");
			}
		}

		public void playSound() {
			if (sound_on) {
				if (this.initialized) {
					this.sound.play();
				} else {
					StaticUtil.error("Audio", this.name + " was told to be played but has not yet been initialized.");
				}
			}
		}

		public void playSound(float volume) {
			if (sound_on) {
				this.sound.play(volume);
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
			return source;
		}

		public boolean isInitialized() {
			return initialized;
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

	}

	public enum MUSIC implements Resource {
		TEMP_MUSIC("HolFix - Pixel Parade", "get it later lol"), TEMP_MAIN_MENU_MUSIC("HolFix - Happy Moment Remix",
				"Holflix, get it later though"), ;
		private final String name;
		private final String source;
		private boolean paused;
		private Music music;
		private static DeltaTimer fade_timer;
		private boolean initialized;

		MUSIC(String name, String source) {
			this.name = name;
			this.source = source;
			this.paused = false;
			this.initialized = false;
		}

		@Override
		public void initialize() {
			if (this.music == null) {
				this.music = Gdx.audio.newMusic(Gdx.files.internal("data" + java.io.File.separator + "audio" + java.io.File.separator
						+ "music" + java.io.File.separator + name + ".mp3"));
				initialized = true;
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

		public void play(float volume) {
			this.music.play();
			this.music.setVolume(volume);
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
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static void init() {
		setAudioLevel(1.0f);
		setMusicLevel(1.0f);
		setSoundLevel(1.0f);
		fade_timer = new DeltaTimer(DeltaTimer.RUN_ONCE, 1000);
	}

	public static void update(int delta) {
		// float fade;
		// if ((music_fading_in || music_fading_out) &&
		// !fade_timer.isFinished()) {
		// fade_timer.update(delta);
		// if (music_fading_in) {
		// fade = fade_timer.percentComplete();
		// } else {
		// fade = 1.0f - fade_timer.percentComplete();
		// }
		// if (main_menu_music.isPlaying()) {
		// main_menu_music.setVolume(fade);
		// }
		// if (competitive_music != null && competitive_music.isPlaying()) {
		// competitive_music.setVolume(fade);
		// }
		// }
		// if (fade_timer.isFinished()) {
		// if (music_fading_in) {
		// fade = 1.0f;
		// music_fading_in = false;
		// } else {
		// fade = 0.0f;
		// music_fading_out = false;
		// }
		// if (main_menu_music.isPlaying()) {
		// main_menu_music.setVolume(fade);
		// if (fade == 0.0f) {
		// main_menu_music.stop();
		// }
		// }
		// if (competitive_music != null && competitive_music.isPlaying()) {
		// competitive_music.setVolume(fade);
		// if (fade == 0.0f) {
		// competitive_music.stop();
		// }
		// }
		// fade_timer.reset();
		// }
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
		for (MUSIC music : MUSIC.values()) {
			if (music.isInitialized() && music.isPlaying()) {
				music.setVolume(music_level);
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
		return AUDIO_ON;
	}

	public static void setAudioOn(boolean b) {
		AUDIO_ON = b;
		if (!AUDIO_ON) {
			for (MUSIC music : MUSIC.values()) {
				if (music.isInitialized() && music.isPlaying()) {
					music.setPaused(true);
					music.pause();
				}
			}
		} else {
			for (MUSIC music : MUSIC.values()) {
				if (music.isInitialized() && music.isPaused()) {
					music.setPaused(false);
					music.play();
				}
			}
		}
	}

	public static boolean isMusicOn() {
		return MUSIC_ON;
	}

	public static void setMusicOn(boolean b) {
		MUSIC_ON = b;
		if (!MUSIC_ON) {
			for (MUSIC music : MUSIC.values()) {
				if (music.isInitialized() && music.isPlaying()) {
					music.pause();
				}
			}
		} else {
			for (MUSIC music : MUSIC.values()) {
				if (music.isPaused()) {
					music.play();
					music.setPaused(true);
				}
			}
		}
	}

	public static void dispose() {
		for (SOUND sound : SOUND.values()) {
			sound.dispose();
		}
		for (MUSIC music : MUSIC.values()) {
			music.dispose();
		}
	}

	private static void startAllMusic() {
		for (MUSIC music : MUSIC.values()) {
			if (music.isInitialized() && music.isPaused()) {
				music.play();
			}
		}
	}

	public static void pauseAllMusic() {
		for (MUSIC music : MUSIC.values()) {
			if (music.isInitialized() && music.isPlaying()) {
				music.pause();
			}
		}
	}

	public static void resumeAllMusic() {
		for (MUSIC music : MUSIC.values()) {
			if (music.isInitialized() && music.isPaused()) {
				music.setPaused(false);
				music.play();
			}
		}
	}

	public static void stopAllMusic() {
		for (MUSIC music : MUSIC.values()) {
			if (music.isInitialized() && music.isPlaying()) {
				music.stop();
			}
		}
	}

	public static String getInfo() {
		String info = "Audio On: " + AUDIO_ON + "\t" + "Music On: " + MUSIC_ON + "\t" + "Sound On: " + SOUND_ON + "\t" + "Audio Level: "
				+ audio_level + "\t" + "Sound Level: " + sound_level + "\t" + "Music Level: " + music_level;
		for (MUSIC music : MUSIC.values()) {
			if (music.isPlaying()) {
				info += "\t" + music.name();
			}
		}
		return info;
	}

	//
	// public static void playMusicFadeIn(byte music, int duration) {
	// music_fading_in = true;
	// fade_timer.setDuration(duration);
	// playMusic(music, 0.0f);
	// }
	//
	// public static void playMusicFadeOut(byte music, int duration) {
	// music_fading_out = true;
	// fade_timer.setDuration(duration);
	// playMusic(music, 0.0f);
	// }

	public static void setSoundOn(boolean b) {
		sound_on = b;
	}

	public static void inverseMusic() {
		music_on ^= true;
		if (music_on) {
			startAllMusic();
		} else {
			pauseAllMusic();
		}
	}

	public static void inverseSound() {
		sound_on ^= true;
	}

	public static void inverseAudio() {
		inverseMusic();
		inverseSound();
	}

}