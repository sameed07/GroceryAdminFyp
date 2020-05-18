package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView category_img;
    private EditText edt_category,edt_desc;
    private Button btn_add_category;

    private final int PICK_IMAGE_REQUEST = 71;
    private static final String TAG ="Tag";
    private static final Integer WRITE_EXST = 2;
    private static final Integer READ_EXST = 3;
    private Uri filePath;
    private String mUrl;

    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        //setupfirebase
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Categories");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("category_images");

        //init view
        category_img = findViewById(R.id.category_img);
        btn_add_category = findViewById(R.id.btn_add_category);
        edt_category = findViewById(R.id.edt_title);
        edt_desc = findViewById(R.id.edt_desc);


        //permission
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
        category_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_category.getText().toString()) &&
                        !TextUtils.isEmpty(edt_desc.getText().toString())) {
                    uploadImage();

                }else{

                    Toast.makeText(AddCategoryActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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

                            mUrl = downloadUri.toString();

                            Map<String, Object> map = new HashMap<>();
                            map.put("category_title", edt_category.getText().toString());
                            map.put("Category_desc", edt_desc.getText().toString());
                            map.put("category_img", mUrl);
                            mRef.push().setValue(map);
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddCategoryActivity.this, CateogryActivity.class));

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("dxdiag", "onComplete: " + task.getException().getMessage());
                        }
                    }
                });


        }else{

            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }


    private void askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(AddCategoryActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddCategoryActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(AddCategoryActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(AddCategoryActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
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
                category_img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



}
