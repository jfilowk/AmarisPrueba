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
import java.util.List;

import amaris.com.amarisprueba.adapters.SimpleAdapter;
import amaris.com.amarisprueba.threads.ReaderTextTxt;


public class MainActivity extends ActionBarActivity {

    private List<String> collection;
    private ListView listView;
    private SimpleAdapter adapter;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        try {
            InputStream inputStream = getApplicationContext().getAssets().open("lorem.txt");

            handler = new Handler() {
                @Override
                public void handleMessage(final Message msg) {
                    super.handleMessage(msg);

                    if (msg.what == ReaderTextTxt.KEY_NOTIFY_HANDLER) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bundle data = msg.getData();
                                ArrayList<String> list = data.getStringArrayList(ReaderTextTxt.KEY_DATA);

                                loadData(list);
                            }
                        });
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

        listView = (ListView) findViewById(R.id.listView);
        adapter = new SimpleAdapter(this, collection);
        listView.setAdapter(adapter);
    }

    private void loadData(ArrayList<String> list) {
        if (collection != null && collection.size() > 0) {
            collection.clear();
        }

        collection.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private List<String> getLines(int numLine) {
        List<String> lines = new ArrayList<>();
        int startPosition = numLine;
        for (int i = 0; i < 20; i++) {
            startPosition++;
            lines.add(collection.get(startPosition));
        }

        return lines;
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
