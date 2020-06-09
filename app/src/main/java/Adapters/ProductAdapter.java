package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Activities.EditProductActivity;
import Activities.MainActivity;
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
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, int position) {

        final ProductModel model = mList.get(position);

        Picasso.get().load(model.getProduct_img()).into(holder.product_img);
        holder.txt_title.setText(model.getProduct_title());
        holder.txt_price.setText("Rs. "+model.getProduct_price());

        holder.product_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(mContext, holder.product_img);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()){

                            case R.id.menu_edt : {
                                Intent i = new Intent(mContext, EditProductActivity.class);
                                i.putExtra("product_id",model.getProduct_id());
                                i.putExtra("category_id",model.getCategory_id());
                                mContext.startActivity(i);
                                break;
                            }
                            case R.id.menu_delete : {

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Confirm dialog demo !");
                                builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference mRef = mdatabase.getReference("Products").child(model.getProduct_id());
                                        mRef.removeValue();
                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      ;
                                    }
                                });

                                builder.show();


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
