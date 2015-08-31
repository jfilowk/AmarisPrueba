package com.amaris.amarisprueba.datasourceApi;

import java.io.IOException;
import java.io.InputStream;

import com.amaris.amarisprueba.callback.TextCallback;
import com.amaris.amarisprueba.retrofit.ReaderApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TextApiImpl implements TextApi {
    @Override
    public void getSmallText(final TextCallback textCallback) {
        ReaderApi.getFilesEndpointAsync().getSmallFile(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    InputStream inputStream = response.getBody().in();
                    textCallback.onSuccess(inputStream);
                } catch (IOException e) {
                    textCallback.onError();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                textCallback.onError();
            }
        });
    }

    @Override
    public void getBigText(final TextCallback textCallback) {
        ReaderApi.getFilesEndpointAsync().getBigFile(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    InputStream inputStream = response.getBody().in();
                    textCallback.onSuccess(inputStream);
                } catch (IOException e) {
                    textCallback.onError();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                textCallback.onError();
            }
        });
    }
}
