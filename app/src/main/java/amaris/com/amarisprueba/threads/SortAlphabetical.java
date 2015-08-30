package amaris.com.amarisprueba.threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import amaris.com.amarisprueba.models.Word;

public class SortAlphabetical implements Runnable {

    public static final String KEY_DATA = "key.data";
    public static final int KEY_NOTIFY_SORT_ALPHA = 1005;
    private List<Word> sortedList;
    private Handler handler;


    public SortAlphabetical() {
        new IllegalStateException("Use parameters constructor");
    }

    public SortAlphabetical(List<Word> sortedList, Handler handler) {
        this.sortedList = sortedList;
        this.handler = handler;
    }

    @Override
    public void run() {

        Collections.sort(sortedList, new Comparator<Word>() {
            @Override
            public int compare(final Word object1, final Word object2) {
                return object1.getWord().toLowerCase().compareTo(object2.getWord().toLowerCase());
            }
        } );
        notifyAlphabeticalSort(new ArrayList<>(sortedList));

    }

    private void notifyAlphabeticalSort(ArrayList<Word> words) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, words);

        Message message = Message.obtain();
        message.what = KEY_NOTIFY_SORT_ALPHA;
        message.setData(bundle);

        handler.sendMessage(message);
    }
}
