package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class FontManager {

	public static final int _0 /*             */= 0x00;
	public static final int _1 /*             */= 0x01;
	public static final int _2 /*             */= 0x02;
	public static final int _3 /*             */= 0x03;
	public static final int _4 /*             */= 0x04;
	public static final int _5 /*             */= 0x05;
	public static final int _6 /*             */= 0x06;
	public static final int _7 /*             */= 0x07;
	public static final int _8 /*             */= 0x08;
	public static final int _9 /*             */= 0x09;
	public static final int KEY_9 = 0x0A;
	public static final int KEY_0 = 0x0B;
	public static final int KEY_MINUS = 0x0C; /* - on main keyboard */
	public static final int KEY_EQUALS = 0x0D;
	public static final int KEY_BACK = 0x0E; /* backspace */
	public static final int KEY_TAB = 0x0F;
	public static final int KEY_Q = 0x10;
	public static final int KEY_W = 0x11;
	public static final int KEY_E = 0x12;
	public static final int KEY_R = 0x13;
	public static final int KEY_T = 0x14;
	public static final int KEY_Y = 0x15;
	public static final int KEY_U = 0x16;
	public static final int KEY_I = 0x17;
	public static final int KEY_O = 0x18;
	public static final int KEY_P = 0x19;
	public static final int KEY_LBRACKET = 0x1A;
	public static final int KEY_RBRACKET = 0x1B;
	public static final int KEY_RETURN = 0x1C; /* Enter on main keyboard */
	public static final int KEY_LCONTROL = 0x1D;
	public static final int KEY_A = 0x1E;
	public static final int KEY_S = 0x1F;
	public static final int KEY_D = 0x20;
	public static final int KEY_F = 0x21;
	public static final int KEY_G = 0x22;
	public static final int KEY_H = 0x23;
	public static final int KEY_J = 0x24;
	public static final int KEY_K = 0x25;
	public static final int KEY_L = 0x26;
	public static final int KEY_SEMICOLON = 0x27;
	public static final int KEY_APOSTROPHE = 0x28;
	public static final int KEY_GRAVE = 0x29; /* accent grave */
	public static final int KEY_LSHIFT = 0x2A;
	public static final int KEY_BACKSLASH = 0x2B;
	public static final int KEY_Z = 0x2C;
	public static final int KEY_X = 0x2D;
	public static final int KEY_C = 0x2E;
	public static final int KEY_V = 0x2F;
	public static final int KEY_B = 0x30;
	public static final int KEY_N = 0x31;
	public static final int KEY_M = 0x32;
	public static final int KEY_COMMA = 0x33;
	public static final int KEY_PERIOD = 0x34; /* . on main keyboard */
	public static final int KEY_SLASH = 0x35; /* / on main keyboard */
	public static final int KEY_RSHIFT = 0x36;
	public static final int KEY_MULTIPLY = 0x37; /* * on numeric keypad */
	public static final int KEY_LMENU = 0x38; /* left Alt */
	public static final int KEY_LALT = KEY_LMENU; /* left Alt */
	public static final int KEY_SPACE = 0x39;
	public static final int KEY_CAPITAL = 0x3A;
	public static final int KEY_F1 = 0x3B;
	public static final int KEY_F2 = 0x3C;
	public static final int KEY_F3 = 0x3D;
	public static final int KEY_F4 = 0x3E;
	public static final int KEY_F5 = 0x3F;
	public static final int KEY_F6 = 0x40;
	public static final int KEY_F7 = 0x41;
	public static final int KEY_F8 = 0x42;
	public static final int KEY_F9 = 0x43;
	public static final int KEY_F10 = 0x44;
	public static final int KEY_NUMLOCK = 0x45;
	public static final int KEY_SCROLL = 0x46; /* Scroll Lock */
	public static final int KEY_NUMPAD7 = 0x47;
	public static final int KEY_NUMPAD8 = 0x48;
	public static final int KEY_NUMPAD9 = 0x49;
	public static final int KEY_SUBTRACT = 0x4A; /* - on numeric keypad */
	public static final int KEY_NUMPAD4 = 0x4B;
	public static final int KEY_NUMPAD5 = 0x4C;
	public static final int KEY_NUMPAD6 = 0x4D;
	public static final int KEY_ADD = 0x4E; /* + on numeric keypad */
	public static final int KEY_NUMPAD1 = 0x4F;
	public static final int KEY_NUMPAD2 = 0x50;
	public static final int KEY_NUMPAD3 = 0x51;
	public static final int KEY_NUMPAD0 = 0x52;
	public static final int KEY_DECIMAL = 0x53; /* . on numeric keypad */
	public static final int KEY_F11 = 0x57;
	public static final int KEY_F12 = 0x58;
	public static final int KEY_F13 = 0x64; /* (NEC PC98) */
	public static final int KEY_F14 = 0x65; /* (NEC PC98) */
	public static final int KEY_F15 = 0x66; /* (NEC PC98) */
	public static final int KEY_KANA = 0x70; /* (Japanese keyboard) */
	public static final int KEY_CONVERT = 0x79; /* (Japanese keyboard) */
	public static final int KEY_NOCONVERT = 0x7B; /* (Japanese keyboard) */
	public static final int KEY_YEN = 0x7D; /* (Japanese keyboard) */
	public static final int KEY_NUMPADEQUALS = 0x8D; /*
													 * = on numeric keypad (NEC
													 * PC98)
													 */
	public static final int KEY_CIRCUMFLEX = 0x90; /* (Japanese keyboard) */
	public static final int KEY_AT = 0x91; /* (NEC PC98) */
	public static final int KEY_COLON = 0x92; /* (NEC PC98) */
	public static final int KEY_UNDERLINE = 0x93; /* (NEC PC98) */
	public static final int KEY_KANJI = 0x94; /* (Japanese keyboard) */

	private static int[] x_coordinates = new int[94];
	{
		x_coordinates[_0] = 2;
		x_coordinates[_1] = 37;
	}
	private static int[] y_coordiates = new int[94];
	{
		y_coordiates[_0] = 6;
		y_coordiates[_1] = 5;
	}
	private static int[] x_widths = new int[94];
	{
		x_coordinates[_0] = 33;
		x_coordinates[_1] = 19;
	}
	private static int[] y_heights = new int[94];
	{
		y_coordiates[_0] = 36;
		y_coordiates[_1] = 37;
	}
	
	public static enum FONT {
		BUBBLE_FONT("bubble_font", "http://opengameart.org/content/platformer-art-replacement-gui-text", true, x_coordinates);

		private final String name;
		private final String source;

		private boolean isTexture;

		private int width;
		private int height;
		private Texture texture;
		private TextureRegion[] characters;

		FONT(String name, String source, boolean isTexture) {
			this.name = name;
			this.source = source;
			this.isTexture = isTexture;
			if (this.isTexture) {
				characters = new TextureRegion[94];
			}
		}
		
		FONT(String name, String source, boolean isTexture, int[] a) {
			this.name = name;
			this.source = source;
			this.isTexture = isTexture;
			if (this.isTexture) {
				characters = new TextureRegion[94];
			}
		}

		public void init() {
			if (this.isTexture) {
				if (this.texture == null) {
					this.texture = new Texture(Gdx.files.internal("data/font/" + name + ".png"));
				} else {
					StaticUtil.error("Font Texture Error", "You are trying to init " + this.name + " twice.");
				}
			}
		}

		public Texture getTexture() {
			return this.texture;
		}

		public String getSource() {
			return source;
		}

		public void dispose() {
			if (this.texture != null) {
				this.texture.dispose();
			}
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

	}

}
