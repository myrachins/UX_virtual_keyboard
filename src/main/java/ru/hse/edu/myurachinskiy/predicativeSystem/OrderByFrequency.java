package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.util.Comparator;

/**
 * <code>OrderByFrequency</code> представляет собой вспомогательный класс,
 * который используется для упорядочения элементов в таких классах как
 * {@link java.util.TreeSet TreeSet} и {@link java.util.TreeMap TreeMap}.
 * 
 * @author Pavel Manakhov (manakhovpavel@gmail.com)
 *
 */
class OrderByFrequency implements Comparator<String> {
	
	private final Dictionary dict;
	
	/**
	 * Создает класс на основе словаря.
	 * 
	 * @param dictionary словарь.
	 */
	public OrderByFrequency(Dictionary dictionary) {
		if (dictionary == null)
			throw new NullPointerException();
		
		this.dict = dictionary;
	}

	@Override
	public int compare(String w1, String w2) {
		if (dict.getFrequency(w1) == dict.getFrequency(w2)) {
			/* 
			 * Небольшая хитрость, связанная с реализацией коллекций
			 * TreeMap и TreeSet. Возвращаемое число, по сути, определяет
			 * порядок словоформ в группе с одинаковой частотой:
			 *    1 - в алфавитном порядке;
			 *   -1 - в обратном алфавитном порядке.   
			 */
			return 1;
		}
		else if (dict.getFrequency(w1) < dict.getFrequency(w2))
			return 1;
		return -1;
	}
	
}
