package ua.lpr.htmldoublesfinder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.lpr.htmldoublesfinder.entity.Result;
import ua.lpr.htmldoublesfinder.entity.site.Page;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
class AsyncPageComparator {

    private Logger logger = LoggerFactory.getLogger(AsyncPageComparator.class);

    @Async
    protected CompletableFuture<Result> compare(Page pageOne, Page pageTwo) {
        String log = "Comparing pair: " + pageOne.hashCode() + " " + pageTwo.hashCode()
                + " on thread " + Thread.currentThread().getName();

        Result result = new Result();

        logger.info(log);

        long begin = System.currentTimeMillis();

        //TODO: remove
        try {
            Thread.sleep((int) (Math.random() * 10) * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result.addPagesWordsCount(pageOne, (long) Math.random() * 10);
        result.addPagesWordsCount(pageTwo, (long) Math.random() * 10);

        log = "Comparing completed on thread " + Thread.currentThread().getName()
                + ", time - " + (System.currentTimeMillis() - begin) / 1000.0 + " sec";

        logger.info(log);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    protected CompletableFuture<Result> compare(Page pageOne, Page pageTwo, Page pageThree) {
        String log = "Comparing pair: " + pageOne.hashCode() + " " + pageTwo.hashCode() + " " + pageThree.hashCode()
                + " on thread " + Thread.currentThread().getName();
        logger.info(log);

        Result result = new Result();

        long begin = System.currentTimeMillis();

        try {
            Thread.sleep((int) (Math.random() * 10) * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result.addPagesWordsCount(pageOne, (long) Math.random() * 10);
        result.addPagesWordsCount(pageTwo, (long) Math.random() * 10);
        result.addPagesWordsCount(pageThree, (long) Math.random() * 10);

        log = "Comparing completed on thread " + Thread.currentThread().getName()
                + ", time - " + (System.currentTimeMillis() - begin) / 1000.0 + " sec";

        logger.info(log);

        return CompletableFuture.completedFuture(result);
    }

}
