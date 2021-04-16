package ua.lpr.util;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;
import ua.lpr.view.View;
import ua.lpr.view.ViewImpl;

import java.util.List;

public class LogAppender implements Appender {
    private static View view;

    private String name = "GUI Logger";
    private Context context;
    private Status status;

    public static void setView(View view) {
        LogAppender.view = view;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void doAppend(Object event) throws LogbackException {
        if (view == null) view = new ViewImpl(null);
        view.setLog(event.toString());
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void addStatus(Status status) {

    }

    @Override
    public void addInfo(String msg) {

    }

    @Override
    public void addInfo(String msg, Throwable ex) {

    }

    @Override
    public void addWarn(String msg) {

    }

    @Override
    public void addWarn(String msg, Throwable ex) {

    }

    @Override
    public void addError(String msg) {

    }

    @Override
    public void addError(String msg, Throwable ex) {

    }

    @Override
    public void addFilter(Filter newFilter) {

    }

    @Override
    public void clearAllFilters() {

    }

    @Override
    public List<Filter> getCopyOfAttachedFiltersList() {
        return null;
    }

    @Override
    public FilterReply getFilterChainDecision(Object event) {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return true;
    }
}
