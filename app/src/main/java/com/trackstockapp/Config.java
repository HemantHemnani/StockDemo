package com.trackstockapp;

/**
 */

public class Config {


    public static String DEVICE_TYPE = "android";

    /*
     * true mean show logcat
     * */
    public static final boolean isLog = true;



    public static String SERVICE_URL = "";
    public static String DOWNLOAD_FILE_SERVICE_URL = "";

    /*
     * API URL
     * */
    public static String APIURLSTAGING =
            "https://api.worldtradingdata.com/api/v1/";
            //"https://www.alphavantage.co/";


    public static String APIURLPRODUCTION = "";

    static {
        SERVICE_URL = APIURLSTAGING ;
    }




}
