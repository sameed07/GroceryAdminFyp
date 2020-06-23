package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.infusiblecoder.groceryadminfyp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Activities.EditProductActivity;
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
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, int position) {

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
                i.putExtra("address",model.getAddress());
                i.putExtra("total_item",model.getTotal_item());
                i.putExtra("total_price",model.getTotal_price());
                i.putExtra("phone", model.getPhone());
                i.putExtra("user_id", model.getUser_id());
                mContext.startActivity(i);
            }
        });

        holder.order_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(mContext, holder.order_layout);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.order_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()){

                            case R.id.menu_pending : {
                                FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
                                DatabaseReference mRef = mdatabase.getReference("Orders").child(model.getOrder_id());
                                Map<String, Object> map = new HashMap<>();
                                map.put("address", model.getAddress());
                                map.put("phone", model.getPhone());
                                map.put("order_status", "Pennding");
                                map.put("total_item",model.getTotal_item());
                                map.put("total_price",model.getTotal_price());
                                map.put("user_id",model.getUser_id());

                                mRef.updateChildren(map);
                                break;
                            }
                            case R.id.menu_onway : {

                                FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
                                DatabaseReference mRef = mdatabase.getReference("Orders").child(model.getOrder_id());
                                Map<String, Object> map = new HashMap<>();
                                map.put("address", model.getAddress());
                                map.put("phone", model.getPhone());
                                map.put("order_status", "On the way");
                                map.put("total_item",model.getTotal_item());
                                map.put("total_price",model.getTotal_price());
                                map.put("user_id",model.getUser_id());

                                mRef.updateChildren(map);
                                break;
                            }

                            default:
                                return false;
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu
                return false;
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
