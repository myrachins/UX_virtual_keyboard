package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OurDictionaryPersister implements DictionaryPersister {
	
	private Map<String, Integer> words;
	
	private OurDictionaryPersister() {
		words = new HashMap<String, Integer>();
	}
	
	public static OurDictionaryPersister newInstance() {
		return new OurDictionaryPersister();
	}
	
	public static OurDictionaryPersister newInstance(InputStream inputStream,
														Charset encoding) throws Exception {
		if (inputStream == null || encoding == null)
			throw new NullPointerException();
		
		OurDictionaryPersister res = new OurDictionaryPersister();
		BufferedReader br = new BufferedReader(
			new InputStreamReader(inputStream, encoding));
		String line;
		while ((line = br.readLine()) != null) {
			if(!line.isEmpty()) {
				String[] data = line.split(" ");
				if (data.length != 1)
					res.words.put(data[0], 1);
			}
			
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
