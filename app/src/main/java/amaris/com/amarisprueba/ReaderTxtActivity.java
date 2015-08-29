package amaris.com.amarisprueba;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import amaris.com.amarisprueba.adapters.IndexAdapter;
import amaris.com.amarisprueba.adapters.WordAdapter;
import amaris.com.amarisprueba.threads.ReaderTextTxt;


public class ReaderTxtActivity extends BaseActivity {

    private List<String> collection;
    private HashMap<String, Integer> indexes;

    private WordAdapter mAdapterWords;
    private IndexAdapter mAdapterIndexes;

    private ListView mListviewWords;
    private ListView mListviewIndexes;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case ReaderTextTxt.KEY_NOTIFY_INDEXES_HANDLER:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            HashMap<String, Integer> indexes = (HashMap<String, Integer>) data.getSerializable(ReaderTextTxt.KEY_INDEXES);

                            responseIndexes(indexes);
                        }
                    });

                    break;
                case ReaderTextTxt.KEY_NOTIFY_WORDS_HANDLER:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            ArrayList<String> list = data.getStringArrayList(ReaderTextTxt.KEY_DATA);

                            loadData(list);
                        }
                    });
                    break;
                case ReaderTextTxt.KEY_NOTIFY_DURATION_HANDLER:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            long duration = data.getLong(ReaderTextTxt.KEY_DURATION);
                            showToast(duration);
                        }
                    });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void showToast(long duration) {
        Toast.makeText(this, "Solution Time : " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)
                + " seconds", Toast.LENGTH_LONG).show();
    }

    private void executeTextReader(String filename) {
        try {
            InputStream inputStream = getApplicationContext().getAssets().open(filename);

            ReaderTextTxt readerTextTxt = new ReaderTextTxt(handler, inputStream);
            Thread thread = new Thread(readerTextTxt);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        collection = new ArrayList<>();
        indexes = new HashMap<String, Integer>();

        mListviewWords = (ListView) findViewById(R.id.activity_main_listview_words);
        mAdapterWords = new WordAdapter(this, collection);
        mListviewWords.setAdapter(mAdapterWords);

        mListviewIndexes = (ListView) findViewById(R.id.activity_main_listview_indexes);

        mAdapterIndexes = new IndexAdapter(indexes, this);
        mListviewIndexes.setAdapter(mAdapterIndexes);

        String filename = getIntent().getStringExtra(MenuActivity.KEY_FILENAME);
        executeTextReader(filename);

    }

    private void responseIndexes(HashMap<String, Integer> indexes) {
        for (int index = 0; index < indexes.size(); index++) {
            String key = (String) indexes.keySet().toArray()[index];
            this.indexes.put(key, indexes.get(key));
        }

        mAdapterIndexes.notifyDataSetChanged();
    }

    private void loadData(ArrayList<String> list) {
        collection.addAll(list);

        mAdapterWords.notifyDataSetChanged();
    }

}
