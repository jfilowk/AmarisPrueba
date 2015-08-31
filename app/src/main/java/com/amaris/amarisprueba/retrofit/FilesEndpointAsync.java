package com.amaris.amarisprueba.retrofit;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Streaming;

public interface FilesEndpointAsync {

    @GET("/loremSmall.txt")
    @Streaming
    void getSmallFile(Callback<Response> responseCallback);


    @GET("/loremBig.txt")
    @Streaming
    void getBigFile(Callback<Response> responseCallback);

}
