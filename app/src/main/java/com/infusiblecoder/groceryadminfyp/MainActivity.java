package com.infusiblecoder.groceryadminfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txt_email,txt_password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

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

            mAuth.signInWithEmailAndPassword(txt_email.getText().toString(),txt_password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }
                    });
        }
        else {

            Toast.makeText(this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }
}
