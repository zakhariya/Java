package ua.lpr.htmldoublesfinder.entity.site.elements.impl;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import ua.lpr.htmldoublesfinder.entity.site.elements.Link;

public class LinkImpl extends Element implements Link {

    public LinkImpl(Tag tag, String baseUri, Attributes attributes, String html) {
        super(tag, baseUri, attributes);
        this.html(html);
    }

    @Override
    public String getURL() {
        return this.attr("href");
    }

    @Override
    public String getContent() {
        return this.html();
    }

    @Override
    public String getText() {
        return this.text();//
    }

    @Override
    public String getTitle() {
        return this.attr("title");
    }

    @Override
    public String getAlternativeText() {
        return this.attr("alt");
    }
}
