package com.trackstockapp.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.trackstockapp.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hemanth on 11/28/2016.
 */

public class BaseModel<T> {

    /*Response code for success*/
    public static final String RESPONSE_CODE = "1";
    public String baseResponseStr = "";
    public Context mContext;
    private JSONObject jsonObj;
    public String ResponseCode = "", Message = "";
    public int Status = 0;

    public BaseModel(Context context) {
        this.mContext = context;
    }

    public BaseModel(String str) {
        this.baseResponseStr = str;
    }

    public boolean isParse(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                jsonObj = new JSONObject(str);
                ResponseCode = jsonObj.optString("ResponseCode", "");
                Message = jsonObj.optString("message", "");
                if (Config.isLog) {
                    Log.v("Message", "Message: " + Message + " msg: " + jsonObj.optString("message", ""));
                }
                Status = jsonObj.optInt("status", 0);
                if (ResponseCode.equalsIgnoreCase(RESPONSE_CODE) || Status == 1) {
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return false;
    }

    /*
    * get Data object
    * */
    public JSONObject getDataObject() {
        try {
            return jsonObj.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
       * get Data array
       * */
    public JSONArray getDataArray() {
        try {
            return jsonObj.getJSONArray("Data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * get jsonobject full response
    * */
    public JSONObject getCompleteResponse() {
        return jsonObj;
    }


    /*
    * get array list without data object
    * */
    public ArrayList<Object> getWithoutDataList(Class<T> clazz, String arrayName) {
        JSONArray dataArray = null;
        try {
            dataArray = getCompleteResponse().getJSONArray(arrayName);
            return getClassList(dataArray, clazz);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * get json array from data object
    * */
    public ArrayList<Object> getClassList(JSONArray jsonArray, Class<T> clazz) {
        Gson gsn = null;
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            gsn = new Gson();
            Object object = gsn.fromJson(jsonArray.optJSONObject(i).toString(), clazz);
            list.add(object);
        }

        return list;
    }


    /*
    * return a class type
    * */
    public Class<Object> getClassObj(JSONObject jsonObj, Class<T> clazz) {
        Gson gsn = new Gson();
        Object object = gsn.fromJson(jsonObj.toString(), clazz);
        return (Class<Object>) object;
    }


}
