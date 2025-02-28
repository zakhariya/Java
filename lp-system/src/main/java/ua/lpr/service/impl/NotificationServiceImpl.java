package ua.lpr.service.impl;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.lpr.model.Setting;
import ua.lpr.model.Task;
import ua.lpr.model.User;
import ua.lpr.service.NotificationService;
import ua.lpr.service.SettingService;
import ua.lpr.service.TaskService;
import ua.lpr.service.UserService;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${settings.param.notification}")
    private String paramNotificationName;

    @Value("${settings.param.notification.delay.tasks-checking}")
    private int paramNotificationDelayTasks;

    @Value("${settings.param.notification.delay.notifiable-checking}")
    private int paramNotificationDelayNotifiable;

    @Value("${settings.param.notification.delay.threads-checking}")
    private int paramNotificationDelayThreads;

    @Value("${settings.param.notification.title}")
    private String paramNotificationTitle;

    @Value("${settings.param.notification.type}")
    private String paramNotificationTypeName;

    @Value("${settings.param.notification.type.email}")
    private String paramNotificationTypeEmail;

    @Value("${settings.param.notification.type.viber}")
    private String paramNotificationTypeViber;

    @Value("${settings.param.phone.mask.length}")
    private int phoneMaskLength;

    @Value("${setting.param.mail}")
    private String paramMailName;

    @Value("${setting.param.mail.address}")
    private String paramMailAddress;

    @Value("${setting.param.mail.smtp.host}")
    private String paramSMTPHost;

    @Value("${setting.param.mail.smtp.port}")
    private String paramSMTPPort;

    @Value("${setting.param.mail.smtp.login}")
    private String paramSmtpLogin;

    @Value("${setting.param.mail.smtp.password}")
    private String paramSmtpPassword;

    @Value("${setting.param.viber.send.url}")
    private String paramViberSendUrl;

    @Value("${setting.param.viber.pa.token}")
    private String paramViberPAToken;

    @Value("${settings.param.notification.hours.min}")
    private int minHour;

    @Value("${settings.param.notification.minutes.min}")
    private int minMinutes;

    @Value("${settings.param.notification.hours.max}")
    private int maxHour;

    @Value("${settings.param.notification.minutes.max}")
    private int maxMinutes;

    private UserService userService;

    private TaskService taskService;

    private SettingService settingService;

    private LocalTime notificationBegin;

    private LocalTime notificationEnd;

    private List<User> notifiableUsers;

    private List<Thread> notificationThreads;

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public void start(UserService userService, TaskService taskService, SettingService settingService) {
        this.userService = userService;
        this.taskService = taskService;
        this.settingService = settingService;

        notificationBegin = LocalTime.of(minHour, minMinutes);
        notificationEnd = LocalTime.of(maxHour, maxMinutes);

        List<Setting> settings = settingService.getByParam(paramNotificationName);
        notifiableUsers = new ArrayList<>();

        for (Setting setting : settings)
            notifiableUsers.addAll(userService.getAliveUsersByPost(setting.getObject()));

        startNotification();
    }

    private void startNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO: always checking for changing list of notifiable posts and users
                while(true){
                    List<Setting> settings = settingService.getByParam(paramNotificationName);
                    List<User> usersByPost = new ArrayList<>();

                    for (Setting setting : settings)
                        usersByPost.addAll(userService.getAliveUsersByPost(setting.getObject()));

                    for (User user : usersByPost){
                        if (!notifiableUsers.contains(user))
                            notifiableUsers = usersByPost;
                    }

                    try {
                        Thread.sleep(paramNotificationDelayNotifiable * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //TODO: waiting for not empty array (first time). is that good idea? :/
        while(notifiableUsers == null
                || notifiableUsers.isEmpty()){

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notificationThreads = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    List<Thread> newThreadList = new ArrayList<>();

                    //checking and filling new array from old, if thread with certain name
                    //witch exists on list of notifiable users
                    for (Thread thread : notificationThreads){
                        for (User user : notifiableUsers)
                            if (thread.getName().equalsIgnoreCase(user.getName()))
                                newThreadList.add(thread);
                    }

                    //closing unwanted threads
                    for (Thread thread : notificationThreads)
                        if (!newThreadList.contains(thread))
                            thread.interrupt();

//                    //TODO: remove
//                    System.out.println("Notifiable users:");

                    //creating new threads and adding to array, if not exists with such name in it
                    for (User user : notifiableUsers){
                        boolean threadExists = false;

                        for (Thread thread : newThreadList)
                            if (thread.getName().equalsIgnoreCase(user.getName()))
                                threadExists = true;

                        if (!threadExists)
                            newThreadList.add(createNotificationThread(user.getName()));

//                        //TODO: remove
//                        System.out.println(user.toString());
                    }

                    notificationThreads = newThreadList;

                    try {
                        Thread.sleep(paramNotificationDelayThreads * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Thread createNotificationThread(String userName){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<Task> tasks = taskService.findHodiernalByUserName(userName);

                while (true){
                    User user = userService.getByLogin(userName);

                    List<Task> newTasks = taskService.findHodiernalByUserName(user.getName());

//                    //TODO: remove
//                    System.out.println(userName + " " + this.hashCode());

                    //Looking for added, or changed tasks
                    for (Task newTask : newTasks){
                        if(!tasks.contains(newTask)){
                            if (containsById(newTask, tasks, true)) {
                                sendMessage(user, "Изменено задание.\\n" +
                                        "Клиент: " + newTask.getClientName() + "\\n" +
                                        "Тема: " + newTask.getTitle() + "\\n" +
                                        "Заметки: " + newTask.getNotes() + "\\n" +
                                        "Статус: " + newTask.getStatus());
                            } else if(!containsById(newTask, tasks, false)) {
                                sendMessage(user, "Добавлено задание.\\n" +
                                        "Клиент: " + newTask.getClientName() + "\\n" +
                                        "Тема: " + newTask.getTitle() + "\\n" +
                                        "Заметки: " + newTask.getNotes() + "\\n" +
                                        "Статус: " + newTask.getStatus());
                            }
                        }
                    }

                    //Looking for removed tasks
                    for (Task oldTask : tasks){
                        if (!newTasks.contains(oldTask)
                                && !containsById(oldTask, newTasks, false)){
                            sendMessage(user, "Удалено задание.\\n" +
                                    "Клиент: " + oldTask.getClientName() + "\\n" +
                                    "Тема: " + oldTask.getTitle() + "\\n" +
                                    "Заметки: " + oldTask.getNotes() + "\\n" +
                                    "Статус: " + oldTask.getStatus());
                        }
                    }

                    tasks = newTasks;

                    try {
                        Thread.sleep(paramNotificationDelayTasks * 1000);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        break;
                    }
                }
            }
        });

        thread.setName(userName);
        thread.start();

        return thread;
    }

    private void sendMessage(User user, String messageText) {

        LocalTime now = LocalTime.now();

        if (now.isBefore(notificationBegin) ||
                now.isAfter(notificationEnd))
            return;

        String notificationType =
                settingService.getByObjectAndParam(paramNotificationTypeName, paramNotificationName).getValue();

        if (notificationType.equalsIgnoreCase(paramNotificationTypeEmail) &&
                user.getEmail() != null && user.hasValidEmail()){
            sendEmailMessage(user, messageText);
        } else if (notificationType.equalsIgnoreCase(paramNotificationTypeViber) &&
                user.getViberId() != null && user.getViberId().length() > 0) {
            sendViberMessage(user, messageText);
        }
    }

    private void sendEmailMessage(User user, String messageText) throws MailAuthenticationException {
        String from = settingService.getByObjectAndParam(paramMailAddress, paramMailName).getValue();
        String host = settingService.getByObjectAndParam(paramSMTPHost, paramMailName).getValue();
        String port = settingService.getByObjectAndParam(paramSMTPPort, paramMailName).getValue();
        String login = settingService.getByObjectAndParam(paramSmtpLogin, paramMailName).getValue();
        String password = settingService.getByObjectAndParam(paramSmtpPassword, paramMailName).getValue();
        String title = settingService.getByObjectAndParam(paramNotificationTitle, paramNotificationName).getValue();

        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(login);
        mailSender.setPassword(password);

        //TODO: do we really need this?
       // mailSender.setJavaMailProperties(System.get);

        /*
	    <property name="javaMailProperties">
	        <props>
       	      <prop key="mail.smtp.auth">true</prop>
       	      <prop key="mail.smtp.starttls.enable">true</prop>
       	   </props>
	    </property>
	    */

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject(title);
        message.setText(messageText);
        mailSender.send(message);

        System.err.println("------Email message------\n" + user.getName() + ": '" + messageText.replace("\\\\" , "\\") + "'");
    }

    private void sendViberMessage(User user, String messageText) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8");
        headers.set("X-Viber-Auth-Token", paramViberPAToken);

        String body = "{\"receiver\":\"" + user.getViberId() + "\"," +
                "\"type\":\"text\"," +
                "\"text\":\"" + messageText.replace("\"", "\\\"") + "\"}";

        SSLContext sslContext = null;

        try {
            sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setSSLSocketFactory(connectionFactory)
                //.setSSLContext(SSLContext.getDefault())
                .useSystemProperties();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));

        ResponseEntity response = restTemplate.exchange(paramViberSendUrl,
                HttpMethod.POST, new HttpEntity<>(body, headers), String.class);

        System.err.println("------Viber message------\n" + user.getName() + ": '" + messageText.replace("\\\\" , "\\") + "'");
        //System.out.println(response);
    }

    private boolean containsById(Task task, List<Task> list, boolean checkStateChanged) {
        int size = list.size();

        for (int i = 0; i < size; i++) {
            if(!checkStateChanged) {
                if (list.get(i).getId() == task.getId())
                    return true;
            } else {
                if (list.get(i).getId() == task.getId()
                        && list.get(i).getStatus().equalsIgnoreCase(task.getStatus()))
                    return true;
            }
        }

        return false;
    }
}
