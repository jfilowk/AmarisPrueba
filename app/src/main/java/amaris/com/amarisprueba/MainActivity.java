package amaris.com.amarisprueba;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amaris.com.amarisprueba.adapters.IndexAdapter;
import amaris.com.amarisprueba.adapters.WordAdapter;
import amaris.com.amarisprueba.threads.ReaderTextTxt;


public class MainActivity extends ActionBarActivity {

    private List<String> collection;
    private HashMap<String, Integer> indexes;

    private WordAdapter mAdapterWords;
    private IndexAdapter mAdapterIndexes;

    public Handler handler;

    private ListView mListviewWords;
    private ListView mListviewIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        String filename = getIntent().getStringExtra(MenuActivity.KEY_FILENAME);

        try {
            InputStream inputStream = getApplicationContext().getAssets().open(filename);

            handler = new Handler() {
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
                    }
                }
            };

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
