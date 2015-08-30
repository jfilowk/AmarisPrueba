package amaris.com.amarisprueba.threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Javi on 29/08/15.
 */
public class SortAlphabetical implements Runnable {

    public static final String KEY_DATA = "key.data";
    public static final int KEY_NOTIFY_SORT_ALPHA = 1005;
    private List<String> sortedList;
    private Handler handler;


    public SortAlphabetical() {
        new IllegalStateException("Use parameters constructor");
    }

    public SortAlphabetical(List<String> sortedList, Handler handler) {
        this.sortedList = sortedList;
        this.handler = handler;
    }

    @Override
    public void run() {

        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
        notifyAlphabeticalSort(new ArrayList<>(sortedList));

    }

    private void notifyAlphabeticalSort(ArrayList<String> words) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, words);

        Message message = Message.obtain();
        message.what = KEY_NOTIFY_SORT_ALPHA;
        message.setData(bundle);

        handler.sendMessage(message);
    }
}
