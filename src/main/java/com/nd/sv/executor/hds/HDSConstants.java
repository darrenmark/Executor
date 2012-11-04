package com.nd.sv.executor.hds;

/**
 */
public class HDSConstants
{
    // Constants
    public static  String HITACHI_BASE_DEVICE_MGR_VERSION = "5.5";
    public static  String HITACHI_STORAGE_MANAGER = "STORAGE_MANAGER";
    public static  String HITACHI_SERVER_MANAGER  = "SERVER_MANAGER";
    public static  String HITACHI_SERVLET_FORMAT = "/service/";
    public static  String HTTP_PROTOCOL = "http";
    public static  String HTTPS_PROTOCOL = "https";
    public static  String CONTENT_TYPE = "text/xml";
    public static  int    HTTP_CONNECTION_TIME_OUT = 2 * 60 * 1000;  // 2 minutes
    public static  int    HTTP_READ_TIME_OUT = 5 * 60 * 1000;      // 5 minutes
    public static String SERVER_CERTS = System.getProperty("user.dir") + System.getProperty("file.separator") + "HiCommandCerts";
}
