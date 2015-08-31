package com.amaris.amarisprueba.retrofit;

import retrofit.RestAdapter;


public class ReaderApi {

    // ycfoonwz@imgof.com

    // c12345678

    private static final String BASE_URL = "http://amartxt.hol.es/";
    private static final RestAdapter ADAPTER = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(BASE_URL).build();

    private static final FilesEndpointAsync FILES_ENDPOINT_ASYNC = ADAPTER.create(FilesEndpointAsync.class);

    public static FilesEndpointAsync getFilesEndpointAsync () {
        return FILES_ENDPOINT_ASYNC;
    }


}
