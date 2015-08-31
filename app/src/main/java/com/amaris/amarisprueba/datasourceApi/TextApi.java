package com.amaris.amarisprueba.datasourceApi;

import com.amaris.amarisprueba.callback.TextCallback;

public interface TextApi {
    void getSmallText(TextCallback textCallback);
    void getBigText(TextCallback textCallback);
}
