package com.uladzislau.dairy_run.information;

public class Score {

	private int milk_high_score;
	private float velocity_high_score;
	private int lowest_time_in_seconds;
	private int highest_time_in_seconds;

	private int current_milk_score;
	private float current_velocity_score;
	private int current_time_in_seconds;

	private static int minutes_played;
	private static int seconds_played;
	private static String time_played;

	public static String convertTimeInSecondsToClockTime(int seconds) {
		minutes_played = (int) (seconds / 60.0f);
		seconds_played = seconds - (minutes_played * 60);
		if (seconds_played < 10) {
			time_played = minutes_played + ":0" + seconds_played;
		} else {
			time_played = minutes_played + ":" + seconds_played;
		}
		return time_played;
	}

	public int getMilkHighScore() {
		return this.milk_high_score;
	}

	public float getVelocityHighScore() {
		return this.velocity_high_score;
	}

	public int getLowestTimeInSeconds() {
		return this.lowest_time_in_seconds;
	}

	public int getHighestTimeInSeconds() {
		return this.highest_time_in_seconds;
	}

	public int getCurrentMilkScore() {
		return this.current_milk_score;
	}

	public void setCurrentMilkScore(int current_milk_score) {
		this.current_milk_score = current_milk_score;
		if (this.current_milk_score > this.milk_high_score) {
			this.milk_high_score = this.current_milk_score;
		}
	}

	public float getCurrentVelocityScore() {
		return this.current_velocity_score;
	}

	public void setCurrentVelocityScore(float current_velocity_score) {
		this.current_velocity_score = current_velocity_score;
		if (this.current_velocity_score > this.velocity_high_score) {
			this.velocity_high_score = this.current_velocity_score;
		}
	}

	public int getCurrentTimeInSeconds() {
		return this.current_time_in_seconds;
	}

	public void setCurrentTimeInSeconds(int current_time_in_seconds) {
		this.current_time_in_seconds = current_time_in_seconds;
		if (this.current_time_in_seconds < this.lowest_time_in_seconds) {
			this.lowest_time_in_seconds = this.current_time_in_seconds;
		} else if (this.current_time_in_seconds > this.highest_time_in_seconds) {
			this.highest_time_in_seconds = this.current_time_in_seconds;
		}
	}

}