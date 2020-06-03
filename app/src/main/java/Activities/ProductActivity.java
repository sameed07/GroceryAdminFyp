package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView category_img;
    private TextView txt_title,txt_desc;
    private FloatingActionButton fab_category;
    private String title,desc,productId,img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);

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
    }
}
