package ua.lpr.webhelper.service;

import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.entity.URLType;

import java.net.URISyntaxException;
import java.util.Set;

public interface LinkService {

    Set<Link> get(String url, URLType type) throws URISyntaxException;
}
