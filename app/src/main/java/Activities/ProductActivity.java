package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapters.CategoryAdapter;
import Adapters.ProductAdapter;
import Model.CategoryModel;
import Model.ProductModel;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView category_img;
    private TextView txt_title,txt_desc;
    private FloatingActionButton fab_category;
    private String title,desc,productId,img_url;


    RecyclerView product_recycler;
    RecyclerView.LayoutManager layoutManager;
    List<ProductModel> mList = new ArrayList<>();
    CategoryAdapter adapter;
    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Products");

        product_recycler = findViewById(R.id.product_recycler);
        layoutManager = new GridLayoutManager(this,2);
        product_recycler.setLayoutManager(layoutManager);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_desc = findViewById(R.id.txt_desc);
        txt_title = findViewById(R.id.txt_title);
        category_img = findViewById(R.id.img_cateogry);
        fab_category = findViewById(R.id.fab_product);


        fab_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductActivity.this, AddProductActivity.class);
                i.putExtra("product_id",productId);
                startActivity(i);
            }
        });


        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        img_url = getIntent().getStringExtra("img_url");
        productId = getIntent().getStringExtra("id");



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();


            }
        });

        txt_title.setText(title);
        txt_desc.setText(desc);
        //  Toast.makeText(this, "" + img_url, Toast.LENGTH_SHORT).show();
        Picasso.get().load(img_url).into(category_img);

        getProducts();
    }

    public void getProducts(){

        mRef.orderByChild("category_id").equalTo(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductModel model = postSnapshot.getValue(ProductModel.class);
                    model.setProduct_id(postSnapshot.getKey());


                    mList.add(model);
                    ProductAdapter adapter = new ProductAdapter(ProductActivity.this, mList);

                    product_recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
