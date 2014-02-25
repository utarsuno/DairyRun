package com.uladzislau.dairy_run.manager;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.uladzislau.dairy_run.DairyRun;
import com.uladzislau.dairy_run.information.ScreenUtil;
import com.uladzislau.dairy_run.math.Vector2i;
import com.uladzislau.dairy_run.utility.StaticUtil;

public class InputManager implements InputProcessor, GestureListener {

	public static boolean ignore_input;

	public static int scroll;

	public static Vector2i pointers[];
	public static boolean pointersDown[];
	public static boolean pointersDragging[];
	
	private final DairyRun dairy_run;

	public InputManager(DairyRun dairy_run) {
		this.dairy_run = dairy_run;
		ignore_input = false;
		pointers = new Vector2i[8];
		for (int i = 0; i < 8; i++) {
			pointers[i] = new Vector2i();
		}
		pointersDown = new boolean[8];
		for (int i = 0; i < 8; i++) {
			pointersDown[i] = false;
		}
		pointersDragging = new boolean[8];
		for (int i = 0; i < 8; i++) {
			pointersDragging[i] = false;
		}
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		StaticUtil.log("input", "" + x);
		System.out.println(x);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	public static final int A = 29, B = 30, C = 31, D = 32, E = 33, F = 34, G = 35, H = 36, I = 37, J = 38, K = 39, L = 40, M = 41, N = 42,
			O = 43, P = 44, Q = 45, R = 46, S = 47, T = 48, U = 49, V = 50, W = 51, X = 52, Y = 53, Z = 54, UP = 19, LEFT = 21, DOWN = 20,
			RIGHT = 22, BACKSPACE = 67, LEFT_CONTROL = 129, RIGHT_CONTROL = 130, ESCAPE = 131;

	public static boolean isKeyDown(int keycode) {
		return keyDown[keycode];
	}

	private static boolean keyDown[] = new boolean[256];

	@Override
	public boolean keyDown(int keycode) {
		// System.out.println(keycode);

		keyDown[keycode] = true;

		if (!ignore_input) {
			switch (keycode) {
			case ESCAPE:
				this.dairy_run.changeState(DairyRun.TERMINATE);
				break;
			case BACKSPACE:
				this.dairy_run.changeState(DairyRun.PREVIOUS_STATE);
				break;
			case Keys.BACK:
				this.dairy_run.changeState(DairyRun.PREVIOUS_STATE);
				break;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// System.out.println(keycode);
		if (keyDown[LEFT_CONTROL] || keyDown[RIGHT_CONTROL]) {
			if (keyDown[M]) {
				AudioManager.inverseMusic();
			}
			if (keyDown[S]) {
				AudioManager.inverseSound();
			}
			if (keyDown[A]) {
				AudioManager.inverseAudio();
			}
		}
		keyDown[keycode] = false;
		return false;
	}

	public static final char SPACE_CHARACTER = 32, M_CHARACTER = 109;

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!ignore_input) {
			pointers[pointer].x = screenX;
			pointers[pointer].y = ScreenUtil.screen_height - screenY;
			pointersDown[pointer] = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!ignore_input) {
			pointersDown[pointer] = false;
		}
		pointersDragging[pointer] = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (!ignore_input) {
			if (pointer == 0) {
				pointers[pointer].x = screenX;
				pointers[pointer].y = ScreenUtil.screen_height - screenY;
				pointersDown[pointer] = true;
			}
			pointersDragging[pointer] = true;
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (!ignore_input) {
			pointers[0].x = screenX;
			pointers[0].y = ScreenUtil.screen_height - screenY;
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		scroll = amount;
		return true;
	}

}
