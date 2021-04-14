package ua.lpr.htmldoublesfinder.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface AccessService {
    boolean clientHasAccess(Set<String> urls, HttpServletRequest request);
}
