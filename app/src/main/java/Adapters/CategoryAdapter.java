package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.CategoryModel;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    List<CategoryModel> mList;
    Context mContext;

   public CategoryAdapter(Context mContext, List<CategoryModel> models){

       this.mContext = mContext;
       this.mList = models;
   }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter,parent,false);

        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        CategoryModel model = mList.get(position);

        holder.txt_desc.setText(model.getCategory_desc());
        holder.txt_title.setText(model.getCategory_title());
        Picasso.get().load(model.getCategory_img()).into(holder.img_category);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_category;
        TextView txt_title, txt_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_category = itemView.findViewById(R.id.img_cateogry);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_desc = itemView.findViewById(R.id.txt_desc);
        }
    }
}
