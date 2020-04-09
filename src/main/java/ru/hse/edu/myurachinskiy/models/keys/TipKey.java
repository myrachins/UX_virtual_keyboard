package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

public class TipKey extends Key {
	
	public TipKey(String key) {
		super(key);
	}
	
	public TipKey(String key, String shiftedKey) {
		super(key, shiftedKey);
	}
	
	public TipKey(String key, String shiftedKey, double width) {
		super(key, shiftedKey, width);
	}
	
	@Override
	public void pressKey(Keyboard keyboard) {
		if (key.length() > 2) {
			keyboard.changeLastWordText(key);
		}
	}
	
	public void setText(String text) {
		this.key = text;
	}
	
}
