package ru.hse.edu.myurachinskiy.models.keyboards;

import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.models.keys.OrdinaryKey;

public class QwertyRussianKeyboard extends Keyboard {
    public QwertyRussianKeyboard() {
        super();
        this.keyboard = new OrdinaryKey[][]{
        	{
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
				new OrdinaryKey("э", "Э")
			},
			{
				new OrdinaryKey("я", "Я"), new OrdinaryKey("ч", "Ч"),
				new OrdinaryKey("с", "С"), new OrdinaryKey("м", "М"),
				new OrdinaryKey("и", "И"), new OrdinaryKey("т", "Т"),
				new OrdinaryKey("ь", "Ь"), new OrdinaryKey("б", "Б"),
				new OrdinaryKey("ю", "Ю")
			}
        };
    }
}
