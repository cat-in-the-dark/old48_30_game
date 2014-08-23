package com.catinthedark.game.screen;

import java.util.HashMap;
import java.util.Map;

import com.catinthedark.game.Config;

public abstract class SwitchableScreen extends BasicScreen {

	public SwitchableScreen(Config conf) {
		super(conf);
	}

	private Map<Integer, Integer> bindingsMap = new HashMap<Integer, Integer>();
	private Integer nextHook = null;
	private Integer prevHook = null;

	public Bindings bindings = new Bindings();

	public class Bindings {
		public Bindings bind(int key, int index) {
			bindingsMap.put(key, index);
			return this;
		}

		public Bindings bindNext(int key) {
			nextHook = key;
			return this;
		}

		public Bindings bindPrev(int key) {
			prevHook = key;
			return this;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println("pressed key:" + keycode);
		Integer nextFrame = bindingsMap.get(keycode);
		if (nextFrame != null) {
			gotoFrame(nextFrame);
			return true;
		}

		if (nextHook != null)
			if (nextHook.equals(keycode)) {
				next();
				return true;
			}

		if (prevHook != null)
			if (prevHook.equals(keycode)) {
				prev();
				return true;
			}

		return false;
	}
}
