package amaris.com.amarisprueba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import amaris.com.amarisprueba.R;

public class IndexAdapter extends BaseAdapter {

    private HashMap<String, Integer> indexes;
    private Context context;

    public IndexAdapter(HashMap<String, Integer> indexes, Context context) {
        this.indexes = indexes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return indexes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.global_text_indexes_adapter, parent, false);


        TextView txtWord = (TextView) rootView.findViewById(R.id.global_text_indexes_adapter_word);
        TextView txtOccurends = (TextView) rootView.findViewById(R.id.global_text_indexes_adapter_index);

        String key = (String) indexes.keySet().toArray()[position];
        Integer value = indexes.get(key);

        txtOccurends.setText(String.valueOf(value));
        txtWord.setText(key);

        return rootView;
    }
}
