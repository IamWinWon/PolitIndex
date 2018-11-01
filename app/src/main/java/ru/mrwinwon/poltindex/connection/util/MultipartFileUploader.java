package ru.mrwinwon.poltindex.connection.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class MultipartFileUploader {

    public static PostPhotoResult loader(File file, String requestURL, Map<String, String> params) {
        String charset = "UTF-8";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addHeaderField("User-Agent", "Android Multipart HTTP Client 1.0");

            String resultLine = "";
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);

                multipart.addFormField(key, value);
            }
            System.out.println(resultLine);

            multipart.addFilePart("avatar", file);

            int code = multipart.getResponseCode();
            String response = multipart.finish();

            return  new PostPhotoResult(code, response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new PostPhotoResult(503, "");
    }
}
