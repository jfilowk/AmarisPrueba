package amaris.com.amarisprueba.threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Javi on 28/08/15.
 */
public class ReaderTextTxt implements Runnable {

    private static final String TAG = ReaderTextTxt.class.getSimpleName();

    public static final int KEY_NOTIFY_WORDS_HANDLER = 1000;
    public static final int KEY_NOTIFY_INDEXES_HANDLER = 1001;

    public static final int NUMBER_OF_CONCURRENTS = 5000;

    public static final String KEY_DATA = "key.data";
    public static final String KEY_INDEXES = "key.indexes";

    private int concurrentCounter = 0;

    private InputStream inputStream;
    private List<String> collection;
    private HashMap<String, Integer> indexes;
    private Handler handler;

    public ReaderTextTxt(Handler handler, InputStream inputStream) {
        this.handler = handler;
        this.inputStream = inputStream;

        indexes = new HashMap<>();
        collection = new ArrayList<>();
    }

    public ReaderTextTxt() {
        throw new IllegalStateException("Must be call to another constructor");
    }

    @Override
    public void run() {

        try {

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // \\s+ means any number of whitespaces between tokens

                    if (!TextUtils.isEmpty(line)) {
                        String[] words = line.split("\\s+");

                        execute(words);

                        if (collection.size() > words.length) {
                            int start = collection.size() - words.length;
                            int end = collection.size();

                            List<String> list = collection.subList(start, end);
                            notifyNewWords(new ArrayList<>(list));

                        } else {
                            notifyNewWords(new ArrayList<>(collection));
                        }

                        if (concurrentCounter >= NUMBER_OF_CONCURRENTS) {
                            notifyIndexes(indexes);
                            concurrentCounter = 0;
                        }
                    }
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        notifyIndexes(indexes);

        Log.d(TAG, "Finish");
    }

    private void notifyIndexes(HashMap<String, Integer> indexes) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_INDEXES, indexes);

        Message message = Message.obtain();
        message.setData(bundle);
        message.what = KEY_NOTIFY_INDEXES_HANDLER;

        handler.sendMessage(message);
    }

    private void execute(String[] words) {
        for (String word : words) {
            word = word.replaceAll("[^A-Za-z0-9]", "");

            collection.add(word);

            indexWord(word);
        }
    }

    private void indexWord(String word) {
        //Ignore case sensitive
        //TODO: Improve to check all letters.
        word = capitalize(word);
        if (indexes.containsKey(word)) {
            Integer counter = indexes.get(word);
            counter++;
            indexes.put(word, counter);

            concurrentCounter++;

        } else {
            indexes.put(word, 0);
        }
    }

    private void notifyNewWords(ArrayList<String> lines) {
        Message message = Message.obtain();
        message.what = KEY_NOTIFY_WORDS_HANDLER;
        Bundle bunde = new Bundle();
        bunde.putStringArrayList(KEY_DATA, lines);
        message.setData(bunde);

        handler.sendMessage(message);
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
