package amaris.com.amarisprueba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Javi on 29/08/15.
 */
public class MenuActivity extends AppCompatActivity {

    public static final String KEY_FILENAME = "filename";
    public static final String LOREM_SMALL_TXT = "loremSmall.txt";
    public static final String LOREM_BIG_TXT = "loremBig.txt";
    @Bind(R.id.btnSmallFile) TextView btnSmallFile;
    @Bind(R.id.btnBigFile) TextView btnBigFile;
    @Bind(R.id.btnSmallHttp) TextView btnSmallHttp;
    @Bind(R.id.btnBigHttp) TextView btnBigHttp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        init();

        //TODO: Create method to check files

        //TODO: Create URL Parser
        //TODO: Create maybe JSON


    }

    private void init() {

    }

    @OnClick(R.id.btnSmallFile)
    public void readSmallFile (){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(KEY_FILENAME, LOREM_SMALL_TXT);
        startActivity(i);
        Log.d("SMALL FILE", "He pulsado small");
    }

    @OnClick(R.id.btnBigFile)
    public void readBigFile (){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(KEY_FILENAME, LOREM_BIG_TXT);
        startActivity(i);
        Log.d("SMALL FILE", "He pulsado small");
    }
    @OnClick(R.id.btnSmallHttp)
    public void readSmallHttp (){
        Log.d("SMALL FILE", "He pulsado small");
    }
    @OnClick(R.id.btnBigHttp)
    public void readBigHttp (){
        Log.d("SMALL FILE", "He pulsado small");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
