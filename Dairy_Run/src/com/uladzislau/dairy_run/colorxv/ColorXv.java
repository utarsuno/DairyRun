package com.uladzislau.dairy_run.colorxv;

import com.uladzislau.dairy_run.math.Dice;

public class ColorXv {

	public static final ColorXv WHITE = new ColorXv(1.0f, 1.0f, 1.0f);
	public static final ColorXv RED = new ColorXv(1.0f, 0.0f, 0.0f);
	public static final ColorXv INDIAN_RED = new ColorXv(205.0f / 255.0f, 92.0f / 255.0f, 92.0f / 255.0f);
	public static final ColorXv ORANGE = new ColorXv(1.0f, 0.78f, 0.0f);
	public static final ColorXv YELLOW = new ColorXv(1.0f, 1.0f, 0.0f);
	public static final ColorXv LIGHT_GOLDRENROD_YELLOW = new ColorXv(250.f / 255.0f, 250.0f / 255.0f, 210.0f / 255.0f);
	public static final ColorXv GREEN = new ColorXv(0.0f, 1.0f, 0.0f);
	public static final ColorXv LIGHT_GREEN = new ColorXv(0.4f, 1.0f, 0.4f);
	public static final ColorXv BLUE = new ColorXv(0.0f, 0.0f, 1.0f);
	public static final ColorXv DARK_BLUE = new ColorXv(0.0f, 0.0f, 139.0f / 255.0f);
	public static final ColorXv TITLE_BLUE = new ColorXv(6.0f / 255.0f, 7.0f / 255.0f, 170f / 255.0f, 50.0f / 255.0f);
	public static final ColorXv DARK_TEAL = new ColorXv(0.0f, 157f / 255f, 1.0f);
	public static final ColorXv TEAL = new ColorXv(0.733333333f, 1.0f, 1.0f);
	public static final ColorXv SLATE_BLUE = new ColorXv(131.0f / 255.0f, 111.0f / 255.0f, 1.0f);
	public static final ColorXv PURPLE = new ColorXv(160.0f / 255.0f, 32.0f / 255.0f, 240.0f / 255.0f);
	public static final ColorXv BROWN = new ColorXv(139.0f / 255.0f, 69.0f / 255.0f, 19.0f / 255.0f);
	public static final ColorXv GRAY = new ColorXv(190.0f / 255.0f, 190.0f / 255.0f, 190.0f / 255.0f, 1.0f);
	public static final ColorXv BLACK = new ColorXv(0.0f, 0.0f, 0.0f, 1.0f);

	private float r;
	private float g;
	private float b;
	private float a;

	public ColorXv(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	public ColorXv(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public ColorXv() {
		this.r = 1.0f;
		this.g = 1.0f;
		this.b = 1.0f;
		this.a = 1.0f;
	}

	public ColorXv(ColorXv colorXv) {
		this.r = colorXv.r;
		this.g = colorXv.g;
		this.b = colorXv.b;
		this.a = colorXv.a;
	}

	public void setColorToFrom(ColorXv startingColorXv, ColorXv endingColorXv, float percentage) {
		if (startingColorXv.getR() == endingColorXv.getR()) {
			setR(startingColorXv.getR());
		} else if (startingColorXv.getR() > endingColorXv.getR()) {
			setR(endingColorXv.getR() + (startingColorXv.getR() - endingColorXv.getR()) * (percentage));
		} else {
			setR(endingColorXv.getR() - (endingColorXv.getR() - startingColorXv.getR()) * percentage);
		}
		if (startingColorXv.getG() == endingColorXv.getG()) {
			setG(startingColorXv.getG());
		} else if (startingColorXv.getG() > endingColorXv.getG()) {
			setG(endingColorXv.getG() + (startingColorXv.getG() - endingColorXv.getG()) * percentage);
		} else {
			setG(endingColorXv.getG() - (endingColorXv.getG() - startingColorXv.getG()) * percentage);
		}
		if (startingColorXv.getB() == endingColorXv.getB()) {
			setB(startingColorXv.getB());
		} else if (startingColorXv.getB() > endingColorXv.getB()) {
			setB(endingColorXv.getB() + (startingColorXv.getB() - endingColorXv.getB()) * percentage);
		} else {
			setB(endingColorXv.getB() - (endingColorXv.getB() - startingColorXv.getB()) * percentage);
		}
		if (startingColorXv.getA() == endingColorXv.getA()) {
			setA(startingColorXv.getA());
		} else if (startingColorXv.getA() > endingColorXv.getA()) {
			setA(endingColorXv.getA() + (startingColorXv.getA() - endingColorXv.getA()) * percentage);
		} else {
			setA(endingColorXv.getA() - (endingColorXv.getA() - startingColorXv.getA()) * percentage);
		}
	}

	public static ColorXv lighterVersion(ColorXv colorXv, float percentage_lighter) {
		return new ColorXv(colorXv.r - percentage_lighter * (1.0f - colorXv.r), colorXv.g - percentage_lighter * (1.0f - colorXv.g), colorXv.b
				- percentage_lighter * (1.0f - colorXv.b));
	}

	@Override
	public String toString() {
		return "R: " + this.r + "\tG: " + this.g + "\tB: " + this.b + "\tA: " + this.a;
	}

	public void setTo(ColorXv colorXv) {
		this.r = colorXv.r;
		this.g = colorXv.g;
		this.b = colorXv.b;
		this.a = colorXv.a;
	}

	public float getR() {
		return this.r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return this.g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return this.b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getA() {
		return this.a;
	}

	public void setA(float a) {
		this.a = a;
	}

	public static ColorXv getRandomColor() {
		return new ColorXv(Dice.get_Random_Float_From_Min_To_Max(0.0f, 1.0f), Dice.get_Random_Float_From_Min_To_Max(0.0f, 1.0f),
				Dice.get_Random_Float_From_Min_To_Max(0.0f, 1.0f));
	}

	public ColorXv withAlphaOf(float alpha) {
		return new ColorXv(r, g, b, alpha);
	}

}