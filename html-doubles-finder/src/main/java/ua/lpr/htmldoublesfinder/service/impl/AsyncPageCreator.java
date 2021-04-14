package ua.lpr.htmldoublesfinder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.lpr.htmldoublesfinder.entity.site.Page;
import ua.lpr.htmldoublesfinder.entity.site.impl.SinglePage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Service
class AsyncPageCreator {

    private Logger logger = LoggerFactory.getLogger(AsyncPageCreator.class);

    @Async
    protected CompletableFuture<Page> createPage(String url, String selector) throws IOException, URISyntaxException {

        logger.info("Begin creation on thread " + Thread.currentThread().getName() + ". URL: " + url);

        long begin = System.currentTimeMillis();

        Page page = new SinglePage(url, selector);

        //TODO: remove
//        try {
//            Thread.sleep((int) (Math.random() * 10) * 250);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        logger.info("Creation completed on thread " + Thread.currentThread().getName()
                + ", time - " + (System.currentTimeMillis() - begin) / 1000.0 + " sec");

        return CompletableFuture.completedFuture(page);
    }
}
