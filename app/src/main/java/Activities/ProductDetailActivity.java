package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infusiblecoder.groceryadminfyp.R;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView product_img;
    private TextView txt_title,txt_desc,txt_price,txt_time;
    private String title,desc,productId,img_url,price,time,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        title = getIntent().getStringExtra("product_title");
        desc = getIntent().getStringExtra("product_desc");
        price = getIntent().getStringExtra("product_price");
        time = getIntent().getStringExtra("product_time");
        img_url = getIntent().getStringExtra("product_img");


        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();


            }
        });

        txt_desc = findViewById(R.id.product_desc);
        txt_title = findViewById(R.id.product_name);
        txt_price = findViewById(R.id.product_price);
        txt_time = findViewById(R.id.product_time);
        product_img = findViewById(R.id.product_img);

        txt_title.setText(title);
        txt_desc.setText(desc);
        txt_price.setText("Rs "+price);
        txt_time.setText(time);
        Picasso.get().load(img_url).into(product_img);
    }
}