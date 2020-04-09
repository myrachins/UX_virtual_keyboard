package ru.hse.edu.myurachinskiy.models.keys;

import javafx.scene.control.Button;
import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

public class TipKey extends Key {
	
	private Button buttonKey;
	
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
		buttonKey.setText(key);
	}
	
	public void setButtonKey(Button buttonKey) {
		this.buttonKey = buttonKey;
	}
}
