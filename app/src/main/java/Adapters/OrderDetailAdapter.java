package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.CartModel;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    List<CartModel> mList;
    Context mContext;

    public OrderDetailAdapter(List<CartModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_adapter,parent,false);

        return  new OrderDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {

        CartModel model = mList.get(position);
        holder.txt_title.setText(model.getTitle());
        holder.txt_price.setText("Rs. "+model.getPrice());
        Picasso.get().load(model.getImg_url()).into(holder.product_img);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_img;
        TextView txt_title,txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.product_img);
            txt_price = itemView.findViewById(R.id.txt_product_price);
            txt_title = itemView.findViewById(R.id.txt_product_title);
        }
    }
}
