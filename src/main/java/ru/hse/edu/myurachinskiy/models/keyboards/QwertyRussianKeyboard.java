package ru.hse.edu.myurachinskiy.models.keyboards;

import ru.hse.edu.myurachinskiy.models.keys.CommandKey;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.models.keys.OrdinaryKey;
import ru.hse.edu.myurachinskiy.models.keys.TipKey;
import ru.hse.edu.myurachinskiy.predicativeSystem.PredictiveTextSystem;

import java.util.List;

public class QwertyRussianKeyboard extends Keyboard {
	
	private PredictiveTextSystem predictiveTextSystem;
	
    public QwertyRussianKeyboard() {
        super();
        this.keyboard = new Key[][]{
			{
				new TipKey(""), new TipKey(""), new TipKey(""),
			},
        	{
        		new CommandKey(CommandKey.COMMAND.TAB, 1),
        		new OrdinaryKey("й", "Й"), new OrdinaryKey("ц", "Ц"),
				new OrdinaryKey("у", "У"), new OrdinaryKey("к", "К"),
				new OrdinaryKey("е", "Е"), new OrdinaryKey("н", "Н"),
				new OrdinaryKey("г", "Г"), new OrdinaryKey("ш", "Ш"),
				new OrdinaryKey("щ", "Щ"), new OrdinaryKey("з", "З"),
				new OrdinaryKey("х", "Х"), new OrdinaryKey("ъ", "Ъ"),
			},
			{
				new OrdinaryKey("ф", "Ф"), new OrdinaryKey("ы", "Ы"),
				new OrdinaryKey("в", "В"), new OrdinaryKey("а", "А"),
				new OrdinaryKey("п", "П"), new OrdinaryKey("р", "Р"),
				new OrdinaryKey("л", "Л"), new OrdinaryKey("о", "О"),
				new OrdinaryKey("д", "Д"), new OrdinaryKey("ж", "Ж"),
				new OrdinaryKey("э", "Э"),
				new CommandKey(CommandKey.COMMAND.ENTER, 1)
			},
			{
				new CommandKey(CommandKey.COMMAND.SHIFT, 1),
				new OrdinaryKey("я", "Я"), new OrdinaryKey("ч", "Ч"),
				new OrdinaryKey("с", "С"), new OrdinaryKey("м", "М"),
				new OrdinaryKey("и", "И"), new OrdinaryKey("т", "Т"),
				new OrdinaryKey("ь", "Ь"), new OrdinaryKey("б", "Б"),
				new OrdinaryKey("ю", "Ю"),
				new CommandKey(CommandKey.COMMAND.BACKSPACE, 1)
			},
			{
				new CommandKey(CommandKey.COMMAND.CTRL, 1),
				new CommandKey(CommandKey.COMMAND.SPACE, 8)
			}
        };
    }
	
	@Override
	public void changeLastWordText(String newLastWord) {
		super.changeLastWordText(newLastWord);
		for (Key key : keyboard[0]) {
			TipKey tipKey = (TipKey) key;
			tipKey.setText("");
		}
	}
	
	public void changeTips() {
    	String pattern = "";
    	if (lastWord.length() >= 3) {
    		for (int i = 0; i < lastWord.length() - 3; i++) {
    			Character currentChar = lastWord.charAt(i);
    			if (Character.isUpperCase(currentChar))
					pattern += "[" + lastWord.charAt(i) + Character.toLowerCase(lastWord.charAt(i)) + "]";
				else {
					pattern += "[" + lastWord.charAt(i) + Character.toUpperCase(lastWord.charAt(i)) + "]";
				}
			}
    		String anyString = "[йцукенгшщзхъэждлорпавыфячсмитьбюёЙЦУКЕНГШЩЗХЪЭЖДЛОРПАВЫФЯЧСМИТЬБЮЁ]";
			pattern += anyString;
			pattern += anyString;
			pattern += anyString;
		}
    	List<String> tips = predictiveTextSystem.getWordsByPattern(pattern);
    	int i = 0;
    	for (String tip : tips) {
    		if (i < keyboard[0].length && keyboard[0][i] instanceof TipKey) {
				TipKey tipKey = (TipKey) keyboard[0][i];
				tipKey.setText(tip);
			} else
				break;
    		i++;
		}
	}
	
	public void setPredictiveTextSystem(PredictiveTextSystem predictiveTextSystem) {
		this.predictiveTextSystem = predictiveTextSystem;
	}
}
