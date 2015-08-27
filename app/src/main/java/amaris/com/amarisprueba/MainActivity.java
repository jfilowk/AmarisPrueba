package amaris.com.amarisprueba;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<String> collection;
    private HashMap<String, Integer> index;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private int countLine;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("PINTAR", "Acabo de pintar 20");
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collection = new ArrayList<>();
        index = new HashMap<>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, collection);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        try {
            Log.d("TAG", Arrays.toString(this.getAssets().list("")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Index", index.toString());

        ReaderText readerText = new ReaderText();
        readerText.run();


    }

    private List <String> getLines (int numLine) {
        List<String> lines = new ArrayList<>();
        int startPosition = numLine;
        for (int i = 0; i < 20; i++) {
            startPosition++;
            lines.add(collection.get(startPosition));
        }

        return lines;
    }

    class ReaderText implements Runnable {

        @Override
        public void run() {
            String ret = "";

            try {
                InputStream inputStream = getApplicationContext().getAssets().open("lorem.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = bufferedReader.readLine()) != null) {
                        // \\s+ means any number of whitespaces between tokens
                        String[] tokens = line.split("\\s+");
                        collection.add(line);

                        if (collection.size() == 20) {
                            Log.d("LINES", "Tengo ya "+collection.size()+" lineas");
                            Message message = Message.obtain();
                            message.what = 100;
                            MainActivity.this.handler.sendMessage(message);
                        }

                        for (String word : tokens) {

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

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }
        }
    }

    private String readFromFile() {
        String ret = "";

        try {
            InputStream inputStream = this.getAssets().open("lorem.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    // \\s+ means any number of whitespaces between tokens
                    String[] tokens = line.split("\\s+");
                    collection.add(line);

                    if (countLine > 100) {
                        countLine++;
                    } else {
                        adapter.notifyDataSetChanged();
                        countLine = 0;
                    }

                    for (String word : tokens) {

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

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
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
