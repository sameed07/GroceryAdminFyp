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

import Activities.ProductActivity;
import Model.CategoryModel;
import Model.ProductModel;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<ProductModel> mList;
    Context mContext;

    public ProductAdapter(Context mContext, List<ProductModel> mList){

        this.mContext = mContext;
        this.mList = mList;

    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter,parent,false);

        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        ProductModel model = mList.get(position);

        Picasso.get().load(model.getProduct_img()).into(holder.product_img);
        holder.txt_title.setText(model.getProduct_title());
        holder.txt_price.setText("Rs. "+model.getProduct_price());

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
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_title = itemView.findViewById(R.id.txt_title);


        }
    }
}
