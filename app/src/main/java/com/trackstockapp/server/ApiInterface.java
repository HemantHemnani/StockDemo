package com.trackstockapp.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by hemanth on 10/3/2016.
 */

public interface ApiInterface {

    @POST
    Call<JsonElement> postSimpleJson(@Url String url, @Body JsonObject body);

    @POST
    Call<JsonElement> postJson(@Url String url, @Body JsonObject body, @Header("session_key") String sessionkey,
                               @Header("version") String version);


//https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=BA&apikey=demo
    @GET("query?function=SYMBOL_SEARCH&apikey=L84QQW5YNR1VF8MM")
    Call < JsonElement > searchAPI(@Query("keywords") String searchStr);


    @GET("stock?api_token=SZCUt2RLC1WqpPC4rpgpnOCqqSYlnWqzTVU10ahZwN5UUCiJFYA6piC0mYze")
    Call < JsonElement > searchStockAPI(@Query("symbol") String searchStr);

//* https://api.worldtradingdata.com/api/v1/?symbol=AAPL&date_from=2019-07-01
//    * &output=json&sort=newest&api_token=SZCUt2RLC1WqpPC4rpgpnOCqqSYlnWqzTVU10ahZwN5UUCiJFYA6piC0mYze
    @GET("history?sort=oldest&api_token=SZCUt2RLC1WqpPC4rpgpnOCqqSYlnWqzTVU10ahZwN5UUCiJFYA6piC0mYze")
    Call < JsonElement > monthlyStockRecordAPI(@Query("symbol") String searchStr,
                 @Query("date_from") String startDate,@Query("date_end") String endDate);


}
