package com.uladzislau.dairy_run.game_state;

public class Level {

	private int number_of_milks_needed;
	private int number_of_lives_needed;
	private boolean run_button_enabled;
	private boolean regular_milk_button_enabled;
	private boolean chocolate_milk_button_enabled;
	private boolean strawberry_milk_button_enabled;
	private boolean createChasers;
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

	public float getVelocityNeededToWin() {
		return this.velocity_needed;
	}

	public void setVelocityNeededToWin(float velocity_needed) {
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

	public boolean isRunButtonEnabled() {
		return this.run_button_enabled;
	}

	public void setRunButtonEnabled(boolean b) {
		this.run_button_enabled = b;
	}

	public boolean isRegularMilkButtonEnabled() {
		return this.regular_milk_button_enabled;
	}

	public void setRegularMilkButtonEnabled(boolean b) {
		this.regular_milk_button_enabled = b;
	}

	public boolean isChocolateMilkButtonEnabled() {
		return this.chocolate_milk_button_enabled;
	}

	public void setChocolateMilkButtonEnabled(boolean b) {
		this.chocolate_milk_button_enabled = b;
	}

	public boolean isStrawberryMilkButtonEnabled() {
		return this.strawberry_milk_button_enabled;
	}

	public void setStrawberryMilkButtonEnabled(boolean b) {
		this.strawberry_milk_button_enabled = b;
	}

	public boolean isCreateChasers() {
		return this.createChasers;
	}

	public void setCreateChasers(boolean createChasers) {
		this.createChasers = createChasers;
	}

}