package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.infusiblecoder.groceryadminfyp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText edt_title,edt_desc,edt_price;
    private ImageView product_img;
    private Button btn_addProduct;

    String productId ;

    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    FirebaseStorage storage;
    StorageReference storageReference;

    //images ke nakhry
    private final int PICK_IMAGE_REQUEST = 71;
    private static final String TAG ="Tag";
    private static final Integer WRITE_EXST = 2;
    private static final Integer READ_EXST = 3;
    private Uri filePath;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productId = getIntent().getStringExtra("product_id");

        //setupfirebase
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Products");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("product_images");

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        edt_desc = findViewById(R.id.edt_desc);
        edt_title = findViewById(R.id.edt_title);
        edt_price = findViewById(R.id.edt_price);
        btn_addProduct = findViewById(R.id.btn_addProduct);
        product_img = findViewById(R.id.category_img);

//        btn_addProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddProductActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//            }
//        });

        product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_title.getText().toString()) &&
                        !TextUtils.isEmpty(edt_desc.getText().toString())&& !TextUtils.isEmpty(edt_price.getText().toString()) ) {
                    uploadImage();

                }else{

                    Toast.makeText(AddProductActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        Toast.makeText(this, "" + productId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                product_img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null )
        {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child(UUID.randomUUID().toString());

            UploadTask uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                        String currentDateandTime = sdf.format(new Date());
                        mUrl = downloadUri.toString();

                        Map<String, Object> map = new HashMap<>();
                        map.put("product_title", edt_title.getText().toString());
                        map.put("product_desc", edt_desc.getText().toString());
                        map.put("product_img", mUrl);
                        map.put("product_price",edt_price.getText().toString());
                        map.put("category_id", productId);
                        map.put("time_stamp",currentDateandTime);
                        mRef.push().setValue(map);
                        progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this, CateogryActivity.class));

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("dxdiag", "onComplete: " + task.getException().getMessage());
                    }
                }
            });


        }else{

            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }
}
