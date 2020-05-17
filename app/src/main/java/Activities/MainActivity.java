package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Half;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusiblecoder.groceryadminfyp.R;

import Model.AdminModel;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txt_email,txt_password;

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Admins");

        txt_email = findViewById(R.id.edt_email);
        txt_password = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithEmail();
            }
        });

    }


    public void loginWithEmail(){

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Signing up...");
        if(!TextUtils.isEmpty(txt_email.getText().toString()) && !TextUtils.isEmpty(txt_password.getText().toString())){

            progressDialog.show();

            mRef.orderByChild("user_name").equalTo(txt_email.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if(dataSnapshot.exists()){

                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            AdminModel model = data.getValue(AdminModel.class);
                            if(txt_password.getText().toString().equals(model.getPassword())) {
                                Log.i("dxdiag", "login success");

                                progressDialog.dismiss();

                               // Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                return;
                            }
                            else {

                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }else{
                        progressDialog.dismiss();

                        Toast.makeText(MainActivity.this, "No User found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else {

            Toast.makeText(this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }
}
