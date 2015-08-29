package amaris.com.amarisprueba.callback;

import java.io.InputStream;

/**
 * Created by Javi on 29/08/15.
 */
public interface TextCallback {

    void onSuccess(InputStream inputStream);
    void onError();

}