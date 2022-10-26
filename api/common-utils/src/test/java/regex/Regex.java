package regex;

import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^rm -rf");
        pattern.matcher("asasa rm -rf");
    }
}
