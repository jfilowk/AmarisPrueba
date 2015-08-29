package amaris.com.amarisprueba.datasourceApi;

import amaris.com.amarisprueba.callback.TextCallback;

/**
 * Created by Javi on 29/08/15.
 */
public interface TextApi {
    void getSmallText(TextCallback textCallback);
    void getBigText(TextCallback textCallback);
}
