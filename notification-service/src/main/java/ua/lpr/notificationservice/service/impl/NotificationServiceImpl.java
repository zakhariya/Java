package ua.lpr.notificationservice.service.impl;

import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private ViberNotificationService viberNotificationService;

    @Autowired
    private SMSNotificationService smsNotificationService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Value("${param.time.begin}")
    private String paramBegin;

    @Value("${param.time.end}")
    private String paramEnd;

    @Value("${param.time.delay.seconds}")
    private int seconds;

    @Async
    @Override
    public void notifyEmployeesByAll(Parameters parameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String beginTime = parameters.getConfigValue(paramBegin);
                    String endTime = parameters.getConfigValue(paramEnd);

                    while (!isWorkingTime(beginTime, endTime)) {
                        Thread.sleep(1000 * seconds);
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            viberNotificationService.sendBroadcastMessage(parameters);
                        }
                    }).start();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            smsNotificationService.sendBroadcastMessage(parameters);
                        }
                    }).start();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            emailNotificationService.sendBroadcastMessage(parameters);
                        }
                    }).run();
                } catch (InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }

            }
        }).run();
    }

    @Override
    public void notifyEmployeesBySms() {

    }

    @Override
    public void notifyEmployeesByEmail() {

    }

    @Override
    public void notifyEmployeesByViber() {

    }

    private boolean isWorkingTime(String beginTime, String endTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date begin = dateFormat.parse(beginTime);
        Date end = dateFormat.parse(endTime);

        String time = dateFormat.format(new Date());
        Date now = dateFormat.parse(time);

        return (now.after(begin) && now.before(end)) || (now.compareTo(begin) >= 0 && now.compareTo(end) <= 0);
    }
}
