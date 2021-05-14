package ua.lpr.webhelper.service;

import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.entity.URLType;

import java.net.URISyntaxException;
import java.util.Map;

public interface LinkService {

    Map<String, Link> get(String url, URLType type) throws URISyntaxException;
}
