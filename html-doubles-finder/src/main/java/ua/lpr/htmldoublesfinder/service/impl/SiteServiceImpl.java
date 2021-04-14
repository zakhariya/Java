package ua.lpr.htmldoublesfinder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import ua.lpr.htmldoublesfinder.entity.Result;
import ua.lpr.htmldoublesfinder.entity.site.Page;
import ua.lpr.htmldoublesfinder.service.SiteService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@EnableAsync
public class SiteServiceImpl implements SiteService {

    private Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private AsyncPageCreator pageCreator;

    @Autowired
    private AsyncPageComparator pageComparator;

    @Override
    public Result comparePages(Set<String> urls, List<String> selectors) throws ExecutionException,
            InterruptedException, IOException, URISyntaxException {

        List<Page> pages = getPages(urls, selectors);

        Result result = compareAndGetResult(pages);

        return result;
    }

    @Override
    public Result getEmailsListFromPages(Set<String> urls) throws InterruptedException, ExecutionException,
            IOException, URISyntaxException {

        logger.info("Start getting emails");

        List<Page> pages = getPages(urls);

        Result result = new Result();

        for (Page page : pages) {
            Set<String> emails = page.getEmails();

            if (emails.size() == 0
                    && (page.getError() == null || page.getError().length() == 0)) {

                result.addUrlErrors(page.getUrl(), "почта отсутствует");

            } else if (page.getError() != null && page.getError().length() > 0) {

                result.addUrlErrors(page.getUrl(), page.getError());

            } else {
                result.addEmails(emails);
            }
        }

        logger.info("Getting emails finished");

        return result;
    }

    private List<Page> getPages(Set<String> urls) throws InterruptedException, ExecutionException, URISyntaxException, IOException {
        return getPages(urls, null);
    }

    private List<Page> getPages(Set<String> urls, List<String> selectors) throws ExecutionException,
            InterruptedException, IOException, URISyntaxException {

        List<Page> pages = new ArrayList<>();

        if (selectors != null && urls.size() != selectors.size()) {
            return pages;
        }

        int length = urls.size();

        CompletableFuture<Page>[] futures = new CompletableFuture[length];

        int i = 0;

        for (String url : urls) {
            CompletableFuture<Page> future;

            if (selectors != null) {
                future = pageCreator.createPage(url, selectors.get(i));
            } else {
                future = pageCreator.createPage(url, null);
            }

            futures[i] = future;

            i++;
        }

        CompletableFuture.allOf(futures).join();

        for (CompletableFuture<Page> future : futures) {
            pages.add(future.get());
        }

        return pages;
    }

    private Result compareAndGetResult(List<Page> pages) {

        Result result = new Result();

        if (pages.size() < 2) {
            return result;
        }

        Iterator iterator = pages.iterator();

        List<CompletableFuture<Result>> futures = new ArrayList<>();

        int pagesCount = pages.size();
        int idx = 0;

        while (iterator.hasNext()) {
            Page pageOne = (Page) iterator.next();
            idx++;

            if (!iterator.hasNext()) {
                break;
            }

            Page pageTwo = (Page) iterator.next();
            idx++;

            if (iterator.hasNext() && pagesCount % 2 > 0 && pagesCount - idx == 1) {
                futures.add(pageComparator.compare(pageOne, pageTwo, (Page) iterator.next()));
                idx++;
            } else {
                futures.add(pageComparator.compare(pageOne, pageTwo));
            }
        }

        int length = futures.size();

        CompletableFuture<Result>[] futuresArray = new CompletableFuture[length];

        for (int i = 0; i < length; i++) {
            futuresArray[i] = futures.get(i);
        }

        CompletableFuture.allOf(futuresArray).join();

        return result;
    }
}
