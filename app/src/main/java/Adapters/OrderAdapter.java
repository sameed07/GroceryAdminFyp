package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infusiblecoder.groceryadminfyp.R;

import java.util.List;

import Activities.OrderDetailActivity;
import Model.OrderModel;


public class OrderAdapter extends  RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<OrderModel> mList;
    Context mContext;

    public OrderAdapter(List<OrderModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_adapter,parent,false);

        return  new OrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        final OrderModel model = mList.get(position);

        holder.txt_title.setText("Order No. "+ position);
        holder.txt_status.setText(model.getOrder_status());
        holder.txt_items.setText(model.getTotal_item());
        holder.txt_price.setText(model.getTotal_price());

        holder.order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, OrderDetailActivity.class);
                i.putExtra("order_id",model.getOrder_id());
                mContext.startActivity(i);
            }
        });

}

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,txt_price,txt_items,txt_status;
        LinearLayout order_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txt_title = itemView.findViewById(R.id.txt_order_id);
            txt_price = itemView.findViewById(R.id.txt_total_price);
            txt_items = itemView.findViewById(R.id.txt_total_item);
            txt_status = itemView.findViewById(R.id.txt_status);
            order_layout = itemView.findViewById(R.id.order_layout);

        }
    }
}
