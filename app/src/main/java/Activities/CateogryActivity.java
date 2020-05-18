package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusiblecoder.groceryadminfyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.CategoryAdapter;
import Model.CategoryModel;

public class CateogryActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FloatingActionButton fab_category;

    RecyclerView categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
    List<CategoryModel> mList = new ArrayList<>();
    CategoryAdapter adapter;
    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cateogry);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Categories");

        categoryRecycler = findViewById(R.id.category_recycler);
        layoutManager = new LinearLayoutManager(this);
        categoryRecycler.setLayoutManager(layoutManager);


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    CategoryModel model = postSnapshot.getValue(CategoryModel.class);
                   model.setId(postSnapshot.getKey());

                   mList.add(model);
                   adapter = new CategoryAdapter(CateogryActivity.this,mList);

                    categoryRecycler.setAdapter(adapter);
                    Toast.makeText(getApplication(), ""+ model.getCategory_title() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab_category = findViewById(R.id.fab_category);
        
        fab_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(CateogryActivity.this, AddCategoryActivity.class));
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }


}
