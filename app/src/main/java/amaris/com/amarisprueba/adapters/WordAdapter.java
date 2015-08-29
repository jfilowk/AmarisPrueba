package amaris.com.amarisprueba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import amaris.com.amarisprueba.R;

public class WordAdapter extends BaseAdapter {

    private List<String> collection;
    private Context context;

    public WordAdapter(Context context, List<String> collection) {
        this.collection = collection;
        this.context = context;
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public String getItem(int position) {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rootView = convertView;

        if(rootView == null) {
            rootView = LayoutInflater.from(context).inflate(R.layout.global_text_adapter, parent, false);

            holder = new ViewHolder();
            holder.textWord = (TextView) rootView.findViewById(R.id.global_text_adapter_txt);

            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        String line = getItem(position);
        holder.textWord.setText(line);

        return rootView;
    }

    static class ViewHolder {
        TextView textWord;
    }
}
