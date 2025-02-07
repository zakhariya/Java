package ua.lpr.notificationservice.entity;

import java.util.Arrays;


public class Parameters {

    private Config[] configs;
    private Recipient[] recipients;

    public String getConfigValue(String parameter){
        if (!isEmpty()) {
            for (Config config : configs) {
                if (config.getName().equalsIgnoreCase(parameter)) {
                    return config.getValue();
                }
            }
        }

        return null;
    }

    public Config[] getConfigs() {
        return configs;
    }

    public void setConfigs(Config[] configs) {
        this.configs = configs;
    }

    public Recipient[] getRecipients() {
        return recipients;
    }

    public void setRecipients(Recipient[] recipients) {
        this.recipients = recipients;
    }

    //TODO: toString
    @Override
    public String toString() {
        return "Parameters{" +
                "configs=" + Arrays.toString(configs) +
                ", recipients=" + Arrays.toString(recipients) +
                '}';
    }

    public boolean isEmpty() {
        return configs == null || configs.length == 0 || recipients == null || recipients.length == 0;
    }

    public boolean isValid() {
        return !isEmpty();
    }
}

//class Config {
//
//    private String param;
//    private String value;
//
//
//    @JsonCreator
//    public Config(@JsonProperty("param") String param, @JsonProperty("value") String value) {
//        this.param = param;
//        this.value = value;
//    }
//
//    //TODO: toString
//    @Override
//    public String toString() {
//        return "Config{" +
//                "param='" + param + '\'' +
//                ", value='" + value + '\'' +
//                '}';
//    }
//
//    public String getParam() {
//        return param;
//    }
//
//    public void setParam(String param) {
//        this.param = param;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//}