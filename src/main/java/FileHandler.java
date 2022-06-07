import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    private static final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    private FileHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static String customChapter(int chapter) {
        StringBuilder builder = new StringBuilder();
        String line = "#-----------------------------------------#";
        builder.append(line).append("\n");

        try (InputStream inputStream = classloader.getResourceAsStream("TaoTeChing.txt")) {

            //Initialize a String with the text book.
            String text = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);

            //Regex that finds the given input chapter from the text book.
            Pattern pattern = Pattern.compile("Chapter\\s?.*" + chapter + "[\\s\\S]+?(?=Chapter)");

            if (chapter == 81) {
                //Regex that finds chapter 81.
                pattern = Pattern.compile("Chapter\\s?.*81[\\s\\S]+?\\Z");
            }

            // match the text book text against above pattern.
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //Add the text matched by the previous match to StringBuilder.
                builder.append(matcher.group());
            }

            builder.append(line).append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
