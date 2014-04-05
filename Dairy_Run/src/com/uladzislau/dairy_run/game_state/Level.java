package com.uladzislau.dairy_run.game_state;

public class Level {

	public static final Level ENDLESS = new Level(true);
	
	public static void createEndlessLevel() {
		ENDLESS.setBeaten(false);
	}
	
	private String description;

	private boolean beaten = false;

	private int number_of_milks_needed_to_win;
	private int number_of_lives_needed;
	private boolean run_button_enabled;
	private boolean regular_milk_button_enabled;
	private boolean chocolate_milk_button_enabled;
	private boolean strawberry_milk_button_enabled;
	private boolean createChasers;
	private float velocity_needed;
	private float initial_velocity;

	private boolean bronze_recieved = false;
	private boolean silver_recieved = false;
	private boolean gold_recieved = false;

	private boolean unlocked = false;

	private boolean velocity_matters;

	public Level(boolean locked_or_not) {
		this.unlocked = locked_or_not;
	}

	public int getNumberOfMilksNeededToWin() {
		return this.number_of_milks_needed_to_win;
	}

	public void setNumberOfMilksNeededToWin(int number_of_milks_needed) {
		this.number_of_milks_needed_to_win = number_of_milks_needed;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBronzeRecieved() {
		return this.bronze_recieved;
	}

	public void setBronzeRecieved(boolean bronze_recieved) {
		this.bronze_recieved = bronze_recieved;
	}

	public boolean isSilverRecieved() {
		return this.silver_recieved;
	}

	public void setSilverRecieved(boolean silver_recieved) {
		this.silver_recieved = silver_recieved;
	}

	public boolean isGoldRecieved() {
		return this.gold_recieved;
	}

	public void setGoldRecieved(boolean gold_recieved) {
		this.gold_recieved = gold_recieved;
	}

	public float getInitialVelocity() {
		return this.initial_velocity;
	}

	public void setInitialVelocity(float initial_velocity) {
		this.initial_velocity = initial_velocity;
	}

	public boolean isBeaten() {
		return this.beaten;
	}

	public void setBeaten(boolean beaten) {
		this.beaten = beaten;
	}

	public void setThisLevelEqualToLevel(Level l) {
		this.description = l.getDescription();
		this.beaten = l.isBeaten();
		this.number_of_milks_needed_to_win = l.getNumberOfMilksNeededToWin();
		this.number_of_lives_needed = l.getNumberOfLivesNeeded();
		this.run_button_enabled = l.isRunButtonEnabled();
		this.regular_milk_button_enabled = l.isRegularMilkButtonEnabled();
		this.chocolate_milk_button_enabled = l.isChocolateMilkButtonEnabled();
		this.strawberry_milk_button_enabled = l.isStrawberryMilkButtonEnabled();
		this.createChasers = l.isCreateChasers();
		this.initial_velocity = l.initial_velocity;
		this.bronze_recieved = l.bronze_recieved;
		this.silver_recieved = l.silver_recieved;
		this.gold_recieved = l.gold_recieved;
		this.unlocked = l.isUnlocked();
		this.velocity_matters = l.isVelocityMatters();
		this.velocity_needed = l.getVelocityNeededToWin();
	}

}