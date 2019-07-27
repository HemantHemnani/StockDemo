package com.trackstockapp.server;

import com.google.gson.JsonObject;

/**
 * Created by hemanth on 12/22/2016.
 */

public class JsonElementUtil {

    public static JsonObject getJsonObject(String... nameValuePair) {
        JsonObject HashMap = null;

        if (null != nameValuePair && nameValuePair.length % 2 == 0) {

            HashMap = new JsonObject();

            int i = 0;

            while (i < nameValuePair.length) {
                HashMap.addProperty(nameValuePair[i], nameValuePair[i + 1]);
                i += 2;
            }

        }

        return HashMap;
    }
}
