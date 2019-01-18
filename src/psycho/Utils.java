package psycho;

public class Utils {

    public static String substringBefore(String text, String subString) {
        int i = text.indexOf(subString);
        if (i == -1) return null;
        return text.substring(0, i);
    }

    public static String substringBeforeLast(String text, String subString) {
        int i = text.lastIndexOf(subString);
        if (i == -1) return text;
        return text.substring(0, i);
    }

    public static String substringAfter(String text, String subString) {
        int i = text.indexOf(subString);
        if (i == -1) return null;
        return text.substring(i + subString.length());
    }

    public static String substringAfterLast(String text, String subString) {
        int i = text.lastIndexOf(subString);
        if (i == -1) return null;
        return text.substring(i + subString.length());
    }

    public static boolean isDigits(String value) {

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) return false;
        }
        return true;
    }
}
