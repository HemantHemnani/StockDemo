package com.trackstockapp.server;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trackstockapp.AppApplication;
import com.trackstockapp.Config;
import com.trackstockapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by hemanth on 10/4/2016.
 */

public class GetBaseRequest<T> {
    private Context mContext;

    /*
    * isLoader = false means not required to show loader
    * */
    private boolean inBackground = false;
    private DialogFragment mDialogLoaderView;
    private ApiInterface apiService;
    private BaseModel baseModel;
    CallBackResponse mCallback;

    JsonObject jsonObj;
    String remainURL = "";
    String sessionKey = "";
    private FragmentManager fragmentManager;
    private String mAppVersion="1.0";
    retrofit2.Call<JsonElement> call;
    public GetBaseRequest(Context context) {
        this.mContext = context;
    }
    //call apis GET type
    public void setBaseRequest(Call call,
                               CallBackResponse listener, boolean inBackground) {
        apiService = ApiBuilderSingleton.getInstance();
        baseModel = new BaseModel(mContext);
        this.mCallback = listener;
        this.call = (retrofit2.Call<JsonElement>) call;
        this.inBackground = inBackground;
        mAppVersion ="1";// GlobalUtil.getAppVersion(mContext);

        requestAPI();
    }


    /*
 * set loader to show views on load API
 * */
    public void setLoader(DialogFragment view) {
        if (view != null) {
            mDialogLoaderView = view;
        }
    }

    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected =  AppApplication.checkNetworkConnectivity();
        return isConnected;
    }


    /*
    * request to server to call API
    * */
    public void requestAPI() {

        if (jsonObj != null) {
            logFullResponse(jsonObj.toString(), "INPUT");
        }

        if (checkConnection()) {
        } else {
            //Show error messages
            call.cancel();
        }


        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                String jsonResponse = "";

                boolean isSuccess;

                if (response.code() == 200) {
                    JsonElement jsonElement = response.body();

                    mCallback.getResponse(jsonElement);
                } else {
                    try {
                        jsonResponse = "" + response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Config.isLog) {
                        //{"status":false,"error":"Unauthorized"} res.code: 401
                        Log.v("result", "resulterrorbody: " + jsonResponse.toString() + " res.code: " + response.code());
                    }
                    logFullResponse(jsonResponse, "Output");
                    if (response.code() == 401) {

                    } else {
                        mCallback.getResponse(jsonResponse);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                if (call.isCanceled() && checkConnection() == false) {
                    mCallback.getError(call, mContext.getResources().getString(R.string.No_Internet_Connection_Detected));
                } else {
                    mCallback.getError(call, "");
                }

//                if ((mDialogLoaderView != null || mDialogLoaderView.isVisible()) && inBackground == false) {
//                    mDialogLoaderView.dismissAllowingStateLoss();
//                }

                Log.d("Error", t.getMessage() + " msg: ");
            }
        });


    }


    /*
    * print inpit/output logcat
    * */
    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;

        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);

            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BASE REQ",
                            "JSON " + inout + " : " + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BASE REQ",
                            "JSON " + inout + " : "
                                    + response.substring((i - 1) * chunkSize,
                                    response.length()));
                }
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("BASE REQ", "JSON " + inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("BASE REQ", "JSON " + inout + " : " + response);
            }

        }
    }


    /*
    * print error
    * */
    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        long readCount = 0;
        int progress = 0, prevProgress = 0;

        String currLine = null;
        try {


			/* Read until all response is read */
            while ((currLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currLine + "\n");
                readCount += currLine.length();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


}
