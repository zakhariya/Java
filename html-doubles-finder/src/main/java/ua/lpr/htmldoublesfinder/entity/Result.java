package ua.lpr.htmldoublesfinder.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.lpr.htmldoublesfinder.entity.site.Page;

import java.util.*;

@Component
@Scope("request")
public class Result {

    private Map<Page, Map<String, Integer>> pagesWords = new HashMap<>();
    private Map<Page, Long> pagesWordsCount = new HashMap<>();
    private Map<Page, Long> pagesSymbolsCount = new HashMap<>();
    private Map<String, String> urlErrors = new HashMap<>();
    private Set<String> emails = new HashSet<>();

    public void addPagesWords(Page page, Map<String, Integer> words) {
        pagesWords.put(page, words);
    }

    public Map<Page, Map<String, Integer>> getPagesWords() {

        return new HashMap<>(pagesWords);
    }

    public void addPagesWordsCount(Page page, long wordsCount) {
        pagesWordsCount.put(page, wordsCount);//TODO: new Long
    }

    public HashMap<Page, Long> getPagesWordsCount() {
        return new HashMap<>(pagesWordsCount);
    }

    public void addPageSymbolsCount(Page page, long symbolCount) {
        pagesSymbolsCount.put(page, symbolCount);
    }

    public HashMap<Page, Long> getPageSymbolsCount() {
        return new HashMap<>(pagesSymbolsCount);
    }

    public void addEmails(Set<String> emails) {
        this.emails.addAll(emails);
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void addUrlErrors(String url, String error) {
        urlErrors.put(url, error);
    }

    public Map<String, String> getUrlErrors() {
        return urlErrors;
    }

    @Override
    public String toString() {
        return "Result{" +
                "pagesWords=" + pagesWords +
                ", pagesWordsCount=" + pagesWordsCount +
                ", pagesSymbolsCount=" + pagesSymbolsCount +
                ", urlErrors=" + urlErrors +
                ", emails=" + emails +
                '}';
    }
}