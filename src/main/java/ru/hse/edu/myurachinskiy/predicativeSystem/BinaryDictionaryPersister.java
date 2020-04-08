package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BinaryDictionaryPersister implements DictionaryPersister, Serializable {

	private static final long serialVersionUID = -2612519664358417466L;
	
	private Map<String, Integer> words;
	
	private BinaryDictionaryPersister() {
		this.words = new HashMap<String, Integer>();
	}
	
	public static BinaryDictionaryPersister newInstance() {
		return new BinaryDictionaryPersister();
	}
	
	public static BinaryDictionaryPersister newInstance(
			InputStream inputStream) throws Exception {
		if (inputStream == null)
			throw new NullPointerException();
		
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		return (BinaryDictionaryPersister) ois.readObject();
	}

	/**
	 * @throws NullPointerException если <code>wordform</code> равен <tt>null</tt>.
	 * @throws IllegalArgumentException если <code>wordform</code> пуст или
	 *         <code>frequency</code> меньше 1.
	 */
	@Override
	public boolean add(String wordform, long frequency) {
		if (wordform == null)
			throw new NullPointerException();
		
		if (wordform.isEmpty() || frequency < 1)
			throw new IllegalArgumentException();
		
		// Проверка наличия словоформы
		if (words.containsKey(wordform))
			return false;
		
		words.put(wordform, (int) frequency);
		
		return true;
	}
	
	/**
	 * @throws NullPointerException если <code>wordform</code> равен <tt>null</tt>.
	 */
	@Override
	public long getFrequency(String wordform) {
		if (wordform == null)
			throw new NullPointerException();
		
		/* Будем считать, что слово нулевой длины встречается
		 * в словаре 0 раз
		 */
		Integer freq = words.get(wordform);
		if (freq == null)
			return 0;
		
		return freq;
	}
	
	public void write(OutputStream outputStream) throws Exception {
		if (outputStream == null)
			throw new NullPointerException();
		
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		oos.writeObject(this);
	}
	
	@Override
	public Iterator<String> iterator() {
		final Set<String> wordforms = words.keySet(); 
		
		return wordforms.iterator();
	}

}
