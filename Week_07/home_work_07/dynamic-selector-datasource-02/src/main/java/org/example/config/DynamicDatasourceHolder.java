package org.example.config;

public class DynamicDatasourceHolder {

    public static ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

    public static String getDetermineCurrentLookupKey(){
        return THREAD_DATA_SOURCE.get();
    }

    public static void setDetermineCurrentLookupKey(String lookedKey){
        THREAD_DATA_SOURCE.set(lookedKey);
    }

    public static void removeDetermineCurrentLookupKey(){
        THREAD_DATA_SOURCE.remove();
    }
}
