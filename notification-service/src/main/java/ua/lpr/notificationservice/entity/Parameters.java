package ua.lpr.notificationservice.entity;

import java.util.Arrays;


public class Parameters {

    private Config[] configs;
    private Manager[] managers;

    public String getConfigValue(String parameter){
        for (Config config : configs) {
            if (config.getName().equalsIgnoreCase(parameter)) {
                return config.getValue();
            }
        }

        return "";
    }

    public Config[] getConfigs() {
        return configs;
    }

    public void setConfigs(Config[] configs) {
        this.configs = configs;
    }

    public Manager[] getManagers() {
        return managers;
    }

    public void setManagers(Manager[] managers) {
        this.managers = managers;
    }

    //TODO: toString
    @Override
    public String toString() {
        return "Parameters{" +
                "configs=" + Arrays.toString(configs) +
                ", managers=" + Arrays.toString(managers) +
                '}';
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