package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleDictionaryPersister implements DictionaryPersister {
	
	private Map<String, Integer> words;
	
	private SimpleDictionaryPersister() {
		words = new HashMap<String, Integer>();
	}
	
	public static SimpleDictionaryPersister newInstance() {
		return new SimpleDictionaryPersister();
	}
	
	public static SimpleDictionaryPersister newInstance(InputStream inputStream,
			Charset encoding) throws Exception {
		if (inputStream == null || encoding == null)
			throw new NullPointerException();
		
		SimpleDictionaryPersister res = new SimpleDictionaryPersister();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(inputStream, encoding));
		String line;
		while ((line = br.readLine()) != null) {
			final String[] data = line.split(" ");
			res.words.put(data[0], Integer.valueOf(data[1]));
		}
		
		return res;
	}

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
	
	public void write(OutputStream outputStream, Charset encoding)
			throws Exception {
		if (outputStream == null || encoding == null)
			throw new NullPointerException();
		
		OutputStreamWriter osw = new OutputStreamWriter(
				new BufferedOutputStream(outputStream), encoding);
		for (String s : this)
			osw.write(String.format("%s %d\n", s, words.get(s)));
		osw.flush();
	}
	
	@Override
	public Iterator<String> iterator() {
		final Set<String> wordforms = words.keySet(); 
		
		return wordforms.iterator();
	}

}
