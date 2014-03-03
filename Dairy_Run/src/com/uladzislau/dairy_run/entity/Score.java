package com.uladzislau.dairy_run.entity;

public class Score {

	private static int milk_high_score;
	private static float velocity_high_score;

	private static int current_milk_score;
	private static float current_velocity_score;

	public static int getMilkHighScore() {
		return milk_high_score;
	}

	public static float getVelocityHighScore() {
		return velocity_high_score;
	}

	public static int getCurrentMilkScore() {
		return current_milk_score;
	}

	public static void setCurrentMilkScore(int cms) {
		current_milk_score = cms;
		if (current_milk_score > milk_high_score) {
			milk_high_score = current_milk_score;
		}
	}

	public static float getCurrentVelocityScore() {
		return current_velocity_score;
	}

	public static void setCurrentVelocityScore(float cvs) {
		current_velocity_score = cvs;
		if (current_velocity_score > velocity_high_score) {
			velocity_high_score = current_velocity_score;
		}
	}

}
