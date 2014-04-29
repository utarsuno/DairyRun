package com.uladzislau.dairy_run.game_state;

import com.uladzislau.dairy_run.entity.GroundBlock;
import com.uladzislau.dairy_run.information.Score;
import com.uladzislau.dairy_run.manager.AudioManager;

public class Level {

	public static final Level ENDLESS = new Level(true);

	public static void createEndlessLevel() {
		ENDLESS.setBeaten(false);
		ENDLESS.setDescription("ENDLESS");
		ENDLESS.setBeaten(false);
		ENDLESS.setInitialVelocity(5f);
		ENDLESS.setVelocityMatters(true);
		ENDLESS.setVelocityNeededToWin(0);
		ENDLESS.setNumberOfMilksNeededToWin(-1);
		ENDLESS.setUnlocked(true);
		ENDLESS.setRunButtonEnabled(true);
		ENDLESS.setCreateChasers(false);
		ENDLESS.setRegularMilkButtonEnabled(true);
		ENDLESS.setChocolateMilkButtonEnabled(true);
		ENDLESS.setStrawberryMilkButtonEnabled(true);
		ENDLESS.setGroundTheme(GroundBlock.Theme.GRASS);
		ENDLESS.setShowTimer(true);
		ENDLESS.setPowerUpsGainedAt(25, 50, 100);
		ENDLESS.setScoresNeededToGainOneLife(25, 50, 100, 200, 400, 800, 1600, 3200, 6400, 12800);
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
	private AudioManager.MusicXv music;
	private GroundBlock.Theme ground_theme;
	private boolean bronze_recieved = false;
	private boolean silver_recieved = false;
	private boolean gold_recieved = false;
	private boolean unlocked = false;
	private boolean velocity_matters = false;
	private boolean show_timer = false;
	private Score score;
	private int power_up_first_acheived_every_consecutive_amount;
	private int power_up_second_acheived_every_consecutive_amount;
	private int power_up_third_acheived_every_consecutive_amount;
	private int scores_needed_to_gain_one_life[];

	public Level(boolean locked_or_not) {
		this.unlocked = locked_or_not;
		this.setScore(new Score());
		this.scores_needed_to_gain_one_life = new int[10];
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

	public GroundBlock.Theme getGroundTheme() {
		return this.ground_theme;
	}

	public void setGroundTheme(GroundBlock.Theme ground_theme) {
		this.ground_theme = ground_theme;
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
		this.ground_theme = l.getGroundBlockTheme();
	}

	public void setPauseOnFirstHouseReached(String string) {
	}

	public GroundBlock.Theme getGroundBlockTheme() {
		return this.ground_theme;
	}

	public AudioManager.MusicXv getMusic() {
		return this.music;
	}

	public void setMusic(AudioManager.MusicXv music) {
		this.music = music;
	}

	public boolean isShowTimer() {
		return this.show_timer;
	}

	public void setShowTimer(boolean show_timer) {
		this.show_timer = show_timer;
	}

	public Score getScore() {
		return this.score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void setScoresNeededToGainOneLife(int i, int j, int k, int l, int m, int n, int o, int p, int q, int r) {
		this.scores_needed_to_gain_one_life[0] = i;
		this.scores_needed_to_gain_one_life[1] = j;
		this.scores_needed_to_gain_one_life[2] = k;
		this.scores_needed_to_gain_one_life[3] = l;
		this.scores_needed_to_gain_one_life[4] = m;
		this.scores_needed_to_gain_one_life[5] = n;
		this.scores_needed_to_gain_one_life[6] = o;
		this.scores_needed_to_gain_one_life[7] = p;
		this.scores_needed_to_gain_one_life[8] = q;
		this.scores_needed_to_gain_one_life[9] = r;
	}

	public int[] getScoresNeededToGainOneLife() {
		return this.scores_needed_to_gain_one_life;
	}

	public void setPowerUpsGainedAt(int first, int second, int third) {
		this.power_up_first_acheived_every_consecutive_amount = first;
		this.power_up_second_acheived_every_consecutive_amount = second;
		this.power_up_third_acheived_every_consecutive_amount = third;
	}

	public int getPowerUpFirstAcheivedEveryConsecutiveAmount() {
		return power_up_first_acheived_every_consecutive_amount;
	}

	public void setPowerUpFirstAcheivedEveryConsecutiveAmount(int power_up_first_acheived_every_consecutive_amount) {
		this.power_up_first_acheived_every_consecutive_amount = power_up_first_acheived_every_consecutive_amount;
	}

	public int getPowerSecondAcheivedEveryConsecutiveAmount() {
		return power_up_second_acheived_every_consecutive_amount;
	}

	public void setPowerSecondAcheivedEveryConsecutiveAmount(int power_second_acheived_every_consecutive_amount) {
		this.power_up_second_acheived_every_consecutive_amount = power_second_acheived_every_consecutive_amount;
	}

	public int getPowerUpThirdAcheivedEveryConsecutiveAmount() {
		return power_up_third_acheived_every_consecutive_amount;
	}

	public void setPowerUpThirdAcheivedEveryConsecutiveAmount(int power_up_third_acheived_every_consecutive_amount) {
		this.power_up_third_acheived_every_consecutive_amount = power_up_third_acheived_every_consecutive_amount;
	}

}