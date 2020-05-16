package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusiblecoder.groceryadminfyp.R;

public class FlipperAdapter extends BaseAdapter {

    Context mContext;
    int[] images;
    String[] news;
    LayoutInflater inflater;

    public FlipperAdapter(Context mContext, int[] images, String[] news ) {
        this.mContext = mContext;
        this.images = images;
        this.news = news;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return news.length;
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

        View view = inflater.inflate(R.layout.flipper_item,null);

        TextView txt_news = view.findViewById(R.id.txt_news);
        ImageView img = view.findViewById(R.id.mNewsImg);

        txt_news.setText(news[position]);
        img.setImageResource(images[position]);

        return view;
    }
}
