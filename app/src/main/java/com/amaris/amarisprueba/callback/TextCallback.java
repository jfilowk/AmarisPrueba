package com.amaris.amarisprueba.callback;

import java.io.InputStream;

public interface TextCallback {

    void onSuccess(InputStream inputStream);
    void onError();

}