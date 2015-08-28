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

    public static final int KEY_NOTIFY_HANDLER = 100;
    public static final String KEY_DATA = "key.data";
    private static final int NUMBER_OF_LINES_TO_NOTIFY = 100;

    private InputStream inputStream;
    private List<String> collection;
    private int countLine;
    private HashMap<String, Integer> index;
    private Handler handler;

    public ReaderTextTxt(Handler handler, InputStream inputStream) {
        this.handler = handler;
        this.inputStream = inputStream;

        index = new HashMap<>();
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
                        collection.add(line);

                        if (!haveToNotify()) {
                            countLine++;
                        } else {
                            // Notify 100 new lines UI
                            notifyNewLines();

                            // Reset counter
                            countLine = 0;
                        }

                        indexListWords(words);
                    }
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }

    private void indexListWords(String[] words) {
        for (String word : words) {
            word = word.replaceAll("[^A-Za-z0-9]", "");
            if (index.containsKey(word)) {
                Integer counter = index.get(word);
                counter++;
                index.put(word, counter);
            } else {
                index.put(word, 0);
            }
        }
    }

    private boolean haveToNotify() {
        return countLine < NUMBER_OF_LINES_TO_NOTIFY;
    }

    private void notifyNewLines() {
        Message message = Message.obtain();
        message.what = KEY_NOTIFY_HANDLER;
        Bundle bunde = new Bundle();
        bunde.putStringArrayList(KEY_DATA, (ArrayList<String>) collection);
        message.setData(bunde);

        handler.sendMessage(message);
    }
}
