package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.NewsModel;

public class FlipperAdapter extends BaseAdapter {

    Context mContext;
    List<NewsModel> modelList;
    LayoutInflater inflater;
    GetSliderItemPosition mPosition;

    public FlipperAdapter(Context mContext, List<NewsModel> modelList, GetSliderItemPosition mPosition) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.inflater = LayoutInflater.from(mContext);
        this.mPosition = mPosition;
    }

    @Override
    public int getCount() {
        return modelList.size();
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

        NewsModel model = modelList.get(position);
        txt_news.setText(model.getNews());
        Picasso.get().load(model.getImg_url()).into(img);
//        txt_news.setText(news[position]);
//        img.setImageResource(images[position]);

        mPosition.getSlider(position);

        return view;
    }


}
