package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.util.List;

public interface PredictiveTextSystem {

	public List<String> getWordsByPattern(String regex);
	
}
