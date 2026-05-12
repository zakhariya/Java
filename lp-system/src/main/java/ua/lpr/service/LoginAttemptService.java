package ua.lpr.service;

public interface LoginAttemptService {
    void loginFailed(String remoteAddr);

    boolean isBlocked(String ip);
}
