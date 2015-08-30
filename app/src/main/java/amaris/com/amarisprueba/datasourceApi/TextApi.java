package amaris.com.amarisprueba.datasourceApi;

import amaris.com.amarisprueba.callback.TextCallback;

public interface TextApi {
    void getSmallText(TextCallback textCallback);
    void getBigText(TextCallback textCallback);
}
