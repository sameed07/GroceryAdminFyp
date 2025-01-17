package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusiblecoder.groceryadminfyp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.OrderDetailAdapter;
import Model.CartModel;

public class OrderDetailActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private FirebaseDatabase mdatabase;
    private DatabaseReference mRef;

    TextView txt_address,txt_total_price,txt_total_item,txt_phone;
    Button btn_deliverd;


    //recycler
    RecyclerView orderRecycler;
    RecyclerView.LayoutManager layoutManager;
    List<CartModel> mList = new ArrayList<>();

    String orderId,address,total_price,total_item,phone,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderId = getIntent().getStringExtra("order_id");
        address = getIntent().getStringExtra("address");
        total_item = getIntent().getStringExtra("total_item");
        total_price = getIntent().getStringExtra("total_price");
        phone = getIntent().getStringExtra("phone");
        user_id = getIntent().getStringExtra("user_id");

        mdatabase = FirebaseDatabase.getInstance();
        mRef   = mdatabase.getReference("Orders").child(orderId).child("items");
        orderRecycler = findViewById(R.id.order_detail_recycler);
        layoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(layoutManager);
        txt_address = findViewById(R.id.txt_address);
        txt_total_price = findViewById(R.id.txt_total_price);
        txt_total_item = findViewById(R.id.txt_total_item);
        txt_phone = findViewById(R.id.txt_phone);
        btn_deliverd = findViewById(R.id.btn_delivered);


        Toast.makeText(this, "" + orderId, Toast.LENGTH_SHORT).show();
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_address.setText(address);
        txt_total_item.setText(total_item);
        txt_phone.setText(phone);
        txt_total_price.setText(total_price);
        
        btn_deliverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOrder();
                Toast.makeText(OrderDetailActivity.this, "Order Delivered", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
        //  Toast.makeText(OrderDetailActivity.this, "Called" , Toast.LENGTH_SHORT).show();
        getData();
    }

    public void getData(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    CartModel model = ds.getValue(CartModel.class);
                    mList.add(model);



                    OrderDetailAdapter adapter = new OrderDetailAdapter(mList,OrderDetailActivity.this);
                    orderRecycler.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void completeOrder(){

        mRef = mdatabase.getReference("Complete_Orders");
        final long val = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("total_item", txt_total_item.getText().toString());
        map.put("total_price", txt_total_price.getText().toString());
        map.put("address", txt_address.getText().toString());
        map.put("phone", txt_phone.getText().toString());
        map.put("user_id", user_id);
        map.put("order_status","pending");
        //   Toast.makeText(this, "" + val, Toast.LENGTH_SHORT).show();


        mRef.child(orderId).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {



                mRef.child(orderId).child(""+val).child("items").setValue(mList).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                    }
                });
            }
        });


    }
}