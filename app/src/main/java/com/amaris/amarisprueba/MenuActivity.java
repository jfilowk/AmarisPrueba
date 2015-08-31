package com.amaris.amarisprueba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    public static final String KEY_SOURCE = "filename";
    public static final String LOREM_SMALL_TXT = "loremSmall.txt";
    public static final String LOREM_BIG_TXT = "loremBig.txt";
    public static final String HTTP_SMALL = "httpSmall";
    public static final String HTTP_BIG = "httpBig";

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
        Intent i = new Intent(this, ReaderTxtActivity.class);
        i.putExtra(KEY_SOURCE, LOREM_SMALL_TXT);
        startActivity(i);
    }

    @OnClick(R.id.btnBigFile)
    public void readBigFile (){
        Intent i = new Intent(this, ReaderTxtActivity.class);
        i.putExtra(KEY_SOURCE, LOREM_BIG_TXT);
        startActivity(i);
    }
    @OnClick(R.id.btnSmallHttp)
    public void readSmallHttp (){
        //TODO: Change name class.
        Intent i = new Intent(this, ReaderTxtActivity.class);
        i.putExtra(KEY_SOURCE, HTTP_SMALL);
        startActivity(i);
    }
    @OnClick(R.id.btnBigHttp)
    public void readBigHttp (){
        Intent i = new Intent(this, ReaderTxtActivity.class);
        i.putExtra(KEY_SOURCE, HTTP_BIG);
        startActivity(i);
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
