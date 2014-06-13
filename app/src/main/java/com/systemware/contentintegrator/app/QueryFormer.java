package com.systemware.contentintegrator.app;

/**
 * Created by The Bat Cave on 6/12/2014.
 */
public class QueryFormer {
    public String formQuery(String... args){
        String appender = "";
        for(String arg : args){
            appender += "&" + arg + "=";
        }
        return appender;
    }
}
