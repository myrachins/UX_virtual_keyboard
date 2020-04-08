package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Класс представляет собой загрузчик словаря в "сыром" виде.
 */
@Root(name="dictionary")
public class XMLDictionaryPersister implements DictionaryPersister {
	
	private static final String FORMATTED_XML_HEADER = "<?xml version=\"1.0\" encoding=\"%s\"?>\n";
	
	@ElementMap(entry="word", key="wordform", attribute=true, inline=true)
	private Map<String, Integer> words;
	
	/**
	 * Создает пустой загрузчик словаря.
	 */
	private XMLDictionaryPersister() {
		this.words = new HashMap<String, Integer>();
	}
	
	/**
	 * Создает пустой загрузчик словаря.
	 * 
	 * @return экземпляр класса загрузчика.
	 */
	public static XMLDictionaryPersister newInstance() {
		return new XMLDictionaryPersister();
	}
	
	public static XMLDictionaryPersister newInstance(InputStream stream,
			Charset encoding) throws Exception {
		if (stream == null || encoding == null)
			throw new NullPointerException();
		
		final Serializer serializer = new Persister();
		return serializer.read(XMLDictionaryPersister.class, new InputStreamReader(
				stream, encoding));
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
	
	public void write(OutputStream stream, Charset encoding) throws Exception {
		if (stream == null || encoding == null)
			throw new NullPointerException();
		
		final Serializer serializer = new Persister();
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream, encoding);
		outputStreamWriter.write(String.format(FORMATTED_XML_HEADER, encoding.name()));
		outputStreamWriter.flush();
		
		serializer.write(this, outputStreamWriter);
	}

	@Override
	public Iterator<String> iterator() {
		final Set<String> wordforms = words.keySet(); 
		
		return wordforms.iterator();
	}

}
