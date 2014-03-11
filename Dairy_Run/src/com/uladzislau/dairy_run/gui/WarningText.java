package com.uladzislau.dairy_run.gui;

import java.awt.Color;

import com.uladzislau.dairy_run.math_utility.DeltaTimer;

public class WarningText {
	
	private int x;
	private int y;
	private String warningText;
	private boolean shake;
	private int x_shake_amount;
	private int y_shake_amount;
	private DeltaTimer shake_timer;
	private Color first_color;
	private Color second_color;
	private DeltaTimer color_timer;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getWarningText() {
		return warningText;
	}
	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}
	public boolean isShake() {
		return shake;
	}
	public void setShake(boolean shake) {
		this.shake = shake;
	}
	public int getX_shake_amount() {
		return x_shake_amount;
	}
	public void setX_shake_amount(int x_shake_amount) {
		this.x_shake_amount = x_shake_amount;
	}
	public int getY_shake_amount() {
		return y_shake_amount;
	}
	public void setY_shake_amount(int y_shake_amount) {
		this.y_shake_amount = y_shake_amount;
	}
	public DeltaTimer getShake_timer() {
		return shake_timer;
	}
	public void setShake_timer(DeltaTimer shake_timer) {
		this.shake_timer = shake_timer;
	}
	public Color getFirst_color() {
		return first_color;
	}
	public void setFirst_color(Color first_color) {
		this.first_color = first_color;
	}
	public Color getSecond_color() {
		return second_color;
	}
	public void setSecond_color(Color second_color) {
		this.second_color = second_color;
	}
	public DeltaTimer getColor_timer() {
		return color_timer;
	}
	public void setColor_timer(DeltaTimer color_timer) {
		this.color_timer = color_timer;
	}
	
	
	
}
