package ua.lpr.htmldoublesfinder.service;

import ua.lpr.htmldoublesfinder.entity.Result;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface SiteService {
    Result comparePages(Set<String> urls, List<String> selectors) throws ExecutionException, InterruptedException, IOException, URISyntaxException;


    Result getEmailsListFromPages(Set<String> urls) throws InterruptedException, ExecutionException, IOException, URISyntaxException;
}
