package com.example.mebinjohnson.logmein;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText mUserEmail;
    private Button mResetPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        mUserEmail = findViewById(R.id.field_re_pass_mail);
        mResetPassword = findViewById(R.id.reset_password_button);

        mAuth = FirebaseAuth.getInstance();

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mUserEmail.getText().toString().trim();
                if (email.equals("")){
                    Toast.makeText(ResetPassword.this,"Please enter the registered email",Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassword.this,"Email has been sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                            }
                            else{
                                Toast.makeText(ResetPassword.this,"Email could not be send. Please check your mail",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });




    }
}
