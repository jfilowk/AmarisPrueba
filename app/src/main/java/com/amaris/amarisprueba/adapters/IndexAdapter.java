package com.amaris.amarisprueba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import com.amaris.amarisprueba.R;
import butterknife.Bind;
import butterknife.ButterKnife;

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
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.global_text_indexes_adapter, parent, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String key = (String) indexes.keySet().toArray()[position];
        Integer value = indexes.get(key);

        viewHolder.txtWord.setText(String.valueOf(value));
        viewHolder.txtRepeated.setText(key);

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.global_text_indexes_adapter_word) TextView txtWord;
        @Bind(R.id.global_text_indexes_adapter_index) TextView txtRepeated;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
