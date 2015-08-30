package amaris.com.amarisprueba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import amaris.com.amarisprueba.R;
import amaris.com.amarisprueba.models.Word;
import butterknife.Bind;
import butterknife.ButterKnife;

public class WordAdapter extends BaseAdapter {

    private List<Word> collection;
    private Context context;

    public WordAdapter(Context context, List<Word> collection) {
        this.collection = collection;
        this.context = context;
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public Word getItem(int position) {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.global_text_adapter, parent, false);

            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Word word = getItem(position);
        holder.textWord.setText(word.getWord());

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.global_text_adapter_txt) TextView textWord;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
