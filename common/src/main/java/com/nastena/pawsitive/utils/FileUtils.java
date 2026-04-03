package com.nastena.pawsitive.utils;

public class FileUtils {
    public static final String MAPPING = "/files";
    public static final String ENDPOINT = "/file";

    private static final String FILES_URI_PATH = MAPPING + ENDPOINT + "/";

    public static String getAbsoluteFileUrl(String baseServerUrl, String localFileUrl) {
        baseServerUrl = baseServerUrl.charAt(baseServerUrl.length() - 1) == '/'
                ? baseServerUrl.substring(0, baseServerUrl.length() - 1)
                : baseServerUrl;
        return baseServerUrl + localFileUrl;
    }

    public static String getLocalFileUrl(String filename) {
        return FILES_URI_PATH + filename;
    }
}
