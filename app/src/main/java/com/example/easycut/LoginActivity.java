package com.example.easycut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    EditText _email;
    EditText _pass;
    Button _enter;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _email=(EditText) findViewById(R.id.email);
        _pass=(EditText) findViewById(R.id.pass);
        _enter=(Button) findViewById(R.id.enter) ;
        mAuth = FirebaseAuth.getInstance();
        _enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
                signIn();

            }
        });

    }

    /**
     *  Check if user is already signed in (non-null), if so continue to ScreenUser.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
            String s=currentUser.getUid();
            System.out.println(s);
            // check if current user is the hairstyle for next screen
            if(!s.equals("hsp5DBs6EZQ3JbDrWk0WiMXTzSM2")){
                Intent intent = new Intent(LoginActivity.this, ScreenUserActivity.class);
                startActivity(intent);
            }
            else{
                currentUser.reload();
                Intent intent = new Intent(LoginActivity.this, ScreenHairStylistActivity.class);
                startActivity(intent);
            }
        }
    }

    private void signIn(){
        String email=_email.getText().toString();
        String pass=_pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String s=user.getUid();
                            System.out.println(s);
                            // check if current user is the hairstyle for next screen
                            if(!s.equals("hsp5DBs6EZQ3JbDrWk0WiMXTzSM2")){
                                Intent intent = new Intent(LoginActivity.this, ScreenUserActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(LoginActivity.this, ScreenHairStylistActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

/*
    private void updateUI(FirebaseUser user) {

    }
*/
}