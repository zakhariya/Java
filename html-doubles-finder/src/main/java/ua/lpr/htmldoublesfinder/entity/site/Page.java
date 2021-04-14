package ua.lpr.htmldoublesfinder.entity.site;

import ua.lpr.htmldoublesfinder.entity.site.elements.Image;
import ua.lpr.htmldoublesfinder.entity.site.elements.Link;
import ua.lpr.htmldoublesfinder.entity.site.elements.Paragraph;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Page {

    long getWordsCount();

    long getSymbolsCount();

    String getUrl();

    String getTitle();

    String getDomain();

    String getText();

    void addMatchingWord(String word);

    Map<String, Integer> getMatchingWordsNumbers();

    List<Image> getImages();

    List<Paragraph> getParagraphs();

    List<Link> getExternalLinks();

    List<Link> getInternalLinks();

    List<String> getWords();

    Set<String> getEmails();

    String getError();
}
