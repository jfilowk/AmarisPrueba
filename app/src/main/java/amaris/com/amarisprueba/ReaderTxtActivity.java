package amaris.com.amarisprueba;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import amaris.com.amarisprueba.adapters.IndexAdapter;
import amaris.com.amarisprueba.adapters.WordAdapter;
import amaris.com.amarisprueba.callback.TextCallback;
import amaris.com.amarisprueba.datasourceApi.TextApi;
import amaris.com.amarisprueba.datasourceApi.TextApiImpl;
import amaris.com.amarisprueba.threads.ReaderTextTxt;


public class ReaderTxtActivity extends BaseActivity {

    private List<String> collection;
    private HashMap<String, Integer> indexes;
    private List<String> words;

    private WordAdapter adapterWords;
    private IndexAdapter adapterIndexes;

    private ListView mListviewWords;
    private ListView mListviewIndexes;

    private TextApi textApi;

    private boolean isReady = false;

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

                            isReady = true;
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

    private void init() {

        collection = new ArrayList<>();
        indexes = new HashMap<String, Integer>();
        words = new ArrayList<>();

        textApi = new TextApiImpl();

        mListviewWords = (ListView) findViewById(R.id.activity_main_listview_words);
        adapterWords = new WordAdapter(this, collection);
        mListviewWords.setAdapter(adapterWords);

        mListviewIndexes = (ListView) findViewById(R.id.activity_main_listview_indexes);

        adapterIndexes = new IndexAdapter(indexes, this);
        mListviewIndexes.setAdapter(adapterIndexes);

        String source = getIntent().getStringExtra(MenuActivity.KEY_SOURCE);
        executeTextReader(source);

    }

    private void executeTextReader(String source) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Downloading...");
            progressDialog.show();
            switch (source) {
                case MenuActivity.HTTP_SMALL:
                    getSmallTextHttp(progressDialog);
                    break;
                case MenuActivity.HTTP_BIG:
                    getBigTextHttp(progressDialog);
                    break;
                default:
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    InputStream in = getApplicationContext().getAssets().open(source);
                    executeReaderTextTxt(in);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getBigTextHttp(final ProgressDialog progressDialog) {
        textApi.getBigText(new TextCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                executeReaderTextTxt(inputStream);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void getSmallTextHttp(final ProgressDialog progressDialog) {
        textApi.getSmallText(new TextCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                executeReaderTextTxt(inputStream);

            }

            @Override
            public void onError() {

            }
        });
    }

    private void executeReaderTextTxt(InputStream inputStream) {
        ReaderTextTxt readerTextTxt = new ReaderTextTxt(handler, inputStream);
        Thread thread = new Thread(readerTextTxt);
        thread.start();
    }


    private void responseIndexes(HashMap<String, Integer> indexes) {
        for (int index = 0; index < indexes.size(); index++) {
            String key = (String) indexes.keySet().toArray()[index];
            this.indexes.put(key, indexes.get(key));
        }
        adapterIndexes.notifyDataSetChanged();
    }

    private void loadData(ArrayList<String> list) {
        collection.addAll(list);
        words.addAll(list);
        adapterWords.notifyDataSetChanged();
    }


    private void sortCollectionAlphabetically() {
        collection.clear();
        collection.addAll(words);
        Collections.sort(collection, String.CASE_INSENSITIVE_ORDER);
        adapterWords.notifyDataSetChanged();
    }

    private void sortCollectionFrequency() {
        collection.clear();
        collection.addAll(words);
        List<String> list = new ArrayList<String>(indexes.keySet());
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                return indexes.get(y) - indexes.get(x);
            }
        });
        collection.clear();
        collection.addAll(list);
        adapterWords.notifyDataSetChanged();
    }

    private void showToast(long duration) {
        Toast.makeText(this, "Solution Time : " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)
                + " seconds", Toast.LENGTH_LONG).show();
    }

    private void showDialogNotReady() {
        AlertDialog alertDialog = new AlertDialog.Builder(ReaderTxtActivity.this).create();
        alertDialog.setTitle("Reading file...");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_sort_alpha) {
            if (isReady) {
                sortCollectionAlphabetically();
            } else {
                showDialogNotReady();
            }
        } else if (itemId == R.id.action_sort_frequency) {
            if (isReady) {
                sortCollectionFrequency();
            } else {
                showDialogNotReady();
            }
        } else if (itemId == R.id.action_list_reset) {
            if (isReady) {
                resetListWords();
            } else {
                showDialogNotReady();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetListWords() {
        collection.clear();
        collection.addAll(words);
        adapterWords.notifyDataSetChanged();
    }
}
