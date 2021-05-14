package ua.lpr.webhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.entity.URLType;
import ua.lpr.webhelper.service.LinkService;
import ua.lpr.webhelper.util.LinkSearcher;

import java.net.URISyntaxException;
import java.util.Map;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkSearcher linkSearcher;

    @Override
    public Map<String, Link> get(String url, URLType type) throws URISyntaxException {

        return linkSearcher.find(url, type);
    }
}
