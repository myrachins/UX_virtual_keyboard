package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Класс <code>Dictionary</code> представляет собой словарь,
 * содержащий информацию о частоте употребления каждой словоформы.
 * <code>Dictionary</code> реализован на основе дерева и поэтому
 * обеспечивает быстрые операции доступа (O(1)) к данным
 * конкретной словоформы.
 * 
 * @author Pavel Manakhov (manakhovpavel@gmail.com)
 *
 */
public class Dictionary implements Iterable<String> {
	
	static class Node {
		
		private static final int INITIAL_CHILD_NODE_NUM = 2;
		
		protected final char character;
		
		/**
		 * Уровень узла.
		 */
		protected final int depth;
		
		/**
		 * Частота употребления словоформы.
		 */
		public long frequency;
		
		protected Map<Character, Node> children;
		
		
		public Node(char character, int depth) {
			this.character = character;
			this.depth = depth;
			
			children = new HashMap<Character, Node>(INITIAL_CHILD_NODE_NUM);
		}
		
		protected Node newNode(char character, int depth) {
			return new Node(character, depth);
		}
		
		public void put(String wordform, long frequency) {
			Node childNode = null;
			
			// Если буква уже есть...
			if (children.containsKey(wordform.charAt(depth)))
				childNode = children.get(wordform.charAt(depth)); // ...просто берем соответсвующий узел
			else { // иначе - создаем узел и добавляем его как потомка
				childNode = newNode(wordform.charAt(depth), depth + 1);
				
				children.put(childNode.character, childNode);
			}
			
			// Когда мы достигли конца словоформы...
			if (depth == wordform.length() - 1)
				childNode.frequency = frequency; // ...сохраняем число употреблений данной словоформы
			else // иначе - движемся дальше
				childNode.put(wordform, frequency);
		}
		
		public long get(String wordform) {
			// Находим узел с соответствующей буквой
			Node childNode = children.get(wordform.charAt(depth));
			
			// Если такого нет...
			if(childNode == null)
				return 0; // ...возвращаем нулевую частоту
			
			// Когда мы достигли конца словоформы...
			if (depth == wordform.length() - 1)
				return childNode.frequency; // ...возвращаем частоту словоформы
			else // иначе - движемся дальше
				return childNode.get(wordform);
		}
		
		public boolean containsWordform(String wordform) {
			return (get(wordform) != 0); 
		}
		
		public Set<String> wordformSet() {
			// Создаем набор, размер которого соответствует количеству словоформ
			Set<String> wordforms = new HashSet<String>();
			
			// Наполняем его
			return wordformSet(wordforms, "");
		}
		
		protected Set<String> wordformSet(Set<String> wordforms, String prefix) {
			Set<Character> characters = children.keySet();
			
			// Симметричный предупорядоченный обход
			for (Character letter : characters) {
				final Node childNode = children.get(letter);
				final String wordform = prefix + letter;
				
				// Если словоформа присутсвует в словаре...
				if (childNode.frequency > 0)
					wordforms.add(wordform); // ...добавляем ее в набор
				
				// Переходим к потомкам узла
				childNode.wordformSet(wordforms, wordform);
			}
			
			return wordforms;
		}
		
	}
	
	/**
	 * Значение <code>averageWordLength</code>, которое говорит о
	 * том, что необходимо перерассчитать среднюю длину слова
	 */
	private static final float NEED_RECALC = -1;

	/**
	 * Корневой узел древа, хранящего все данные словаря.
	 */
	protected Node root;
	
	/**
	 * Количество словоупотреблений.
	 */
	private long wordUsageNumber;
	
	/**
	 * Количество словоформ.
	 */
	private long wordformNumber;
	
	/**
	 * Средняя длина слова.
	 */
	private float averageWordLength;
	
	
	/**
	 * Создает пустой словарь.
	 */
	public Dictionary() { }
	
	/**
	 * Создает словарь на основе данных <code>persister</code>.
	 * 
	 * @param persister объект, загрузчик словаря.
	 */
	public Dictionary(DictionaryPersister persister) {
		fill(persister);
	}
	
	/**
	 * Копирует данные из словаря <code>persister</code> в дерево
	 * с корнем <code>root</code>. Использует для этого метод
	 * <code>add(String, long)</code>.
	 * 
	 * @param persister объект, загрузчик словаря.
	 */
	public void fill(DictionaryPersister persister) {
		if (persister == null)
			throw new NullPointerException();
		
		for (String wordform : persister)
			add(wordform, persister.getFrequency(wordform));
	}
	
	/**
	 * Возвращает корневой узел дерева. При первом вызове
	 * создает его.
	 * 
	 * @return корневой узел дерева словоформ.
	 */
	protected Node rootNode() {
		if (root == null)
			root = new Node(' ' /*символ корневого узла не имеет значения*/, 0);
		
		return root;
	}
	
	/**
	 * Добавляет <code>wordform</code> с указанной частотой в словарь,
	 * если данной словоформы там прежде не было.
	 * 
	 * @param wordform словоформа.
	 * @param frequency частота указанной словоформы.
	 * @return <tt>true</tt>, если <code>wordform</code> была добавлена,
	 *         и <tt>false</tt> в противном случае.
	 * @throws NullPointerException если <code>wordform</code> равен <tt>null</tt>.
	 * @throws IllegalArgumentException если <code>wordform</code> пуст или
	 *         <code>frequency</code> меньше 1.
	 */
	public boolean add(String wordform, long frequency) {
		if (wordform == null)
			throw new NullPointerException();
		
		if (wordform.isEmpty() || frequency < 1)
			throw new IllegalArgumentException();
		
		// Проверка наличия словоформы
		if (rootNode().containsWordform(wordform))
			return false;
		
		wordformNumber++;
		wordUsageNumber += frequency;
		
		/* Средняя длина слова изменилась. Помечаем на отложенный
		 * перерасчет
		 */
		averageWordLength = NEED_RECALC;
		
		rootNode().put(wordform, frequency);
		
		return true;
	}
	
	/**
	 * Возвращает вероятность ввода <code>wordform</code>.
	 * 	
	 * @param wordform словоформа.
	 * @return вероятность указанной словоформы. Вернет 0.0, если такой словоформы
	 *         нет в словаре.
	 * @throws NullPointerException если <code>wordform</code> равен <tt>null</tt>.
	 */
	public double getProbability(String wordform) {
		final long frequency = getFrequency(wordform);
		
		if (wordUsageNumber == 0)
			return 0.;
		else
			return (frequency / (double) wordUsageNumber);
	}
	
	/**
	 * Возвращает число, которое соответсвует числу вхождений
	 * <code>wordform</code> в корпус, на основе которого
	 * был создан данный словарь.
	 * 
	 * @param wordform словоформа.
	 * @return частота указанной словоформы. Вернет 0, если такой словоформы
	 *         нет в словаре.
	 * @throws NullPointerException если <code>wordform</code> равен <tt>null</tt>.
	 */
	public long getFrequency(String wordform) {
		if (wordform == null)
			throw new NullPointerException();
		
		/* Будем считать, что слово нулевой длины встречается
		 * в словаре 0 раз
		 */
		if (wordform.isEmpty())
			return 0;
		
		// FIXME Нет проверки на отсутсвие слова
		return rootNode().get(wordform);
	}
	
	
	/**
	 * Возвращает общее количество слов корпуса, на основе которого
	 * был создан данный словарь.
	 * 
	 * @return число словоупотреблений.
	 */
	public long getWordUsageNumber() {
		return wordUsageNumber;
	}
	
	/**
	 * Возвращает число уникальных слов корпуса, на основе которого
	 * был создан данный словарь.
	 * 
	 * @return число словоформ.
	 */
	public long getWordformNumber() {
		return wordformNumber;
	}
	
	
	/**
	 * Возвращает среднюю длину слов, рассчитанную с учетом
	 * вероятности каждой словоформы.
	 * 
	 * @return среднюю длину слова в словаре.
	 */
	public float getAverageWordLength() {
		// Если содержимое словаря было изменено...
		if (averageWordLength == -1) {
			averageWordLength = 0f;

			// ...перерассчитываем среднюю длину
			for (String wordform : this) {
				final double p = getProbability(wordform);
				
				averageWordLength += p * wordform.length();
			}
		}
		
		return averageWordLength;
	}

	/**
	 * Возвращает итератор по всем словоформам.
	 * 
	 * @return итератор.
	 */
	@Override
	public Iterator<String> iterator() {
		final Set<String> wordforms = rootNode().wordformSet();
		
		return wordforms.iterator();
	}
	
}

