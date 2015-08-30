package amaris.com.amarisprueba.callback;

import java.io.InputStream;

public interface TextCallback {

    void onSuccess(InputStream inputStream);
    void onError();

}