package com.systemware.contentintegrator.app;

/**
 * Created by The Bat Cave on 6/12/2014.
 */
public class QueryFormer {
    public String formQuery(String... args){
        String appender = "";
        for(String arg : args){
            if(arg != null) {// if the argument is null, leave it out(arg.isEmpty() is added with an empty string as the value)
                appender += "&" + arg + "=";
            }
        }
        return appender;
    }
}
