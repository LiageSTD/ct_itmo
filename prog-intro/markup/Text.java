package markup;

public class Text implements Father {
    // :NOTE: модификатор доступа
    String tLine;

    public Text(String line) {
        tLine = line;
    }

    public void toMarkdown(StringBuilder readyString) {
        readyString.append(tLine);
    }

    public void toHtml(StringBuilder htmlString) {
        htmlString.append(tLine);
    }
}