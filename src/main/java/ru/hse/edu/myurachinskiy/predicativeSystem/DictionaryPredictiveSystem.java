package ru.hse.edu.myurachinskiy.predicativeSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;


/**
 * Класс <code>DictionaryPredictiveSystem</code> представляет собой модель
 * предикативной системы со словарем.
 * 
 * @author Pavel Manakhov (manakhovpavel@gmail.com)
 *
 */
public class DictionaryPredictiveSystem extends Dictionary
		implements PredictiveTextSystem {
	
	static class PSNode extends Node {

		public PSNode(char character, int depth) {
			super(character, depth);
		}
		
		@Override
		protected Node newNode(char character, int depth) {
			return new PSNode(character, depth);
		}
		
		public TreeSet<String> getWordwormsByPattern(TreeSet<String> wordforms,
				String prefix, List<String> pattern) {
			
			// Если шаблон поиска пуст...
			if (pattern.isEmpty())
				return wordforms; // ...вернуть список как он есть
			
			// Получаем группу символов, соответсвующую номеру буквы в слове
			final String chracterGroup = pattern.get(depth);
			
			// Движемся по всем символам в группе
			for (int index = 0; index < chracterGroup.length(); index++) {
				
				// Находим соответсвующий узел
				final Node childNode = children.get(chracterGroup.charAt(index));
				
				// Если такая буква есть в текущей ветви...
				if (childNode != null) {
					if (depth == pattern.size() - 1) { // ...и мы достигли конца слова
						// Добавляем слово в результирующий набор
						if (childNode.frequency >= 0)
							wordforms.add(prefix + childNode.character);
					} else { // иначе - двигаемся дальше по шаблону
						((PSNode) childNode).getWordwormsByPattern(
								wordforms, prefix + childNode.character, pattern);
					}
				}
			}
			
			return wordforms;
		}
		
	}
	
	/**
	 * Создает модель предикативной системы с пустым словарем.
	 */
	DictionaryPredictiveSystem() { }
	
	public static DictionaryPredictiveSystem newInstance() {
		return new DictionaryPredictiveSystem();
	}
	
	@Override
	protected PSNode rootNode() {
		if (root == null)
			root = new PSNode(' ' /*символ корневого узла не имеет значения*/, 0);
		
		return (PSNode) root;
	}
	
	/**
	 * Возвращает упорядоченный по вероятности ввода (1-ый элемент -
	 * наиболее вероятный) список словоформ, длина которых равна
	 * длине шаблона поиска. Словоформы, имеющие одинаковую частоту,
	 * будут упорядочены по алфавиту.
	 * 
	 * @param regex шаблон поиска на основе регулярных выражений
	 *                вида <code>[мно][абвг]</code>.
	 * @return список словоформ по убыванию вероятности ввода. Если
	 *         шаблон поиска пуст, то метод вернет пустой immutable список.
	 */
	@Override
	public List<String> getWordsByPattern(String regex) {
		final String emptyPrefix = "";
		List<String> pattern = convertRegexPattern(regex);
		
		/* Создаем набор, элементы которого будут упорядочены по
		 * частоте употреблений и наполняем его
		 */
		TreeSet<String> wordformSet = rootNode().getWordwormsByPattern(
				new TreeSet<String>(new OrderByFrequency(this)),
				emptyPrefix,
				pattern);
		
		return Arrays.asList(wordformSet.toArray(new String[0]));
	}
	
	/**
	 * Возвращает список групп символов, содержащихся в <code>regex</code>.
	 * 
	 * @param regex шаблон поиска на основе регулярных выражений.
	 * @return список групп символов.
	 * @throws NullPointerException если <code>regex</code> равен <tt>null</tt>.
	 */
	protected static List<String> convertRegexPattern(String regex) {
		if (regex == null)
			throw new NullPointerException();
		
		// Если шаблон пуст...
		if (regex.isEmpty())
			return new ArrayList<String>(); // ...возвращаем пустой список
		
		// Проверка корректности шаблона
		if (!regex.matches("(\\[[\\p{L}\\p{M}\\p{N}_]+\\])+"))
			throw new IllegalArgumentException();
		
		/* Убираем первую квадратную скобку. Если этого не сделать,
		 * первым элементом списка будет пустая строка
		 */
		final String modifiedPattern = regex.replaceFirst("\\[", "");

		// Разбиваем шаблон поиска
		return Arrays.asList(modifiedPattern.split("[\\[\\]]+"));
	}
	
}
