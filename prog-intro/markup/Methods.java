package markup;

import java.util.List;

public class Methods implements Father {

    private final String puncMethod;
    private final String puncHtml;
    private final List<Father> listOfEl;

    public Methods(String puncMethod, String puncHtml, List<Father> listOfEl) {
        this.puncMethod = puncMethod;
        this.puncHtml = puncHtml;
        this.listOfEl = listOfEl;
    }

    public void toMarkdown(StringBuilder readyString) {
        readyString.append(puncMethod);
        for (Father el : listOfEl) {
            el.toMarkdown(readyString);
        }
        readyString.append(puncMethod);
    }

    public void toHtml(StringBuilder htmlString) {
        htmlString.append("<");
        htmlString.append(puncHtml);
        htmlString.append(">");
        for (Father el : listOfEl) {
            el.toHtml(htmlString);
        }
        htmlString.append("</");
        htmlString.append(puncHtml);
        htmlString.append(">");
    }
}
