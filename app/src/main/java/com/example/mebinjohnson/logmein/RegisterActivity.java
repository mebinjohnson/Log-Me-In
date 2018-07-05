package com.example.mebinjohnson.logmein;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText mUserName, mUserPassword, mUserMail, mUserAge;
    private TextView mSignIn;

    private Button mRegButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        mAuth = FirebaseAuth.getInstance();

        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    //Uploading Data to Firebase

                    String email = mUserMail.getText().toString().trim();
                    String password = mUserPassword.getText().toString().trim();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName("Jane Q. User")
//                                        .build();
                                Toast.makeText(RegisterActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                SendVerificationMail();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Account Creation Failed!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void SendVerificationMail() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(RegisterActivity.this, "Verification Email is sent to your Mail", Toast.LENGTH_LONG).show();
                        updateUserData();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Verification Email could not be send", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    private void initViews() {
        mUserName = findViewById(R.id.field_reg_name);
        mUserPassword = findViewById(R.id.field_reg_password);
        mUserMail = findViewById(R.id.field_reg_mail);
        mUserAge = findViewById(R.id.field_reg_age);
        mRegButton = findViewById(R.id.create_account_button);
        mSignIn = findViewById(R.id.tvSignIn);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mUserMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUserMail.setError("Required.");
            valid = false;
        } else {
            mUserMail.setError(null);
        }

        String password = mUserPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mUserPassword.setError("Required.");
            valid = false;
        } else {
            mUserPassword.setError(null);
        }
        String agestr = mUserAge.getText().toString();
        int age = Integer.parseInt(mUserAge.getText().toString());
        if (TextUtils.isEmpty(agestr)) {
            mUserAge.setError("Required.");
            valid = false;
        } else if (age < 15) {
            mUserAge.setError("You're to Young");
            valid = false;
        } else {
            mUserAge.setError(null);
        }
        return valid;
    }
    private void updateUserData(){
        FirebaseDatabase mdata= FirebaseDatabase.getInstance();
        DatabaseReference mref= mdata.getReference(mAuth.getUid());
        UserProfile userProfile= new UserProfile( mUserName.getText().toString(), mUserMail.getText().toString().trim(), mUserAge.getText().toString().trim());
        mref.setValue(userProfile);
    }
}
