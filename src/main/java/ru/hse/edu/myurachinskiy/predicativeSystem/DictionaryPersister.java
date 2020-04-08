package ru.hse.edu.myurachinskiy.predicativeSystem;

/**
 * Интерфейс <code>DictionaryPersister</code> представляет собой контракт
 * по которому его наследники кроме, собственно, словоформ должны
 * предоставлять информацию о частоте употребления этих словоформ.
 * 
 * @author Pavel Manakhov (manakhovpavel@gmail.com)
 *
 */
public interface DictionaryPersister extends Iterable<String> {
	
	/**
	 * Добавляет <code>wordform</code> с указанной частотой в словарь,
	 * если данной словоформы там прежде не было.
	 * 
	 * @param wordform словоформа.
	 * @param frequency частота указанной словоформы.
	 * @return <tt>true</tt>, если <code>wordform</code> была добавлена,
	 *         и <tt>false</tt> в противном случае.
	 */
	public boolean add(String wordform, long frequency);
	
	/**
	 * Возвращает число, которое соответсвует числу вхождений
	 * <code>wordform</code> в корпус, на основе которого
	 * был создан данный словарь.
	 * 
	 * @param wordform словоформа.
	 * @return частота указанной словоформы. Должен вернет 0,
	 *         если такой словоформы нет в словаре.
	 */
	public long getFrequency(String wordform);
	
}
