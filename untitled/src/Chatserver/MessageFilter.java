package Chatserver;


// It can replace the offensive words to asterick.
public class MessageFilter {
    private static final String[] offensiveWords = {
            "fucking", "fuck", "hell", "damn", "gandu"
    };

    public static String filter(String message) {
        for (String w : offensiveWords) { // simply use the for loop method with replace string.
            message = message.replaceAll("(?i)\\b" + w + "\\b", "***");
        }
        return message;
    }
}
