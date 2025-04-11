package twitter.tweets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFilter {

    // Метод для фильтрации текста
    public static String filterText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        // Удаляем упоминания (слова, начинающиеся с @)
        text = text.replaceAll("@\\w+", "");
        text = text.replaceAll("#\\w+", "");
        text = text.replaceAll("\\b(?:https?://)?(?:www\\.)?\\S+\\.\\S+\\b", "");

        // Оставляем только слова, начинающиеся с букв
        StringBuilder filteredText = new StringBuilder();
        Matcher matcher = Pattern.compile("\\b[a-zA-Z][a-zA-Z0-9]*\\b").matcher(text);

        while (matcher.find()) {
            if (filteredText.length() > 0) {
                filteredText.append(" ");
            }
            filteredText.append(matcher.group());
        }

        return filteredText.toString();
    }
}
