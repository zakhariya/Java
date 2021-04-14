package ua.lpr.htmldoublesfinder.entity.site.elements.impl;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import ua.lpr.htmldoublesfinder.entity.site.elements.Paragraph;

public class ParagraphImpl extends Element implements Paragraph {

    public ParagraphImpl(Tag tag, String baseUri, Attributes attributes, String html) {
        super(tag, baseUri, attributes);
        this.html(html);
    }

    @Override
    public String getText() {
        return this.text();
    }

    @Override
    public String getContent() {
        return this.html();
    }
}
