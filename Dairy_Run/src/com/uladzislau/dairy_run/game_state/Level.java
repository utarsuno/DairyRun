package com.uladzislau.dairy_run.game_state;

public class Level {

	private int number_of_milks_needed;
	private int number_of_lives_needed;
	private float velocity_needed;

	private boolean unlocked = false;

	private boolean velocity_matters;

	public Level(boolean locked_or_not) {
		this.unlocked = locked_or_not;
	}

	public int getNumberOfMilksNeeded() {
		return this.number_of_milks_needed;
	}

	public void setNumberOfMilksNeeded(int number_of_milks_needed) {
		this.number_of_milks_needed = number_of_milks_needed;
	}

	public int getNumberOfLivesNeeded() {
		return this.number_of_lives_needed;
	}

	public void setNumberOfLivesNeeded(int number_of_lives_needed) {
		this.number_of_lives_needed = number_of_lives_needed;
	}

	public float getVelocityNeeded() {
		return this.velocity_needed;
	}

	public void setVelocityNeeded(float velocity_needed) {
		this.velocity_needed = velocity_needed;
	}

	public boolean isVelocityMatters() {
		return this.velocity_matters;
	}

	public void setVelocityMatters(boolean velocity_matters) {
		this.velocity_matters = velocity_matters;
	}

	public boolean isUnlocked() {
		return this.unlocked;
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

}
