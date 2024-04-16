package markup;

import java.util.List;

public class Paragraph implements Father {

    // :NOTE: модификатор доступа
    List<Father> listOfEl;

    public Paragraph(List<Father> pList) {
        listOfEl = pList;
    }


    public void toMarkdown(StringBuilder readyString) {
        for (Father el : listOfEl) {
            el.toMarkdown(readyString);
        }
    }

    public void toHtml(StringBuilder htmlString) {
        for (Father el : listOfEl) {
            el.toHtml(htmlString);
        }
    }
}